package com.spring.controller;

import com.spring.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class TestController {
    final List<TestService> testServiceList;

    @PostConstruct
    public void ouputTestService(){
        for (TestService testService : testServiceList) {
            System.out.println(testService);
        }
    }
}
