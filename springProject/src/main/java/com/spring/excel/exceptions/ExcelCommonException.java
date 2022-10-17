package com.spring.excel.exceptions;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/17 16:32
 */
public class ExcelCommonException extends RuntimeException{

    public ExcelCommonException() {
    }

    public ExcelCommonException(String message) {
        super(message);
    }

    public ExcelCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelCommonException(Throwable cause) {
        super(cause);
    }

    public ExcelCommonException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
