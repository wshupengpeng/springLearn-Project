package com.spring.excel.support;

import com.spring.excel.support.AnnotationDefintion;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 15:51
 */
public abstract class AbstractExcelPostProcessor {

    public abstract void postProcessBeforeWrite(AnnotationDefintion defintion);

}
