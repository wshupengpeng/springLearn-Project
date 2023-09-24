package com.sf.vsolution.hx.hanzt.template.generator;

import com.sf.vsolution.hx.hanzt.template.generator.enums.GeneratorModeEnum;
import com.sf.vsolution.hx.hanzt.template.generator.param.*;

/**
 * @Description: 文件模板生成器
 * @Author 01415355
 * @Date 2023/5/8 15:32
 */
public abstract class AbstractFileGenerator {

    private GeneratorModeEnum generatorModeEnum;

    public AbstractFileGenerator(GeneratorModeEnum mode) {
        this.generatorModeEnum = mode;
    }

    public GeneratorModeEnum getGeneratorMode(){
        return generatorModeEnum;
    }

    /**
     *  必要信息校验
     * @param configuration
     * @return
     */
    public abstract String checkFileTemplateConfiguration(FileTemplateConfiguration configuration);
    /**
     *  执行文件生成流程
     * @param fileTemplateConfiguration 文件模板配置信息
     */
    public abstract FileTemplatePreResult execute(FileTemplateConfiguration fileTemplateConfiguration);

    /**
     *  解析excel
     * @param configuration  模板配置信息
     */
    public abstract FileTemplateParseExcelResult parseExcel(FileTemplateConfiguration configuration);

    /**
     *  组装render数据
     * @param parseExcelResult excel解析后数据结果
     */
    public abstract FileTemplateRenderBatchData assemblingRenderData(FileTemplateConfiguration configuration,FileTemplateParseExcelResult parseExcelResult);


    /**
     *  生成文件
     * @param configuration 模板配置信息
     * @param fileTemplateRenderBatchData  poi-tl 组装后结果数据
     */
    public abstract FileTemplateGeneratorResult generate(FileTemplateConfiguration configuration,FileTemplateRenderBatchData fileTemplateRenderBatchData);

}
