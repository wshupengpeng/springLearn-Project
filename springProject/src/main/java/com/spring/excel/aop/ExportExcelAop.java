package com.spring.excel.aop;

import com.spring.entity.ResponseResult;
import com.spring.excel.processor.AbstractExcelPostProcessor;
import com.spring.excel.support.AnnotationDefinition;
import com.spring.excel.support.ExcelRulerHolder;
import com.spring.excel.support.interfaces.ExcelExecutor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @creater hpp
 * @Date 2022/10/9-23:17
 * @description:
 */
@Aspect
@Component
@Slf4j
public class ExportExcelAop {
    @Autowired
    private ExcelRulerHolder excelRulerHolder;

    @Autowired
    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.spring.excel.annotation.FilterAnnotation)")
    public void pointCut(){
    }

    @Around(value = "pointCut()")
    public Object export(ProceedingJoinPoint jp){
        try {
            AnnotationDefinition defintion = new AnnotationDefinition(jp);
            invokePostProcesser(defintion);
            ExcelExecutor executor = excelRulerHolder.match(defintion);
            executor.execute(defintion);
        }catch (Exception e){
            log.error("导出文件失败，失败原因为：",e);
            return ResponseResult.error("导出文件失败");
        }

        return null;
    }

    private void invokePostProcesser(AnnotationDefinition defintion) {
        Class<? extends AbstractExcelPostProcessor>[] postProcessors = defintion.getExportAnnotation().postProcessor();
        for (Class<? extends AbstractExcelPostProcessor> postProcessor : postProcessors) {
            AbstractExcelPostProcessor processor = applicationContext.getBean(postProcessor);
            processor.postProcessBeforeWrite(defintion);
        }
    }


}
