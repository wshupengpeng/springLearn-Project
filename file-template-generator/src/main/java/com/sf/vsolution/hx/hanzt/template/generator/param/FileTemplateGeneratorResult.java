package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.util.Set;

/**
 * @Description: 文件生成结果
 * @Author 01415355
 * @Date 2023/6/6 9:56
 */
@Data
public class FileTemplateGeneratorResult{

    /**
     *  文件生成打包结果,可以为空
     */
    private String zipPath;

    /**
     *  文件生成配置信息
     */
    private FileTemplateConfiguration fileTemplateConfiguration;

    /**
     *  文件模板生成记录
     */
    private Set<FileTemplateRecord> fileTemplateRecordSet;

    /**
     * 是否生成成功标记
     */
    private Boolean isSuccess = Boolean.TRUE;


    /**
     *  文件生成异常
     */
    private Exception exception;

}
