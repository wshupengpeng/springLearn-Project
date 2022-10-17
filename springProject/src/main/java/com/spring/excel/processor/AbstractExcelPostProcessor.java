package com.spring.excel.processor;

import com.spring.excel.support.AnnotationDefinition;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 15:51
 */
public abstract class AbstractExcelPostProcessor {

    public abstract void postProcessBeforeWrite(AnnotationDefinition defintion);

}
