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
            Annotation[] annotations = declaredField.getDeclaredAnnotations();
            for (Annotation an : annotations) {
                if(an.annotationType() == annotation){
                    searchList.add(declaredField);
                }
            }
        }
        return searchList;
    }

    /**
     * 判断当前类型是否是基础类型或者基础类型包装类
     * @param clz
     * @return
     */
    public static boolean isPrimitive(Class clz){
        try {
            return clz.isPrimitive() || ((Class)clz.getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }
}
