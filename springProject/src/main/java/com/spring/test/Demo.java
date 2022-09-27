package com.spring.test;

import com.alibaba.fastjson.JSONObject;
import com.spring.InnerService;
import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class Demo {

    @Test
    public void format(){
        Date time = Calendar.getInstance().getTime();
        System.out.println(time);
    }

    @Test
    public void test(){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost");
        uriComponentsBuilder.queryParam("name","1 2");
        System.out.println(uriComponentsBuilder.toUriString());
        InnerService innerService = new InnerService(){};
        innerService.print();
    }
    @Test
    public void sign(){
        String body = "{\n" +
                "    \"receiveTel\": \"15310276488\",\n" +
                "    \"sendCity\": \"重庆市\",\n" +
                "    \"monthlyCust\": \"\",\n" +
                "    \"sendProvince\": \"重庆市\",\n" +
                "    \"sendStartTime\": \"\",\n" +
                "    \"remark\": \"\",\n" +
                "    \"type\": 0,\n" +
                "    \"productName\": \"其他\",\n" +
                "    \"packageNumber\": 1,\n" +
                "    \"receiveName\": \"谢进军\",\n" +
                "    \"payType\": 2,\n" +
                "    \"orgCode\": \"WB0022082416001\",\n" +
                "    \"sendMobile\": \"15310276488\",\n" +
                "    \"productCodes\": [\n" +
                "        \"9\"\n" +
                "    ],\n" +
                "    \"receiveMobile\": \"15310276488\",\n" +
                "    \"sendName\": \"王女士\",\n" +
                "    \"receiveAddress\": \"学府大道渝能国际\",\n" +
                "    \"sendCounty\": \"渝北区\",\n" +
                "    \"expressType\": 1,\n" +
                "    \"bspType\": 1,\n" +
                "    \"productWeight\": \"1\",\n" +
                "    \"receiveProvince\": \"重庆市\",\n" +
                "    \"sendAddress\": \"新江与城\",\n" +
                "    \"sendTel\": \"15310276488\",\n" +
                "    \"receiveCity\": \"重庆市\",\n" +
                "    \"customerOrderNo\": \"SY20220906740599\",\n" +
                "    \"receiveCounty\": \"南岸区\"\n" +
                "}";
        body = JSONObject.parseObject(body).toJSONString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String format = formatter.format(LocalDateTime.now());
        System.out.println(format);
        String content = String.join("|",body,"8d8d5fe328894fff8bb6bdea8fa42832",format);
        String sign = encryptMD5(content);
        System.out.println("content:" + content);
        System.out.println("sign :" + sign);
    }

    public String encryptMD5(String content){
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            return null;
        }
        byte [] input = content.getBytes(StandardCharsets.UTF_8);
        byte [] output = md.digest(input);
        return Base64.getUrlEncoder().encodeToString(output);
    }
}
