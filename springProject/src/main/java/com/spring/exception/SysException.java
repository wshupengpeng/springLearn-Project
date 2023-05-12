package com.spring.exception;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/11 9:40
 */
public class SysException extends RuntimeException {


    public SysException() {
    }

    public SysException(String message) {
        super(message);
    }

    public SysException(String message, Throwable cause) {
        super(message, cause);
    }

    public SysException(Throwable cause) {
        super(cause);
    }

    public SysException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
