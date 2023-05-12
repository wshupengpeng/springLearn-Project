package com.spring.annotation;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/8 9:36
 */
@Data
@Slf4j
@Component
public class OrderAnnotationTest implements ApplicationContextAware {
    @Autowired
    List<OrderTest> orderTestList;


    @PostConstruct
    public void orderPrint(){
        log.info("orderList sort:{}", JSONObject.toJSONString(orderTestList));
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("orderList sort:{}",JSONObject.toJSONString(orderTestList));

    }
}

@Order(value = 4)
@Component
@Data
class OrderTest{
 private String firstOrder = "firstOrder";
}

@Order(value = 2)
@Component
@Data
class SecondTest extends OrderTest{
    private String firstOrder = "Second";
}

@Order(value = 3)
@Component
@Data
class ThirdTest extends OrderTest{
    private String firstOrder = "third";
}
