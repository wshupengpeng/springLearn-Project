package com.spring.excel.support;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.spring.excel.enums.ExportModeEnum;
import com.spring.excel.enums.SubSelectionEnum;
import com.spring.excel.exceptions.ExcelCommonException;
import com.spring.excel.pojo.FieldEntity;
import com.spring.excel.pojo.PageArgs;
import com.spring.excel.support.interfaces.ExcelExecutor;
import com.spring.excel.utils.ExcelUtils;
import com.spring.excel.utils.HttpServletHolderUtil;
import com.spring.excel.utils.ResponseUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @creater hpp
 * @Date 2022/10/13-21:29
 * @description:
 */
@Component
public class SubselectionExecutor implements ExcelExecutor {
    @Override
    public void execute(AnnotationDefinition annotationDefinition) {
        if (annotationDefinition instanceof ExcelAnnotationDefinition) {
            ExcelAnnotationDefinition defintion = (ExcelAnnotationDefinition) annotationDefinition;
            Class<?> beanClass = defintion.getExportAnnotation().beanClass();
            ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
            Class returnType = defintion.getMethodSignature().getReturnType();
            // 返回必须是集合，否则不支持
            if (Collection.class.isAssignableFrom(returnType)) {
                Collection proceed = null;
                PageArgs pageArgs = ExcelUtils.parsePage(defintion);
                List<FieldEntity> parse = ExcelUtils.parseHead(beanClass);
                List<List<String>> headList = parse.stream()
                        .map(field -> Arrays.asList(field.getName()))
                        .collect(Collectors.toList());
                HttpServletResponse response = HttpServletHolderUtil.getHttpServletResponse();
                ResponseUtils.setExcelResponseHead(response, defintion.getExportAnnotation().fileName());
                try {
                    ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream())
                            .head(headList);
                    defintion.getWriteHandlerList().forEach(writerBuilder::registerWriteHandler);
                    WriteSheet writeSheet = new WriteSheet();
                    writeSheet.setSheetNo(0);
                    writeSheet.setSheetName(defintion.getExportAnnotation().sheetName());
                    ExcelWriter writer = writerBuilder.build();
                    // 添加拦截器
                    long limit = defintion.getExportAnnotation().limit();
                    long count = 0l;
                    while (!CollectionUtils.isEmpty(proceed = (Collection) jp.proceed(pageArgs.buildPage()))) {
                        List page = (List) proceed;
                        count += page.size();
                        if(count > limit) throw new ExcelCommonException("超过当前最大导出数量:" + limit);
                        List<List<String>> dataList = ExcelUtils.parseData(page, parse);
                        writer.write(dataList, writeSheet);
                        PageArgs.PageDefinition pd = pageArgs.getPage(SubSelectionEnum.PAGE_NO);
                        pd.setValue(pd.getValue() + 1);
                    }
                    writer.finish();
                } catch (Throwable e) {
                    throw new ExcelCommonException(e);
                }
            }
        }
    }

    @Override
    public boolean support(AnnotationDefinition definition) {
        return definition.getExportAnnotation().mode() == ExportModeEnum.SUBSELECTION
                && (definition instanceof ExcelAnnotationDefinition);
    }


}
