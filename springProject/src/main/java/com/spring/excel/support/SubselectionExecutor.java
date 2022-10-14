package com.spring.excel.support;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.excel.annotation.ExportSubSelection;
import com.spring.excel.pojo.FieldEntity;
import com.spring.excel.pojo.PageArgs;
import com.spring.excel.support.interfaces.ExcelExecutor;
import com.spring.excel.utils.ExcelUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @creater hpp
 * @Date 2022/10/13-21:29
 * @description:
 */
public class SubselectionExecutor implements ExcelExecutor {
    @Override
    public void execute(AnnotationDefintion defintion) {
        Class<?> returnType = defintion.getExportAnnotation().beanClass();
        ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
        Object[] args = jp.getArgs();
        try {
            Object proceed = jp.proceed();
            if(proceed instanceof Page){
                List<FieldEntity> parse = ExcelUtils.parseHead(returnType);
                List<List<String>> headList = parse.stream()
                        .map(field-> Arrays.asList(field.getName()))
                        .collect(Collectors.toList());
                Page page = (Page) proceed;
                List records = page.getRecords();
                List<List<String>> dataList = ExcelUtils.parseData(records, parse);

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public PageArgs parsePage(AnnotationDefintion defintion){
        ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
        MethodSignature methodSignature = defintion.getMethodSignature();
        Parameter[] parameters = methodSignature.getMethod().getParameters();

        for (Parameter parameter : parameters) {
            // 判断是否是基本类型
            if(parameter.getType().isPrimitive()){
                Annotation[] annotations = parameter.getAnnotations();
                for (Annotation annotation : annotations) {
                    if(annotation.annotationType() == ExportSubSelection.class ){

                    }
                }
            }else{

            }
        }



        return null;
    }



}
