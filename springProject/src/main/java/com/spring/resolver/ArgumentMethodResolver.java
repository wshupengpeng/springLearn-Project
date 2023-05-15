package com.spring.resolver;

import com.alibaba.fastjson.JSONObject;
import com.spring.resolver.annotation.CurLogin;
import com.spring.resolver.param.LoginUserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @creater hpp
 * @Date 2023/5/14-19:03
 * @description:
 */
public class ArgumentMethodResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurLogin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 1 token
        String token = webRequest.getHeader("token");
        // 解析token
        return JSONObject.parseObject(token, LoginUserInfo.class);
    }
}
