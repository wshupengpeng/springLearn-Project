package com.sf.vsolution.hx.hanzt.template.html.param;

import lombok.Data;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/7/18 10:02
 */
@Data
public class DynamicReferenceStructure {

    /**
     * 当前字段映射的sheet页
     */
    private Integer sheetNo;

    /**
     * sheet页名称
     */
    private String sheetName;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列类型,double(数据填充时,当数据是数字时，默认保留两位小数),string
     */
    private String columnType;


    private Integer columnIndex;

    /**
     * 字体大小
     */
    private Integer fontSize;

    /**
     * 字体颜色
     */
    private String fontColor;

    /**
     * 字体类型
     */
    private String fontFamily;

}
