package com.spring.excel.support;

import com.spring.excel.annotation.ExportField;
import com.spring.excel.support.interfaces.ExcelExecutor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @creater hpp
 * @Date 2022/10/13-21:29
 * @description:
 */
public class NormalExecutor implements ExcelExecutor {
    @Override
    public void execute(AnnotationDefintion defintion) {
        Class<?> returnType = defintion.getExportAnnotation().beanClass();
        ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
        try {
            Object proceed = jp.proceed();
            if(proceed instanceof List){
                List<FieldEntity> parse = parse(returnType);
                List<FieldEntity> sortedFieldList = parse.stream()
                        .sorted(Comparator.comparing(FieldEntity::getOrder)).collect(Collectors.toList());
                List list = (List) proceed;
                List<List<String>> dataList= new ArrayList<>();
                for (Object o : list) {
                    Field[] declaredFields = o.getClass().getDeclaredFields();
                    List<String> row = new ArrayList<>();
                    for (FieldEntity fieldEntity : sortedFieldList) {
                        for (Field declaredField : declaredFields) {
                            declaredField.setAccessible(true);
                            String strValue = "";
                            if(declaredField.getName().equals(fieldEntity.getFieldName())){
                                Object value = declaredField.get(o);
                                if(value instanceof Date){
                                    strValue = new SimpleDateFormat(fieldEntity.getFormat()).format((Date) value);
                                }else{
                                    strValue = value.toString();
                                }
                            }
                            row.add(strValue);
                        }
                    }
                    dataList.add(row);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private List<FieldEntity> parse(Class<?> returnType) {
        Field[] declaredFields = returnType.getDeclaredFields();
        List<FieldEntity> fieldEntityList = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            Annotation[] annotations = declaredField.getAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation.getClass() == ExportField.class){
                    ExportField exportField = (ExportField) annotation;
                    FieldEntity fieldEntity = new FieldEntity();
                    fieldEntity.setName(exportField.name());
                    fieldEntity.setOrder(exportField.order());
                    fieldEntity.setFieldName(declaredField.getName());
                    fieldEntity.setFormat(exportField.format());
                    fieldEntityList.add(fieldEntity);
                }
            }
        }
        return fieldEntityList;
    }
}
