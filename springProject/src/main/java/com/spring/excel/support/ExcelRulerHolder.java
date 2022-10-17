package com.spring.excel.support;

import com.spring.excel.support.interfaces.ExcelExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 17:54
 */
@Component
public class ExcelRulerHolder{

    @Autowired
    private ApplicationContext applicationContext;

    public ExcelExecutor match(AnnotationDefinition defintion){
        switch (defintion.getExportAnnotation().mode()){
            case NORMAL: return applicationContext.getBean(NormalExecutor.class);
            case SUBSELECTION: return applicationContext.getBean(SubselectionExecutor.class);
            case CUSTOMIZER: throw new RuntimeException("暂不支持");
        }
        return null;
    }

}
