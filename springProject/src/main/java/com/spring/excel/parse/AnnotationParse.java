package com.spring.excel.parse;

import com.spring.excel.support.AnnotationDefinition;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Description:
 * 注解解析器
 * @Author 01415355
 * @Date 2022/10/21 16:56
 */
public interface AnnotationParse {

    /**
     *  通过解析jp获取对应的annotationDefinition
     * @param jp
     * @return
     */
    AnnotationDefinition parse(ProceedingJoinPoint jp);

    /**
     * 判断当前解析器是否支持当前注解解析
     * @param jp
     * @return
     */
    boolean support(ProceedingJoinPoint jp);

}
