package com.spring.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.spring.entity.User;
import org.junit.Test;

/**
 *  验证fastjson漏洞：@type
 */
public class FastJsonLoophole {

    @Test
    public void verify(){
//        ParserConfig.getGlobalInstance().setSafeMode(false);
        ParserConfig.getGlobalInstance().addAccept("com.spring.entity.");
//        String x = "{\"name\": \"test\"}";
//        Object xx = JSON.parse(x);
//        System.out.println(xx);
//        System.out.println();

        String y = "{\"@type\":\"com.spring.entity.User\",\"name\":\"test\"}";
        User yy = (User) JSON.parse(y);
//        System.out.println(yy);
//        System.out.println();

//        String z = "{\"name\": \"test\"}";
//        User zz = (User) JSON.parseObject(z, User.class);
//        System.out.println(zz);
    }

    @Test
    public void testString(){
        System.out.println(JSONObject.toJSONString("123", SerializerFeature.WriteNonStringValueAsString));
    }

}
