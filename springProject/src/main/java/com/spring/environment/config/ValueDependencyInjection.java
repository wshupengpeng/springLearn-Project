package com.spring.environment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import lombok.Data;

import javax.annotation.PostConstruct;

/**
 * @creater hpp
 * @Date 2023/5/15-22:39
 * @description: 测试@Value注入方式
 */
//@Configuration
@Slf4j
@Component
@Data
public class ValueDependencyInjection {
    @Value("${value.dependency.injection.name}")
    private String name;
    @Value("${value.dependency.injection.addr}")
    private String addr;

    public ValueDependencyInjection() {
        log.info("ValueDependencyInjection, name:{},addr:{}", name, addr);
    }

    @PostConstruct
    public void printValueProperty() {
        log.info("printValueProperty, name:{},addr:{}", name, addr);
    }
}
