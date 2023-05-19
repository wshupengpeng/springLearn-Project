package com.spring.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/10 14:59
 */
@RestController
@RequestMapping("/request")
public class RequestController {


    @PostMapping(value = "/formData/1")
    public String formData(@Valid User user) {

        return "success";
    }

    @PostMapping(value = "/formData/2")
    public String formData2(@RequestBody User user) {
        return JSONObject.toJSONString(user);
    }

    @PostMapping(value = "/formData/3")
    public String formData(String name, String age) {
        return name + age;
    }

}
@Data
class User {
    @NotNull(message = "名字不能为空")
    private String name;

    private int age;

    private MultipartFile multipartFile;
}
