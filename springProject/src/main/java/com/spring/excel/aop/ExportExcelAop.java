package com.spring.excel.aop;

import com.spring.excel.support.AnnotationDefintion;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @creater hpp
 * @Date 2022/10/9-23:17
 * @description:
 */
@Aspect
@Component
public class ExportExcelAop {

    @Pointcut("@annotation(com.spring.excel.ExportExcel)")
    public void pointCut(){
    }

    @Around(value = "pointCut()")
    public void exportProcessor(JoinPoint jp){
        AnnotationDefintion defintion = new AnnotationDefintion(jp);
        parse(defintion);
    }

    private void parse(AnnotationDefintion defintion) {


    }

}
