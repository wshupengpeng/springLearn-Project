package com.spring.environment;

/**
 * @creater hpp
 * @Date 2023/5/15-22:07
 * @description: 用于刷新@Value的动态参数
 *  原理：
 *  1 spring的@value会存在environment环境变量中，想要动态修改@value的参数,先更改environment中的值
 *  2 再通过beanDefintion销毁bean,重新注入容器
 *
 */
public class ValueScopeRefrsh {



}
