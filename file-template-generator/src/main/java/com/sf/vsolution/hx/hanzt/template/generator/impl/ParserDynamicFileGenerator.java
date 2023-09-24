package com.sf.vsolution.hx.hanzt.template.generator.impl;

import com.sf.vsolution.hx.hanzt.template.generator.enums.GeneratorModeEnum;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateConfiguration;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateParseExcelResult;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplatePreResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 解析模式生成器,仅用于解析excel模板
 * @Author 01415355
 * @Date 2023/6/8 17:40
 */
@Slf4j
public class ParserDynamicFileGenerator extends GeneralDynamicFileGenerator{

    public ParserDynamicFileGenerator() {
        super(GeneratorModeEnum.PARSER_MODE);
    }

    @Override
    public FileTemplatePreResult execute(FileTemplateConfiguration configuration) {
        //前置校验规则
        String errorMsg = checkFileTemplateConfiguration(configuration);
        if(!StringUtils.isBlank(errorMsg)){
            log.error("文件生成校验不通过,batchNo：{},错误原因:{}", configuration.getBatchNo(), errorMsg);
            return FileTemplatePreResult.builder()
                    .batchNo(configuration.getBatchNo())
                    .isSuccess(Boolean.FALSE)
                    .errorMsg(errorMsg)
                    .build();
        }
        // 获取监听器进行excel的数据解析
        FileTemplateParseExcelResult fileTemplateParseExcelResult = parseExcel(configuration);
        // 执行回调
        if(configuration.getFileGeneratorCallBackFn() != null){
            configuration.getFileGeneratorCallBackFn().generatorAfterCallback(fileTemplateParseExcelResult);
        }

        return FileTemplatePreResult.builder()
                .success(fileTemplateParseExcelResult.getTemplateRowList().size())
                .fileTemplateParseExcelResult(fileTemplateParseExcelResult)
                .error(fileTemplateParseExcelResult.getTotalNum() - fileTemplateParseExcelResult.getTemplateRowList().size())
                .batchNo(configuration.getBatchNo()).build();
    }
}
