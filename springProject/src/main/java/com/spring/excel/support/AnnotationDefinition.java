package com.spring.excel.support;

import com.spring.excel.annotation.ExportExcel;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Optional;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 15:23
 */
@Data
public class AnnotationDefinition {

    private JoinPoint jp;

    private ExportExcel exportAnnotation;

    private MethodSignature methodSignature;


    public AnnotationDefinition(JoinPoint jp) {
        this(jp,null,null);
    }

    public AnnotationDefinition(JoinPoint jp, ExportExcel exportAnnotation, MethodSignature methodSignature) {
        this.jp = jp;
        this.methodSignature = Optional.ofNullable(methodSignature).orElseGet(this::extraMethodSignature);
        this.exportAnnotation = Optional.ofNullable(exportAnnotation).orElseGet(this::extraAnnotation);
    }

    public MethodSignature extraMethodSignature(){
        return (MethodSignature) this.jp.getSignature();
    }

    public ExportExcel extraAnnotation(){
        return this.methodSignature.getMethod().getAnnotation(ExportExcel.class);
    }

}
