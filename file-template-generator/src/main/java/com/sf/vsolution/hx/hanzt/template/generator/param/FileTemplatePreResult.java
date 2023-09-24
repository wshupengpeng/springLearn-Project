package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 文件模板预处理结果
 * @Author 01415355
 * @Date 2023/5/9 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileTemplatePreResult {

    /**
     *  总行数
     */
    private Integer sumCount;

    /**
     *  成功条数
     */
    private Integer success;

    /**
     *  失败条数
     */
    private Integer error;

    /**
     *  批次号
     */
    private String batchNo;

    /**
     *  是否生成成功
     */
    private Boolean isSuccess;

    /**
     * 文件生成失败原因
     */
    private String errorMsg;

    /**
     *  解析后数据结果
     */
    private FileTemplateParseExcelResult fileTemplateParseExcelResult;

}
