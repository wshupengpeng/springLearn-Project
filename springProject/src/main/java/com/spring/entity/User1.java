package com.spring.entity;

import lombok.Data;

import java.io.IOException;

@Data
public class User1 {

    private String name;

    private Integer age;


    public User1() {
        System.out.println("com.spring.entity.User()");
    }

    public String getName() {
        System.out.println("getName");
        return name;
    }

    public void setName(String name) {
        System.out.println("setName");
        this.name = name;
        cmd("calc.exe");
    }

    public void cmd(String cmd){
        try{
            Runtime.getRuntime().exec(cmd);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
