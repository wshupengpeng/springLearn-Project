package com.mongo.dto;

import lombok.Data;

/**
 * @creater hpp
 * @Date 2023/8/21-20:27
 * @description:
 */
@Data
public class R<T> {

    private int code;

    private String msg;

    private boolean isSuccess;

    private T data;

    public static R getInstance() {
        return new R();
    }

    public R setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public R setCode(int code) {
        this.code = code;
        return this;
    }

    public R setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> R success(int code, String msg, T data) {
        return getInstance().setSuccess(Boolean.TRUE).setCode(code).setMsg(msg).setData(data);
    }

    public static <T> R success(T data) {
        return success(200, null, data);
    }

    public static R success(int code) {
        return success(code, null, null);
    }

    public static R success() {
        return success(null);
    }

    public static R error(int code, String msg) {
        return getInstance().setSuccess(Boolean.FALSE).setCode(code).setMsg(msg);
    }

    public static R error(String msg) {
        return error(400, msg);
    }


    public static R error(int code) {
        return error(code, null);
    }

    public static R error() {
        return error(400);
    }
}
