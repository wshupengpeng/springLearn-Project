package com.spring.environment.config;

import com.spring.environment.resolver.ValuePlaceHolderResolver;
import com.spring.util.ReflectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @creater hpp
 * @Date 2023/5/16-22:41
 * @description:
 *  此类用于动态刷新value的前置解析类，主要功能有:
 *  1 获取含有@value的bean对象 （用于销毁或替换）
 *  2 获取容器上下文对象 （可以获取beanfactory,用于重新实例化对象）
 *  3 提供refresh接口,可以用于热替换对象
 */
@Component
public class RefreshValueBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, EnvironmentAware {

//    @Autowired
//    private AnnotationConfigApplicationContext applicationContext;

    private ApplicationContext applicationContext;

    private Environment environment;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 1 判断当前对象是否含有@value的注解
        Class<?> aClass = bean.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if(ReflectUtils.hasAnnotation(declaredField, Value.class)){
                // 2 如果含有,则将其存入到内存中
                Value value = declaredField.getDeclaredAnnotation(Value.class);
                String placeHolder = ValuePlaceHolderResolver.parseValuePlaceHolder(value.value());
            }
        }


        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
