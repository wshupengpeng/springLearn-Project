package com.spring.excel.filter;

import com.spring.excel.annotation.ExportExcel;
import com.spring.excel.enums.SubSelectionEnum;
import com.spring.excel.pojo.PageArgs;
import com.spring.excel.support.AnnotationDefinition;
import com.spring.excel.support.ExcelAnnotationDefinition;

/**
 * @Description: 用于拦截excel文件导出行数
 * @Author 01415355
 * @Date 2022/10/31 10:33
 */
public class ExcelLimitRowFilter implements ExportFilter {
    @Override
    public void doFilter(AnnotationDefinition definition) {
        ExcelAnnotationDefinition excelAnnotationDefinition = (ExcelAnnotationDefinition) definition;
        setLimit(definition.getExportAnnotation().limit(),excelAnnotationDefinition.getPageArgs());
    }

    private void setLimit(long limit,PageArgs pageArgs){
        pageArgs.setPage(Math.min(pageArgs.getPage(SubSelectionEnum.PAGE_SIZE).getValue(), limit),SubSelectionEnum.PAGE_SIZE);
    }

    @Override
    public boolean isSupport(AnnotationDefinition definition) {
        return definition instanceof ExcelAnnotationDefinition;
    }
}
