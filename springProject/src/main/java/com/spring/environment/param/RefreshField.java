package com.spring.environment.param;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;

/**
 * @creater hpp
 * @Date 2023/5/29-21:45
 * @description:
 */
@Data
@Slf4j
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

    public RefreshField(Object bean, Field field, String value) {
        this.bean = bean;
        this.field = field;
        this.value = value;
    }

    public void updateValue(Environment environment){
        boolean accessible = field.isAccessible();
        if(!accessible){
            field.setAccessible(Boolean.TRUE);
        }
        String placeHolder = String.format("%s%s%s","${",value,"}");
        String updateVal = propertyPlaceholderHelper.replacePlaceholders(placeHolder, environment::getProperty);
        log.info("refresh value successfully,newValue：{},key:{}", updateVal, value);
        try {
            if(field.getType() == String.class){
                field.set(bean, updateVal);
            }else{
                field.set(bean, JSONObject.parseObject(updateVal,field.getType()));
            }
        }catch (Exception e){

        }

    }

}
