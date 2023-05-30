package com.spring.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/29 16:41
 */
@Data
@Configuration
public class ValueConfig {

    @Value("${value.name}")
    private String name;

    @Value("${value.age}")
    private int age;

}
