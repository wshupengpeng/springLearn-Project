package com.spring.resolver.controller;

import com.alibaba.fastjson.JSONObject;
import com.spring.resolver.annotation.CurLogin;
import com.spring.resolver.param.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @creater hpp
 * @Date 2023/5/14-19:08
 * @description:
 */
@RestController
@RequestMapping("/resolver")
public class ResolverController {

    @RequestMapping("/resolver")
    public String resolverRequest(@CurLogin LoginUserInfo loginUserInfo){
        return JSONObject.toJSONString(loginUserInfo);
    }


}
