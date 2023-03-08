package com.export.test;

import lombok.Data;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/7 11:33
 */
@Data
public class BeanUtilsTest {

    private String name = "123";

    public void copyProperties(){
        BeanUtilsTest beanUtilsTest = new BeanUtilsTest();
        beanUtilsTest.setName("234");
        BeanUtilsTest beanUtilsTest1 = new BeanUtilsTest();
    }
}
