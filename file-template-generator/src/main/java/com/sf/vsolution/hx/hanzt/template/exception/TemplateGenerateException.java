package com.sf.vsolution.hx.hanzt.template.exception;

/**
 * @Description: 模板生成异常，用于模板生成的异常报错
 * @Author 01415355
 * @Date 2023/5/9 14:23
 */
public class TemplateGenerateException extends RuntimeException{

    public TemplateGenerateException() {
    }

    public TemplateGenerateException(String message) {
        super(message);
    }

    public TemplateGenerateException(Throwable cause) {
        super(cause);
    }

}
