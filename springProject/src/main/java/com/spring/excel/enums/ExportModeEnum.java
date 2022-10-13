package com.spring.excel.enums;

public enum ExportModeEnum {
    /**
     *  普通模式，导出返回值即写出到excel中
     */
    NORMAL,
    /**
     * 分段模式，即可以进行分页查询导出
     */
    SUBSELECTION,
    /**
     *  自定义模式
     */
    CUSTOMIZER
    ;

}
