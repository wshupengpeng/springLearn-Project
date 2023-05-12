package com.spring.advice;

import com.alibaba.fastjson.JSONObject;
import com.spring.exception.CutomSysCode;
import com.spring.exception.SysException;
import com.spring.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/11 9:31
 */
@Slf4j
@RestControllerAdvice
@ControllerAdvice
public class ErrorControllerAdvice {

    /**
     * 捕获@Validate校验抛出的异常
     *
     * @param e        MethodArgumentNotValidException异常信息
     * @param request  HttpServletRequest
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public R validException2Handler(BindException e, HttpServletRequest request) {
        SysException ex = parseBindingResult(e.getBindingResult());
        log.error("请求地址: {} . 异常信息: {}", request.getServletPath(), ex.getMessage());
        return R.newInstance().setCode(CutomSysCode.ILLEGAL_PARAMETER.getCode()).setMsg(e.getBindingResult().getFieldError().getDefaultMessage());
    }


    /**
     * 提取Validator产生的异常错误
     *
     * @param bindingResult BindingResult
     * @return 返回异常信息
     */
    private SysException parseBindingResult(BindingResult bindingResult) {
        Map<String, String> errorMsgs = new HashMap<String, String>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMsgs.put(error.getField(), error.getDefaultMessage());
        }
        if (errorMsgs.isEmpty()) {
            return new SysException(CutomSysCode.ILLEGAL_PARAMETER.getMsg());
        } else {
            return new SysException(JSONObject.toJSONString(errorMsgs).replace("\"", ""));
        }
    }
}
