package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.util.List;

/**
 * @Description: 文件模板一行数据
 * @Author 01415355
 * @Date 2023/1/17 17:05
 */
@Data
public class FileTemplateRow {
    /**
     *  所有列数据
     */
    private List<FileTemplateCol> fileTemplateCols;

    /**
     *  生成文件的函证编号
     *  业务字段
     */
    private String confirmationNo;

}
