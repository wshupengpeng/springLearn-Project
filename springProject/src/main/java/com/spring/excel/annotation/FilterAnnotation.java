package com.spring.excel.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FilterAnnotation {
    ExportExcel[] value();
}
