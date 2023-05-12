package com.spring.exception;

import lombok.Data;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/11 9:41
 */
public enum  CutomSysCode {
    ok(200,"正常访问"),
    exception(0,"未知异常"),
    ILLEGAL_PARAMETER(401,"非法参数"),
    unauthorized(100,"未授权访问");

    CutomSysCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
