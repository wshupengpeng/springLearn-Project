package com.spring.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.environment.config.RefreshEvent;
import com.spring.environment.config.RefreshValueBeanPostProcessor;
import com.spring.environment.config.ValueDependencyInjection;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Map;

/**
 * @creater hpp
 * @Date 2023/5/30-2:12
 * @description:
 */
@RestController
@RequestMapping("/refresh")
@Slf4j
public class RefreshController {

    @Autowired
    private ApplicationEventPublisher publisher;


    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @Autowired
    ValueDependencyInjection valueDependencyInjection;



    @RequestMapping("/event")
    public String refreshEvent(String key,String value){
        log.info("refresh before:{}", JSONObject.toJSONString(valueDependencyInjection));
        String name = "applicationConfig: [classpath:/application.yml]";


        MapPropertySource propertySource = (MapPropertySource) applicationContext.getEnvironment().getPropertySources().get(name);

        Map<String, Object> source = propertySource.getSource();

        source.put(key,value);

        applicationContext.getEnvironment().getPropertySources().replace(name,new MapPropertySource(name,source));

        publisher.publishEvent(new RefreshEvent(new Object(),key));

        return JSONObject.toJSONString(valueDependencyInjection);
    }

    @RequestMapping("/apolloBeanFactory")
    public String apolloBeanFactory(String placeHolder,String beanName){
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        String strVal = beanFactory.resolveEmbeddedValue(placeHolder);
        BeanExpressionResolver beanExpressionResolver = beanFactory.getBeanExpressionResolver();
        Object stringVal = getStringVal(beanName, beanFactory, strVal, beanExpressionResolver);
        TypeConverter typeConverter = beanFactory.getTypeConverter();
        stringVal = typeConverter.convertIfNecessary(stringVal, String.class);
        return stringVal.toString();
    }

    @Nullable
    private Object getStringVal(String beanName, ConfigurableListableBeanFactory beanFactory, String strVal, BeanExpressionResolver beanExpressionResolver) {
        if(beanExpressionResolver == null){
            return strVal;
        }else{
            BeanDefinition beanDefinition = beanFactory.containsBean(beanName) ? beanFactory.getMergedBeanDefinition(beanName) : null;
            Scope scope = beanDefinition != null ? beanFactory.getRegisteredScope(beanDefinition.getScope()) : null;
            return beanFactory.getBeanExpressionResolver().evaluate(strVal, new BeanExpressionContext(beanFactory, scope));
        }
    }
}
