package com.sf.vsolution.hx.hanzt.template.generator.function;

/**
 * @description:  beanUtils 集合copy回调定义接口
 * @author: 01415355
 * @createDate: 2022-03-25 15:22
 * @version: 1.0
 * @todo:
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack<S,T> {

   void callBack(S source,T target);

}
