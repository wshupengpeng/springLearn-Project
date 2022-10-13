package com.spring.excel.support;

import com.spring.excel.support.interfaces.ExcelExecutor;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 17:54
 */
public abstract class AbstractExcelRuler{

    public abstract ExcelExecutor match(AnnotationDefintion defintion);

    public abstract boolean support(AnnotationDefintion defintion);
}
