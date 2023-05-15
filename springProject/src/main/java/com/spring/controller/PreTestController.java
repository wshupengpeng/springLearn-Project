package com.spring.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @creater hpp
 * @Date 2023/5/14-14:04
 * @description: 拦截器拦截多次的原因是： 因为controller不是restController，因此会有静态资源进行请求，导致拦截器多次拦截
 */
@RestController
@RequestMapping("/pre")
@Slf4j
public class PreTestController {

    @RequestMapping("/preTest")
    public String preTest(@RequestParam String name) {
        log.info("PreTestController#preTest name:{}", name);
        return JSONObject.toJSONString(name);
    }


    @PostMapping("/preTest1")
    public String preTest1(@RequestParam MultipartFile file) {
        log.info("preTest1,form submit file");
        return "success";
    }

}
