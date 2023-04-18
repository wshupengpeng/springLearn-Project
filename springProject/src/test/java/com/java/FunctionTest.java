package com.java;

import org.apache.catalina.core.ApplicationContext;
import org.apache.catalina.core.StandardContext;
import org.junit.Test;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/14 16:35
 */
public class FunctionTest {

    @Test
    public void getFunction() throws ServletException {
        FunctionA lambda = getLambda();

        String s = lambda.toDo();

        System.out.println(s);


    }


    public FunctionA getLambda(){
        return this::test;
    }

    public String test(){
        System.out.println("nothing to do");
        return "";
    }

}

@FunctionalInterface
interface FunctionA{

    String toDo();
}


