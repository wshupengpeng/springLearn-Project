package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/1/17 19:59
 */
@Data
public class DynamicReferenceCol {
    /**
     *  动态表头下标
     */
    private Integer colIndex;
    /**
     *  动态表头值
     */
    private String excelValue;

}
