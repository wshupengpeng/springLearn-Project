package com.spring;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.spring.config.AutoProperties;

public class InnerService {
    protected InnerService(){}

    public void print(){
        System.out.println("print");
    }

}
