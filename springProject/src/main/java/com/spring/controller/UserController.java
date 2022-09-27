package com.spring.controller;

import com.spring.entity.User;
import com.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hpp
 * @since 2022-09-15
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @RequestMapping("/insertUser")
    public void insertUser(@RequestParam String name) {
        User user = new User();
        user.setUserName(name);
        iUserService.save(user);
        System.out.println(user);
    }
}
