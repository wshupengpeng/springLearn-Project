package com.spring.excel.annotation;

import com.spring.excel.enums.SubSelectionEnum;

import java.lang.annotation.*;

/**
 *  用于标记分页参数
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportSubSelection {
    SubSelectionEnum subselection();

    int defaultValue();
}