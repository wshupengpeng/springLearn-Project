package com.spring.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/14 18:04
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(FilterTest filterTest) {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(filterTest);
        bean.addUrlPatterns("/*");
        return bean;
    }



//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean bean = new FilterRegistrationBean();
//        bean.setFilter(new FilterTest());
//        bean.addUrlPatterns("/*");
//        return bean;
//    }
}
