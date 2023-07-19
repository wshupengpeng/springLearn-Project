package com.spring;

import com.spring.config.AutoProperties;
import lombok.Data;
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
        SpringApplication.run(ApplicationStarter.class);

    }

    @PostConstruct
    public void test() {
        System.out.println("name:" + autoProperties.getName());
    }
    @Data
    class CpbmPullOrderResponse{
        int pageCount;

    }

    @Test
    public void testStopWatch() {
        CpbmPullOrderResponse cpbmPullOrderResponse = new CpbmPullOrderResponse();
        int batchJobNum = 1000;


//        int jobNum = (int) Math.ceil(cpbmPullOrderResponse.getPageCount() / 1000);

        // ======== 分割线 ========
        int jobNum = (int) Math.ceil(cpbmPullOrderResponse.getPageCount() / 1000d);


        System.out.println(jobNum);
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        try {
//            Thread.sleep(1000L);
//            stopWatch.stop();
//            System.out.println("stop watch total:" + stopWatch.getTotalTimeMillis());
//            stopWatch.start();
//            Thread.sleep(1000L);
//            stopWatch.stop();
//            System.out.println("stop watch total:" + stopWatch.getTotalTimeMillis());
//        } catch (Exception e) {
//
//        }
    }

}
