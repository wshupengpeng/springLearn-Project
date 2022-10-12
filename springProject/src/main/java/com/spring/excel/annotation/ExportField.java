package com.spring.excel.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface ExportField {
    String name();

    int order();
}
