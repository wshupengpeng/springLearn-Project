package com.spring.interceptor;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/17 11:18
 */
@Configuration
public class WebCongfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorTest interceptorTest;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorTest).addPathPatterns("/**");
    }

}
