package com.spring;

import com.spring.config.AutoProperties;
import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan("com.spring.**")
@EnableConfigurationProperties
@ServletComponentScan("com.spring")
public class ApplicationStarter {
    @Autowired
    private AutoProperties autoProperties;

    public static void main(String[] args) {
        new SpringApplication().run(ApplicationStarter.class);

    }

    @PostConstruct
    public void test(){
        System.out.println("name:" + autoProperties.getName());
    }

    @Test
    public void testStopWatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            Thread.sleep(1000l);
            stopWatch.stop();
            System.out.println("stop watch total:" + stopWatch.getTotalTimeMillis());
            stopWatch.start();
            Thread.sleep(1000l);
            stopWatch.stop();
            System.out.println("stop watch total:" + stopWatch.getTotalTimeMillis());
        } catch (Exception e) {

        }
    }

}
