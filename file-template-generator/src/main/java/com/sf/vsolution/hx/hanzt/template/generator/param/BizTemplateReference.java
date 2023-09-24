package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 模板映射字段
 *
 * @author 01415355
 * @since 2023-01-17
 */
@Data
public class BizTemplateReference implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 映射类型,0-表格
     */
    private Integer referenceType;

    /**
     *  动态表格表单元格大小,默认为0,没有数据则不保留单元格.
     *  可以通过当前配置,选择无数据时，保留几个单元格。
     */
    private Integer tableCellSize;

    /**
     * 当前字段映射的sheet页
     */
    private Integer sheetNo;

    /**
     *  当前字段映射的sheet名称
     */
    private String sheetName;

    /**
     * word映射占位字段
     */
    private String referencePlaceHolder;

    /**
     * biz_tempalte_field主键id
     */
    private String templateFieldId;

    /**
     * 标题字体大小
     */
    private Integer tableTitleFontSize;

    /**
     * 表头字体颜色
     */
    private String tableTitleFontColor;

    /**
     * 表头字体类型
     */
    private String tableTitleFontFamily;

    /**
     * 表字体大小
     */
    private Integer fontSize;

    /**
     * 表字体颜色
     */
    private String fontColor;

    /**
     * 表字体类型
     */
    private String fontFamily;

    /**
     *  模板id
     */
    private String templateId;


    private String defaultTableValue;


}
