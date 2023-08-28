package com.log;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class LogMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogMonitorApplication.class);
    }
}
