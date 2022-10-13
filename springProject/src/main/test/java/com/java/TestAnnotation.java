package com.java;

import java.lang.annotation.*;

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

    public static void searchAnnotation(Class<?> clz, Class<?> targetAnnotation){
        Annotation[] annotations = clz.getAnnotations();
        for (Annotation annotation : annotations) {

        }
    }



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
