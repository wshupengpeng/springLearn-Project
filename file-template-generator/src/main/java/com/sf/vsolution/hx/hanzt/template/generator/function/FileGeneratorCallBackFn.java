package com.sf.vsolution.hx.hanzt.template.generator.function;


import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateGeneratorResult;

/**
 * @Description:
 * 文件生成后的回调函数,用于文件生成结束后的一系列操作
 * @Author 01415355
 * @Date 2023/5/8 15:52
 */
public interface FileGeneratorCallBackFn{

    void generatorAfterCallback(FileTemplateGeneratorResult result);

}
