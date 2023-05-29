package com.spring.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @creater hpp
 * @Date 2023/5/16-22:54
 * @description:
 */
public class ReflectUtils {


    public static boolean hasAnnotation(Field field, Class<?> annotationClz) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation.getClass().isAssignableFrom(annotationClz)){
                return true;
            }
        }
        return false;
    }


    public static Annotation getFieldAnnotation(Field field, Class<?> annotationClz) {
        Annotation[] annotations = field.getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation.getClass().isAssignableFrom(annotationClz)){
                return annotation;
            }
        }
        return null;
    }
//    public static boolean hasAnnotation(Class aClass, Class<?> target) {
//        Field[] declaredFields = aClass.getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            if(hasAnnotation(declaredField,target)){
//
//            }
//        }
////        Annotation[] annotations = field.getAnnotations();
////        for (Annotation annotation : annotations) {
////            if(annotation.getClass().isAssignableFrom(annotationClz)){
////                return true;
////            }
////        }
//        return false;
//    }
}
