package com.spring.excel.utils;

import com.spring.excel.annotation.ExportField;
import com.spring.excel.annotation.ExportSubSelection;
import com.spring.excel.pojo.FieldEntity;
import com.spring.excel.pojo.PageArgs;
import com.spring.excel.support.AnnotationDefinition;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/14 10:55
 */
public class ExcelUtils {

    public static List<List<String>> parseData(Collection<Object> list, List<FieldEntity> sortedFieldList) {
        List<List<String>> dataList= new ArrayList<>();
        for (Object o : list) {
            Field[] declaredFields = o.getClass().getDeclaredFields();
            List<String> row = new ArrayList<>();
            for (Field declaredField : declaredFields) {
                String strValue = "";
                for (FieldEntity fieldEntity : sortedFieldList) {
                    declaredField.setAccessible(true);
                    if(declaredField.getName().equals(fieldEntity.getFieldName())){
                        Object value = null;
                        try {
                            value = declaredField.get(o);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        if(value instanceof Date){
                            strValue = new SimpleDateFormat(fieldEntity.getFormat()).format((Date) value);
                        }else{
                            strValue = value == null ? strValue : value.toString();
                        }
                        break;
                    }
                }
                row.add(strValue);
            }
            dataList.add(row);
        }
        return dataList;
    }

    public static List<FieldEntity> parseHead(Class<?> returnType) {
        Field[] declaredFields = returnType.getDeclaredFields();
        List<FieldEntity> fieldEntityList = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            Annotation[] annotations = declaredField.getAnnotations();
            for (Annotation annotation : annotations) {
                if(annotation.annotationType() == ExportField.class){
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
        return  fieldEntityList.stream()
                .sorted(Comparator.comparing(FieldEntity::getOrder)).collect(Collectors.toList());
    }

    public static PageArgs parsePage(AnnotationDefinition defintion){
        ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
        MethodSignature methodSignature = defintion.getMethodSignature();
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        PageArgs pageArgs = new PageArgs();
        for (int mark = 0; mark < parameters.length; mark++) {
            Parameter parameter = parameters[mark];
            // 判断是否是基本类型
            if(ReflectUtils.isPrimitive(parameter.getType())){
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
                    ExportSubSelection subSelection = field.getAnnotation(ExportSubSelection.class);
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
        pageArgs.setArgs(jp.getArgs());
        return pageArgs;
    }


}
