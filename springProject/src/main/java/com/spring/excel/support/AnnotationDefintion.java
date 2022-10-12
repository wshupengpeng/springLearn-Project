package com.spring.excel.support;

import com.alibaba.excel.write.handler.WriteHandler;
import com.spring.excel.annotation.ExportExcel;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 15:23
 */
@Data
public class AnnotationDefintion {

    private JoinPoint jp;

    private ExportExcel exportAnnotation;

    private MethodSignature methodSignature;

    private List<WriteHandler> writeHandlerList = new ArrayList<>();

    public AnnotationDefintion(JoinPoint jp) {
        this(jp,null,null);
    }

    public AnnotationDefintion(JoinPoint jp, ExportExcel exportAnnotation, MethodSignature methodSignature) {
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

    public List<WriteHandler> addWriterHandler(WriteHandler writeHandler){
        writeHandlerList.add(writeHandler);
        return writeHandlerList;
    }
}
