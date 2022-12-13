package com.spring.excel.support;

import com.spring.excel.exceptions.ExcelCommonException;
import com.spring.excel.support.interfaces.ExcelExecutor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 17:54
 */
@Component
public class ExcelRulerHolder implements BeanPostProcessor {

   private List<ExcelExecutor> allExecutorList = new ArrayList<>();

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof ExcelExecutor){
            allExecutorList.add((ExcelExecutor) bean);
        }
        return bean;
    }

    public ExcelExecutor match(AnnotationDefinition defintion){
        List<ExcelExecutor> supportExcel = allExecutorList.stream()
                .filter(excelExecutor -> excelExecutor.support(defintion))
                .collect(Collectors.toList());
        if(supportExcel.size() > 1){
            throw new ExcelCommonException(String.format("too many exector support, proxy className: %s, proxy method name: %s",
                    defintion.getJp().getTarget().getClass().getName(), defintion.getMethodSignature().getMethod().getName()));
        }

        if(supportExcel.size() == 0){
            throw new ExcelCommonException(String.format("not found exector support, proxy className: %s, proxy method name: %s",
                    defintion.getJp().getTarget().getClass().getName(), defintion.getMethodSignature().getMethod().getName()));
        }
        return supportExcel.stream().findFirst().get();
    }

}
