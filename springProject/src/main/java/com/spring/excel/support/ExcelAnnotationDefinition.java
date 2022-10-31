package com.spring.excel.support;

import com.alibaba.excel.write.handler.WriteHandler;
import com.spring.excel.annotation.ExportExcel;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/21 16:42
 */
@Data
public class ExcelAnnotationDefinition extends AnnotationDefinition{
    private List<WriteHandler> writeHandlerList = new ArrayList<>();

    public ExcelAnnotationDefinition(JoinPoint jp) {
        super(jp);
    }

    public ExcelAnnotationDefinition(JoinPoint jp, ExportExcel exportAnnotation, MethodSignature methodSignature) {
        super(jp, exportAnnotation, methodSignature);
    }

    public List<WriteHandler> addWriterHandler(WriteHandler writeHandler){
        writeHandlerList.add(writeHandler);
        return writeHandlerList;
    }
}
