package com.java;

import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Description: 测试通过父注解类型能否拿到子注解类型
 * @Author 01415355
 * @Date 2022/10/13 14:36
 */
@ChildAnnotation
public class TestAnnotation {
    public static void main(String[] args) {
        Annotation[] annotations = TestAnnotation.class.getAnnotations();
        for (Annotation annotation : annotations) {
            if(annotation.annotationType() == ParentAnnotation.class){
                System.out.println("parent:" + annotation);
            }else{
                Annotation[] annotations1 = annotation.annotationType().getAnnotations();
                for (Annotation annotation1 : annotations1) {
                    if (annotation1.annotationType() == ParentAnnotation.class) {
                        System.out.println("childer:" + annotation);
                    }
                }
            }
        }
    }

    public void test(@ParamterType String str){

    }

    @Test
    public void searchParamterAnnotation(){
        Method[] methods = TestAnnotation.class.getMethods();
        for (Method method : methods) {
            if(method.getName().equals("test")){
                Parameter[] parameters = method.getParameters();
                for (Parameter parameter : parameters) {
                    Annotation[] annotations = parameter.getAnnotations();
                    for (Annotation annotation : annotations) {
                        if(annotation.annotationType() == ParamterType.class){
                            Class<?> type = parameter.getType();
                            System.out.println(type.isPrimitive());
                            System.out.println(parameter);
                        }
                    }
                }
            }
        }
    }


    public static void searchAnnotation(Class<?> clz, Class<?> targetAnnotation){
        Annotation[] annotations = clz.getAnnotations();
        for (Annotation annotation : annotations) {

        }
    }



}

@Target(ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@interface ParamterType{

}

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@interface ParentAnnotation{

}

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ParentAnnotation
@interface ChildAnnotation{

}
