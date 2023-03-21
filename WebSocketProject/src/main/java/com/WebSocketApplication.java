package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/2/3 9:57
 */
@SpringBootApplication
@EnableScheduling
public class WebSocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class);
    }
}
