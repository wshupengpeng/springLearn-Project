package com.spring.excel.utils;

import com.spring.excel.annotation.ExportField;
import com.spring.excel.pojo.FieldEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
            for (FieldEntity fieldEntity : sortedFieldList) {
                for (Field declaredField : declaredFields) {
                    declaredField.setAccessible(true);
                    String strValue = "";
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
                    }
                    row.add(strValue);
                }
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
        return  fieldEntityList.stream()
                .sorted(Comparator.comparing(FieldEntity::getOrder)).collect(Collectors.toList());
    }


}
