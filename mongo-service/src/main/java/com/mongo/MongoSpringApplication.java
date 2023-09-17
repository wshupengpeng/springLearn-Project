package com.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @creater hpp
 * @Date 2023/7/19-19:12
 * @description:
 */
@SpringBootApplication
@Transactional
public class MongoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoSpringApplication.class);
    }


}
