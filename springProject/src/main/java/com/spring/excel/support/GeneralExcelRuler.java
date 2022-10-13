package com.spring.excel.support;

import com.spring.excel.support.interfaces.ExcelExecutor;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/13 18:36
 */
public class GeneralExcelRuler extends AbstractExcelRuler{


    @Override
    public ExcelExecutor match(AnnotationDefintion defintion) {
        return null;
    }

    @Override
    public boolean support(AnnotationDefintion defintion) {

        return false;
    }
}
