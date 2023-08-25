package com.mybatis.test.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.mybatis.test.interceptr.DataChangeRecorderInnerInterceptor;
import com.mybatis.test.interceptr.LogIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/20 15:46
 */
@Configuration
public class MybaitsConfig {


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(LogIntercepter logIntercepter) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //向Mybatis过滤器链中添加分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        interceptor.addInnerInterceptor(logIntercepter);
//        interceptor.addInnerInterceptor(new DataChangeRecorderInnerInterceptor());
        //还可以添加其他的拦截器
        return interceptor;
    }

}
