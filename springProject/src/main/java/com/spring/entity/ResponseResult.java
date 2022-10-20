package com.spring.entity;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/19 16:47
 */
public class ResponseResult<T> {

    private Integer code;

    private String msg;

    private T data;

    private boolean success;

    private static final Integer SUCCESS_CODE = 200;

    private static final Integer ERROR_CODE = 400;

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private static <T> ResponseResult<T> newInstance(T data) {
        return new ResponseResult(null, null, data);
    }

    public ResponseResult(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> error(String msg) {
        return error(ERROR_CODE, msg);
    }
    public static <T> ResponseResult<T> error(Integer code) {
        return error(code, null);
    }

    public static <T> ResponseResult<T> error(Integer code, String msg) {
        return error(code, msg, null);
    }

    public static <T> ResponseResult<T> error(Integer code, String msg, T data) {
        return newInstance(data).setCode(code).setMsg(msg).setData(data).setSuccess(false);
    }
    public static <T> ResponseResult<T> ok() {
        return ok(SUCCESS_CODE, null);
    }
    public static <T> ResponseResult<T> ok(T t) {
        return ok(SUCCESS_CODE, t);
    }

    public static <T> ResponseResult<T> ok(Integer code) {
        return ok(code, null);
    }

    public static <T> ResponseResult<T> ok(Integer code, T data) {
        return ok(code, null, data);
    }

    public static <T> ResponseResult<T> ok(Integer code, String msg, T data) {
        return newInstance(data).setCode(code).setMsg(msg).setData(data).setSuccess(true);
    }

    public ResponseResult<T> setCode(Integer code){
        this.code = code;
        return this;
    }

    public ResponseResult<T> setMsg(String msg){
        this.msg = msg;
        return this;
    }

    public ResponseResult<T> setData(T data){
        this.data = data;
        return this;
    }

    public ResponseResult<T> setSuccess(boolean success){
        this.success = success;
        return this;
    }
}
