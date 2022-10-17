package com.spring.excel.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/17 17:42
 */
public class ReflectUtils {

    public static List<Field> getFieldByAnnotation(Class<?> clz, Class<?> annotation){
        List<Field> searchList = new ArrayList<>();
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Annotation[] annotations = declaredField.getAnnotations();
            for (Annotation an : annotations) {
                if(an.getClass() == annotation){
                    searchList.add(declaredField);
                }
            }
        }
        return searchList;
    }
}
