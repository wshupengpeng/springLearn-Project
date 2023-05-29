package com.spring.environment.param;

import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Field;

/**
 * @creater hpp
 * @Date 2023/5/29-21:45
 * @description:
 */
@Data
public class RefreshField {
    private static PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}",
            ":", true);

    /**
     *  spring 容器实例bean
     */
    Object bean;

    /**
     * @value注解标识属性
     */
    Field field;

    /**
     *  占位符
     */
    String value;


    public void updateValue(Environment environment){
        boolean accessible = field.isAccessible();
        if(!accessible){
            field.setAccessible(Boolean.TRUE);
        }
        String updateVal = propertyPlaceholderHelper.replacePlaceholders(value, environment::getProperty);


    }

}
