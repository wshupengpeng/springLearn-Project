package com.sf.vsolution.hx.hanzt.template.generator.impl;

import com.sf.vsolution.hx.hanzt.template.generator.enums.GeneratorModeEnum;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateConfiguration;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateParseExcelResult;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplatePreResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/8 17:51
 */
@Slf4j
public class DynamicFileGenerator extends GeneralDynamicFileGenerator {

    public DynamicFileGenerator() {
        super(GeneratorModeEnum.GENERATOR_MODE);
    }

    @Override
    public FileTemplatePreResult execute(FileTemplateConfiguration configuration) {
        return super.execute(configuration);
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
        if (Objects.isNull(configuration.getFileTemplateParseExcelResult())) {
            errorMsg.append("文件模板解析数据缺失;");
        }
        configuration.getConfig().init();
        return errorMsg.toString();
    }

    @Override
    public FileTemplateParseExcelResult parseExcel(FileTemplateConfiguration configuration) {
        return configuration.getFileTemplateParseExcelResult();
    }
}
