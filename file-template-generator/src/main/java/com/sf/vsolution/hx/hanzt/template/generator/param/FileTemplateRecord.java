package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

/**
 * @Description: 文件模板生成记录
 * @Author 01415355
 * @Date 2023/6/6 10:04
 */
@Data
public class FileTemplateRecord {

    /**
     *  文件模板填充后的word地址
     */
    private String wordFilePath;

    /**
     *  文件模板生成的pdf地址
     */
    private String pdfFilePath;

    private String confirmationNo;
}
