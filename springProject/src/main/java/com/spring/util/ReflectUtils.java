package com.spring.util;

import com.spring.environment.config.ValueDependencyInjection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @creater hpp
 * @Date 2023/5/16-22:54
 * @description:
 */
public class ReflectUtils {


    public static boolean hasAnnotation(Field field, Class<?> annotationClz) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation.annotationType().isAssignableFrom(annotationClz)){
                return true;
            }
        }
        return false;
    }
//    public static void main(String[] args) {
//        Class<? extends Class> aClass = ValueDependencyInjection.class.getClass();
//        Field[] declaredFields = aClass.getDeclaredFields();
//
//        for (Field declaredField : declaredFields) {
//            Annotation[] declaredAnnotations = declaredField.getDeclaredAnnotations();
//
//            for (Annotation declaredAnnotation : declaredAnnotations) {
//                declaredAnnotation.annotationType().isAssignableFrom(Value.class)
//            }
//        }
//    }


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
