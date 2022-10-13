package com.spring.excel.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportField {
    String name();

    int order();

    String format() default "yyyy-MM-dd HH:mm:ss";
}
