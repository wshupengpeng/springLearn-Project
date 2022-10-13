package com.spring.excel.aop;

import com.spring.excel.support.AbstractExcelRuler;
import com.spring.excel.support.AnnotationDefintion;
import com.spring.excel.support.interfaces.ExcelExecutor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AbstractExcelRuler abstractExcelRuler;

    @Pointcut("@annotation(com.spring.excel.ExportExcel)")
    public void pointCut(){
    }

    @Around(value = "pointCut()")
    public void exportProcessor(JoinPoint jp){
        AnnotationDefintion defintion = new AnnotationDefintion(jp);
        ExcelExecutor executor = abstractExcelRuler.match(defintion);
        executor.execute(defintion);
    }


}
