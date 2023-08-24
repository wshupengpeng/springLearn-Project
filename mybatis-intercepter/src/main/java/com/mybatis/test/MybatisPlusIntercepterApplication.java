package com.mybatis.test;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @creater hpp
 * @Date 2023/8/21-20:26
 * @description:
 */
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = "com.mybatis.test.mapper")
public class MybatisPlusIntercepterApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusIntercepterApplication.class);
    }
}
