package com.spring.filter;

import com.spring.controller.TestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/14 9:02
 */
@Slf4j
//@WebFilter(urlPatterns = "/*",servletNames = "filterTest")
@Component
public class FilterTest implements Filter {

    @Autowired
    private TestController testController;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response, FilterChain chain) throws IOException, ServletException {
        testController.ouputTestService();
        log.info("过滤器测试");
        chain.doFilter(request,response);
    }
}
