package com.spring.excel.annotation;

import com.spring.excel.enums.ExportModeEnum;

import java.lang.annotation.*;

/**
 *  ExportExcel模块是为了设计一个通用化的导出方式，
 *  用于适配多场景下的导出情况
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportExcel {
    /**
     *  需要导出实体类
     * @return
     */
    Class<?> beanClass();

    String fileName();

    String sheetName() default "sheet";

    ExportModeEnum mode() default ExportModeEnum.NORMAL;

}
