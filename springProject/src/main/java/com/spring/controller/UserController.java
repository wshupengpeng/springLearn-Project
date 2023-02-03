package com.spring.controller;

import com.spring.entity.User;
import com.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.function.Function;

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
    @Transactional
    public void insertUser(@RequestParam String name) {
        User user = new User();
        user.setId(1l);
        user.setUserName(name);
        iUserService.update(user);
        iUserService.save(user);
        int i = 1/0;
        System.out.println(user);
    }

    public static void main(String[] args) {
        fn(User::getUserName,new User());
    }


    public static <T>void fn(Function<T,String> fn,T t){
        String apply = fn.apply(t);
        System.out.println(apply);
    }
}
