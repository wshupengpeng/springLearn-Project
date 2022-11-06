package com.spring.excel.filter;

import com.spring.excel.support.AnnotationDefinition;

public interface ExportFilter {

    void doFilter(AnnotationDefinition definition);

    boolean isSupport(AnnotationDefinition definition);
}
