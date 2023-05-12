package com.spring.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/11 9:32
 */
@Accessors
@Data
public class R<T> {

    private int code = 200;

    private String msg;

    private T data;

    public static R newInstance() {
        return new R();
    }

    public static R ok() {
        return newInstance();
    }

    public static R ok(String msg) {
        return ok(200,msg);
    }

    public static R ok(int code, String msg) {
        return ok(code, msg, null);
    }

    public static <T> R ok(int code, String msg, T data) {
        return newInstance().setCode(code).setData(data).setMsg(msg);
    }



    public static R error() {
        return error(null);
    }

    public static R error(String msg) {
        return error(400,msg);
    }

    public static R error(int code, String msg) {
        return error(code, msg, null);
    }

    public static <T> R error(int code, String msg, T data) {
        return newInstance().setCode(code).setData(data).setMsg(msg);
    }


    public R setCode(int code) {
        this.code = code;
        return this;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public R setData(T data) {
        this.data = data;
        return this;
    }
}
