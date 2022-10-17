package com.spring.excel.support;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.excel.annotation.ExportSubSelection;
import com.spring.excel.enums.SubSelectionEnum;
import com.spring.excel.exceptions.ExcelCommonException;
import com.spring.excel.pojo.FieldEntity;
import com.spring.excel.pojo.PageArgs;
import com.spring.excel.support.interfaces.ExcelExecutor;
import com.spring.excel.utils.ExcelUtils;
import com.spring.excel.utils.HttpServletHolderUtil;
import com.spring.excel.utils.ReflectUtils;
import com.spring.excel.utils.ResponseUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @creater hpp
 * @Date 2022/10/13-21:29
 * @description:
 */
public class SubselectionExecutor implements ExcelExecutor {
    @Override
    public void execute(AnnotationDefinition defintion) {
        Class<?> beanClass = defintion.getExportAnnotation().beanClass();
        ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
        Class returnType = defintion.getMethodSignature().getReturnType();
        if (Collection.class.isAssignableFrom(returnType)) {
            Collection proceed = null;
            PageArgs pageArgs = parsePage(defintion);
            List<FieldEntity> parse = ExcelUtils.parseHead(beanClass);
            List<List<String>> headList = parse.stream()
                    .map(field -> Arrays.asList(field.getName()))
                    .collect(Collectors.toList());
            HttpServletResponse response = HttpServletHolderUtil.getHttpServletResponse();
            ResponseUtils.setExcelResponseHead(response, defintion.getExportAnnotation().fileName());
            ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream())
                    .head(headList);
            defintion.getWriteHandlerList().forEach(writerBuilder::registerWriteHandler);
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetNo(0);
            writeSheet.setSheetName(defintion.getExportAnnotation().sheetName());
            ExcelWriter writer = writerBuilder.build();
            try {
                while (!CollectionUtils.isEmpty(proceed = (Collection) jp.proceed(pageArgs.buildPage()))) {
                    List page = (List) proceed;
                    List<List<String>> dataList = ExcelUtils.parseData(page, parse);
                    writer.write(dataList,writeSheet);
                    PageArgs.PageDefinition pd = pageArgs.getPage(SubSelectionEnum.PAGE_NO);
                    pd.setValue(pd.getValue() + 1);
                }
                writer.finish();
            } catch (Throwable e) {
                throw new ExcelCommonException(e);
            }
        }
    }



    public PageArgs parsePage(AnnotationDefinition defintion){
        ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
        MethodSignature methodSignature = defintion.getMethodSignature();
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        PageArgs pageArgs = new PageArgs();
        for (int mark = 0; mark < parameters.length; mark++) {
            // 判断是否是基本类型
            Parameter parameter = parameters[mark];
            if(parameter.getType().isPrimitive()){
                Annotation[] annotations = parameter.getAnnotations();
                for (Annotation annotation : annotations) {
                    if(annotation.annotationType() == ExportSubSelection.class ){
                        ExportSubSelection subSelection = (ExportSubSelection)annotation;
                        PageArgs.PageDefinition pageDefinition = new PageArgs.PageDefinition();
                        pageDefinition.setMark(mark);
                        pageDefinition.setSubSelectionEnum(subSelection.subselection());
                        pageDefinition.setObj(false);
                        pageDefinition.setValue(subSelection.defaultValue());
                        pageArgs.addPageDefinition(pageDefinition);
                    }
                }
            }else{
                List<Field> subList = ReflectUtils.getFieldByAnnotation(parameter.getType(),ExportSubSelection.class);
                if(CollectionUtils.isEmpty(subList)){
                    continue;
                }
                for (Field field : subList) {
                    PageArgs.PageDefinition pageDefinition = new PageArgs.PageDefinition();
                    pageDefinition.setMark(mark);
                    pageDefinition.setSubSelectionEnum(subSelection.subselection());
                    pageDefinition.setObj(true);
                    pageDefinition.setValue(subSelection.defaultValue());
                    pageDefinition.setField(field);
                    pageArgs.addPageDefinition(pageDefinition);
                }
            }
        }
        return pageArgs;
    }



}
