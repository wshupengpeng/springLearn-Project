package com.spring.excel;

import java.lang.annotation.*;

/**
 *  ExportExcel模块是为了设计一个通用化的导出方式，
 *  用于适配多场景下的导出情况
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.CLASS)
public @interface ExportExcel {
    /**
     *  需要导出实体类
     * @return
     */
    Class<?> beanClass();
}
