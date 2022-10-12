package com.spring.excel.annotation;

import com.spring.excel.enums.SubSelectionEnum;

import java.lang.annotation.*;

/**
 *  用于标记分页参数
 */
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface ExportSubSelection {
    SubSelectionEnum mark();

    int batchNum() default 1000;

    int maxNum() default 10000;
}
