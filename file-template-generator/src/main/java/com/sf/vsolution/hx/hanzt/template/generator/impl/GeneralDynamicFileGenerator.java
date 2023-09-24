package com.sf.vsolution.hx.hanzt.template.generator.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.exception.TemplateGenerateException;
import com.sf.vsolution.hx.hanzt.template.generator.AbstractFileGenerator;
import com.sf.vsolution.hx.hanzt.template.generator.enums.GeneratorModeEnum;
import com.sf.vsolution.hx.hanzt.template.generator.listener.BaseDynamicFieldListener;
import com.sf.vsolution.hx.hanzt.template.generator.listener.DynamicReflectListener;
import com.sf.vsolution.hx.hanzt.template.generator.listener.MaxValidListener;
import com.sf.vsolution.hx.hanzt.template.generator.param.*;
import com.sf.vsolution.hx.hanzt.template.generator.policy.CustomizeTablePolicy;
import com.sf.vsolution.hx.hanzt.template.generator.rule.DynamicArgumentRule;
import com.sf.vsolution.hx.hanzt.template.generator.utils.BeanUtil;
import com.sf.vsolution.hx.hanzt.template.generator.utils.PoiTlUtils;
import com.sf.vsolution.hx.hanzt.template.generator.utils.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description: 通过动态文件生成器
 * @Author 01415355
 * @Date 2023/5/9 11:52
 */
@Slf4j
public class GeneralDynamicFileGenerator extends AbstractFileGenerator {

    private static List<DynamicArgumentRule> dynamicArgumentRuleList = new ArrayList<>();

    static {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(ClassUtil.getPackage(DynamicArgumentRule.class), DynamicArgumentRule.class);
        if (CollUtil.isNotEmpty(classes)) {
            for (Class clazz : classes) {
                DynamicArgumentRule dynamicArgumentRule = (DynamicArgumentRule) ReflectUtil.newInstance(clazz);
                dynamicArgumentRuleList.add(dynamicArgumentRule);
            }
        }
    }


    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
            2 * Runtime.getRuntime().availableProcessors(), 60L, TimeUnit.SECONDS,
            new ArrayBlockingQueue(500),
            new BasicThreadFactory.Builder().namingPattern("async-file-execute-pool-%d").daemon(true).build(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    protected GeneralDynamicFileGenerator(GeneratorModeEnum generatorModeEnum) {
        super(generatorModeEnum);
    }

    public GeneralDynamicFileGenerator() {
        super(GeneratorModeEnum.GENERAL_MODE);
    }

    @Override
    public FileTemplatePreResult execute(FileTemplateConfiguration configuration) {
        Long startTime = System.currentTimeMillis();
        //前置校验规则
        String errorMsg = checkFileTemplateConfiguration(configuration);
        if (!StringUtils.isBlank(errorMsg)) {
            log.error("文件生成校验不通过,batchNo：{},错误原因:{}", configuration.getBatchNo(), errorMsg);
            return FileTemplatePreResult.builder()
                    .batchNo(configuration.getBatchNo())
                    .isSuccess(Boolean.FALSE)
                    .errorMsg(errorMsg)
                    .build();
        }
        // 1 获取监听器进行excel的数据解析
        FileTemplateParseExcelResult fileTemplateParseExcelResult = parseExcel(configuration);
        log.info("parseExcel success, cost time:{}ms, batchNo:{}", System.currentTimeMillis() - startTime, configuration.getBatchNo());
        executor.execute(() -> {
            Long asyncStartTime = System.currentTimeMillis();
            log.info("async execute start,batchNo:{}", configuration.getBatchNo());
            // 2 参数组装为poi-tl 数据对象
            FileTemplateRenderBatchData fileTemplateRenderBatchData = assemblingRenderData(configuration, fileTemplateParseExcelResult);
            log.info("async assemblingRenderData end, cost time:{}ms,batchNo:{}", System.currentTimeMillis() - asyncStartTime, configuration.getBatchNo());
            // 3 生成文件
            FileTemplateGeneratorResult result = generate(configuration, fileTemplateRenderBatchData);
            log.info("async generate  end, cost time:{}ms,batchNo:{}", System.currentTimeMillis() - asyncStartTime, configuration.getBatchNo());
            // 4 函数回调,用于数据库操作或上传obs等操作
            if (Objects.nonNull(configuration.getFileGeneratorCallBackFn())) {
                configuration.getFileGeneratorCallBackFn().generatorAfterCallback(result);
            }
            // 5 清除临时文件
            finish(result);
            log.info("async execute end, cost time:{}ms,batchNo:{}", System.currentTimeMillis() - asyncStartTime, configuration.getBatchNo());
        });
        log.info("file template execute end, cost time:{},batchNo:{}", System.currentTimeMillis() - startTime, configuration.getBatchNo());
        return FileTemplatePreResult.builder()
                .success(fileTemplateParseExcelResult.getTemplateRowList().size())
                .sumCount(fileTemplateParseExcelResult.getTotalNum())
                .fileTemplateParseExcelResult(fileTemplateParseExcelResult)
                .error(fileTemplateParseExcelResult.getTotalNum() - fileTemplateParseExcelResult.getTemplateRowList().size())
                .isSuccess(Boolean.TRUE)
                .batchNo(configuration.getBatchNo()).build();

    }

    @Override
    public String checkFileTemplateConfiguration(FileTemplateConfiguration configuration) {
        StringBuilder errorMsg = new StringBuilder();
        if (Objects.isNull(configuration.getConfig())
                || StringUtils.isBlank(configuration.getConfig().getDestPdfPath())
                || StringUtils.isBlank(configuration.getConfig().getDestWordPath())
                || StringUtils.isBlank(configuration.getConfig().getImageDir())
                || Objects.isNull(configuration.getConfig().getImportSize())) {
            errorMsg.append("临时文件目录配置缺失;");
        }

        if (StringUtils.isBlank(configuration.getTemplatePath())) {
            errorMsg.append("模板缺失;");
        }

        if (StringUtils.isBlank(configuration.getBatchNo())) {
            errorMsg.append("批次号缺失;");
        }

        if (Objects.isNull(configuration.getFileTemplateArgumentConfig())) {
            errorMsg.append("文件模板参数缺失;");
        }

        if (Objects.isNull(configuration.getFile())) {
            errorMsg.append("导入文件缺失;");
        }

        configuration.getConfig().init();
        return errorMsg.toString();
    }

    private void finish(FileTemplateGeneratorResult result) {
        Set<FileTemplateRecord> fileTemplateRecordSet = result.getFileTemplateRecordSet();
        log.info("generator finish begin ,clear temp file");
        for (FileTemplateRecord fileTemplateRecord : fileTemplateRecordSet) {
            if (StringUtils.isNotBlank(fileTemplateRecord.getWordFilePath())) {
                FileUtil.del(fileTemplateRecord.getWordFilePath());
            }

            if (StringUtils.isNotBlank(fileTemplateRecord.getPdfFilePath())) {
                FileUtil.del(fileTemplateRecord.getPdfFilePath());
            }
        }

        if (StringUtils.isNotBlank(result.getZipPath())) {
            FileUtil.del(result.getZipPath());
        }
        log.info("generator finish end ,clear temp file success");
    }

    @Override
    public FileTemplateParseExcelResult parseExcel(FileTemplateConfiguration configuration) {
        try {
            MaxValidListener maxValidListener = new MaxValidListener();

            EasyExcel.read(configuration.getFile().getInputStream())
                    .sheet()
                    .registerReadListener(maxValidListener)
                    .sheetNo(0)
                    .doRead();

            Assert.isTrue(configuration.getConfig().getImportSize() < maxValidListener.getTotalNum(),
                    "导入数据超过最大限制:" + configuration.getConfig().getImportSize());

            // 读取第一个sheet页
            BaseDynamicFieldListener baseDynamicFieldListener = new BaseDynamicFieldListener(configuration.getFileTemplateArgumentConfig());

            EasyExcel.read(configuration.getFile().getInputStream())
                    .sheet()
                    .registerReadListener(baseDynamicFieldListener)
                    .sheetNo(0)
                    .doRead();

            // 读取后续的sheet页
            int sheetSize = EasyExcel.read(configuration.getFile().getInputStream()).build().excelExecutor().sheetList().size();

            List<DynamicReferenceData> dynamicReflectDataList = new ArrayList<>();

            if (sheetSize > 1) {
                for (int i = 1; i < sheetSize; i++) {
                    DynamicReflectListener dynamicReflectListener = new DynamicReflectListener();
                    EasyExcel.read(configuration.getFile().getInputStream()).sheet(i).registerReadListener(dynamicReflectListener).doRead();
                    dynamicReflectDataList.add(dynamicReflectListener.getDynamicReflectData());
                }
            }

            // 组装数据
            FileTemplateParseExcelResult fileTemplateParseExcelResult = FileTemplateParseExcelResult.builder()
                    .totalNum(maxValidListener.getTotalNum())
                    .templateRowList(baseDynamicFieldListener.getRecordList())
                    .build();

            // 校验参数合法性,目前只验证了导入数据不能为空,以后可以加上必传参数的校验验证
            Assert.isTrue(checkExcelResultVaild(fileTemplateParseExcelResult), "excel导入数据为空");

            // 预处理数据,后续处理数据按照FileTemplateCol维度
            for (FileTemplateRow fileTemplateRow : baseDynamicFieldListener.getRecordList()) {
                for (FileTemplateCol fileTemplateCol : fileTemplateRow.getFileTemplateCols()) {
                    if (CollectionUtils.isNotEmpty(fileTemplateCol.getReferenceList())) {
                        fileTemplateCol.setDynamicReferenceDataList(dynamicReflectDataList);
                    }
                }
            }

            return fileTemplateParseExcelResult;
        } catch (Exception e) {
            log.info("parseExcel is failed,e:", e);
            throw new TemplateGenerateException("excel导入解析失败");
        }
    }

    private boolean checkExcelResultVaild(FileTemplateParseExcelResult fileTemplateParseExcelResult) {
        return CollectionUtils.isNotEmpty(fileTemplateParseExcelResult.getTemplateRowList());
    }


    @Override
    public FileTemplateRenderBatchData assemblingRenderData(FileTemplateConfiguration configuration, FileTemplateParseExcelResult parseExcelResult) {
        List<FileTemplateRenderData> fileTemplateRenderDataList = new ArrayList<>();
        // 遍历每一行模板数据
        for (FileTemplateRow fileTemplateRow : parseExcelResult.getTemplateRowList()) {
            FileTemplateRenderData fileTemplateRenderData = new FileTemplateRenderData();
            List<FileTemplateCol> fileTemplateCols = fileTemplateRow.getFileTemplateCols();
            // 遍历每一列模板数据
            for (FileTemplateCol fileTemplateCol : fileTemplateCols) {
                // 遍历组装动态参数规则
                for (DynamicArgumentRule dynamicArgumentRule : dynamicArgumentRuleList) {
                    // 判断是否满足当前规则条件
                    if (dynamicArgumentRule.isMatchRule(fileTemplateCol)) {
                        // 组装poi-tl render对象数据
                        dynamicArgumentRule.rule(configuration, fileTemplateCol, fileTemplateRenderData);
                    }
                }
            }

            //todo 文件名自定义
            fileTemplateRenderData.setFileName(UUID.randomUUID().toString());
            fileTemplateRenderData.setConfirmationNo(fileTemplateRow.getConfirmationNo());
            fileTemplateRenderDataList.add(fileTemplateRenderData);

        }
        log.info("render data end,fileTemplateRenderDataList size:{}, batchNo:{}", fileTemplateRenderDataList.size(), configuration.getBatchNo());
        // 组装固定图片
        if (CollectionUtils.isNotEmpty(configuration.getFileTemplateParseExcelResult().getFixedImageList())) {
            log.info("contains fixed image, assembling fixed image begin");
            List<ImageConfig> fixedImageList = configuration.getFileTemplateParseExcelResult().getFixedImageList().stream()
                    .map((fixedTemplateImage) -> parseFixedImage(configuration, fixedTemplateImage))
                    .collect(Collectors.toList());
            fileTemplateRenderDataList.stream().forEach(fileTemplateRenderData -> fileTemplateRenderData.getImageConfigs().addAll(fixedImageList));
            log.info("batchNo:{},add fixed image size:{},assembling fixed end", configuration.getBatchNo(), fixedImageList.size());
        }

        FileTemplateRenderBatchData fileTemplateRenderBatchData = new FileTemplateRenderBatchData();
        fileTemplateRenderBatchData.setFileTemplateRenderData(fileTemplateRenderDataList);
        return fileTemplateRenderBatchData;
    }


    private ImageConfig parseFixedImage(FileTemplateConfiguration configuration, FixedTemplateImage fixedTemplateImage) {

        ImageConfig imageConfig = BeanUtil.copyBeanProperties(ImageConfig::new, fixedTemplateImage);
        String imageUrl = fixedTemplateImage.getImageUrl();
        // 图片落地
        String imageTempPath = configuration.getConfig().getImageDir() + File.separator + UUID.randomUUID().toString() + FileConstant.JPG_SUFFIX;

        if (imageUrl.matches(FileConstant.HTTP_REGEX)) {
            HttpUtil.downloadFile(imageTempPath, FileUtil.file(imageTempPath), FileConstant.HTTP_TIME_OUT);
        } else {
            imageTempPath = imageUrl;
        }
        // 如果图片生成失败，没必要继续生成PDF了，因为生成的也是错误的，无需浪费资源
        if (org.apache.commons.lang3.StringUtils.isBlank(imageTempPath)) {
            throw new TemplateGenerateException("image generate failed,shut down job");
        }
        imageConfig.setImagePath(imageTempPath);
        return imageConfig;
    }

    @Override
    public FileTemplateGeneratorResult generate(FileTemplateConfiguration configuration, FileTemplateRenderBatchData fileTemplateRenderBatchData) {
        // 创建线程池
        ThreadPoolExecutor generateThreadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                2 * Runtime.getRuntime().availableProcessors(), 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue(500),
                new BasicThreadFactory.Builder().namingPattern("async-file-generator-pool-%d").daemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        List<FileTemplateRenderData> fileTemplateRenderDataList = fileTemplateRenderBatchData.getFileTemplateRenderData();
        Set<FileTemplateRecord> fileTemplateRecordSet = Collections.EMPTY_SET;

        FileTemplateGeneratorResult fileTemplateGeneratorResult = new FileTemplateGeneratorResult();
        fileTemplateGeneratorResult.setFileTemplateConfiguration(configuration);

        String zipPath = null;
        String wordFilePath = configuration.getConfig().getDestWordPath() + File.separator + UUID.randomUUID().toString() + FileConstant.DOC_SUFFIX;
        try {
            File wordFile = FileUtil.file(wordFilePath);
            if (configuration.getTemplatePath().matches(FileConstant.HTTP_REGEX)) {
                log.info("template path is network path, download word template,url path:{}", configuration.getTemplatePath());
                HttpUtil.downloadFile(configuration.getTemplatePath(), wordFile, FileConstant.HTTP_TIME_OUT);
            } else {
                log.info("template path is local path ,url path:{}", configuration.getTemplatePath());
                wordFilePath = configuration.getTemplatePath();
            }

            // 生成文件
            fileTemplateRecordSet = fileTemplateRenderDataList.stream()
                    .map(fileTemplateRenderData -> CompletableFuture.supplyAsync(() -> renderData(fileTemplateRenderData, wordFile, configuration), generateThreadPool).join())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            if (configuration.getIsCompress()) {
                Set<String> pdfSet = fileTemplateRecordSet.stream()
                        .map(FileTemplateRecord::getPdfFilePath)
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.toSet());
                zipPath = ZipUtil.z7z(pdfSet, configuration.getConfig().getDestPdfPath());
                fileTemplateGeneratorResult.setZipPath(zipPath);
            }
            fileTemplateGeneratorResult.setFileTemplateRecordSet(fileTemplateRecordSet);
        } catch (Exception e) {
            log.error("file generate failed, error:", e);
            fileTemplateGeneratorResult.setIsSuccess(Boolean.FALSE);
            fileTemplateGeneratorResult.setException(e);
        } finally {
            // 关闭线程池
            generateThreadPool.shutdown();
            // 删除模板文件
            FileUtil.del(wordFilePath);
//            pdfFileList.stream().forEach(FileUtil::del);
        }
        return fileTemplateGeneratorResult;
    }

    public FileTemplateRecord renderData(FileTemplateRenderData record, File wordFile, FileTemplateConfiguration fileTemplateConfiguration) {
        XWPFTemplate template = null;
        Configure.ConfigureBuilder configureBuilder = Configure.newBuilder();
        configureBuilder.addPlugin('~', new CustomizeTablePolicy());
        Configure config = configureBuilder.build();
        String destWordFilePath = fileTemplateConfiguration.getConfig().getDestWordPath() + File.separator + record.getFileName() + FileConstant.DOC_SUFFIX;
        FileTemplateRecord fileTemplateRecord = new FileTemplateRecord();
        try (FileOutputStream out = new FileOutputStream(destWordFilePath)) {
            template = XWPFTemplate.compile(wordFile, config).render(record.getRenderDataMap());
            template.write(out);
            out.flush();
            out.close();
            template.close();
            String destPdfFilePath = fileTemplateConfiguration.getConfig().getDestPdfPath() + File.separator + record.getFileName() + FileConstant.PDF_SUFFIX;
            PoiTlUtils.wordToPdf(destWordFilePath, destPdfFilePath);

            // 添加图片信息
            if (CollectionUtil.isNotEmpty(record.getImageConfigs())) {
                log.info("batchNo:{}.add image config size:{}", fileTemplateConfiguration.getBatchNo(), record.getImageConfigs().size());
                // 添加图片信息
                record.getImageConfigs().forEach(imageConfig -> {
                    PoiTlUtils.addImgOnPdf(destPdfFilePath, imageConfig);
                });
            }
            fileTemplateRecord.setWordFilePath(destWordFilePath);
            fileTemplateRecord.setPdfFilePath(destPdfFilePath);
            fileTemplateRecord.setConfirmationNo(record.getConfirmationNo());
            return fileTemplateRecord;
        } catch (Exception e) {
            log.error("getPdfPath failed , error msg : {}", e);
        } finally {
            if (template != null) {
                try {
                    template.close();
                } catch (Exception e) {
                    log.error("close template failed");
                    template = null;
                }
            }
//            FileUtil.del(destWordFilePath);
        }
        return null;
    }

}
