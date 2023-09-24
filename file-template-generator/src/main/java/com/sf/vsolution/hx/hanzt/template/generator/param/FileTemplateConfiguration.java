package com.sf.vsolution.hx.hanzt.template.generator.param;

import com.sf.vsolution.hx.hanzt.template.generator.config.FileTemplateConfig;
import com.sf.vsolution.hx.hanzt.template.generator.enums.GeneratorModeEnum;
import com.sf.vsolution.hx.hanzt.template.generator.function.FileGeneratorCallBackFn;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Description: 文件模板配置
 * @Author 01415355
 * @Date 2023/1/17 16:39
 */
@Data
public class FileTemplateConfiguration {

    /**
     *  文件模板参数配置
     */
    private FileTemplateArgumentConfig fileTemplateArgumentConfig;

    /**
     *  导入excel文件
     *
     */
    private MultipartFile file;

    /**
     *  业务字段,用于生成后回表字段
     */
    private String batchNo;

    /**
     *  文件生成回调函数
     */
    private FileGeneratorCallBackFn fileGeneratorCallBackFn;

    /**
     *  绘制模板的地址,可以是网络资源或本地资源
     */
    private String templatePath;

    /**
     *  是否需要压缩
     */
    private Boolean isCompress = Boolean.TRUE;

    /**
     *  默认采用通用模式
     */
    private GeneratorModeEnum modeEnum = GeneratorModeEnum.GENERAL_MODE;

    /**
     *  文件目录地址
     */
    private FileTemplateConfig config;

    /**
     *  前置解析结果
     *  如果GeneratorModeEnum为生成器模式,此参数必传。
     */
    private FileTemplateParseExcelResult fileTemplateParseExcelResult;


    /**
     *  压缩结果是否上传obs
     */
//    private Boolean isUploadObs = Boolean.TRUE;


}
