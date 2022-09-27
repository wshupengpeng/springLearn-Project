package com.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "prefix-app")
@Configuration
@Data
public class AutoConfigurationProperties {
    private String name;

    @PostConstruct
    public void test() {
        System.out.println("name:" + name);
    }
}
