package com.spring.excel.support;

import com.spring.excel.enums.ExportModeEnum;
import com.spring.excel.support.interfaces.ExcelExecutor;
import com.spring.excel.support.interfaces.ExcelParser;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 17:54
 */
public class AbstarctExcelParser implements ExcelParser {

    /**
     * 目前仅支持两种模式解析
     * @param defintion
     * @return
     */
    @Override
    public ExcelExecutor parse(AnnotationDefintion defintion) {
        switch (defintion.getExportAnnotation().mode()){
            case NORMAL: return null;
            case SUBSELECTION: return null;
            default:  return null;
        }
    }
}
