package com.sf.vsolution.hx.hanzt.template.html.processor;

import com.sf.vsolution.hx.hanzt.template.html.param.RichText;

/**
 * @Description:
 * 用于定义htmlTagHandler前置处理操作和后置处理操作顶级接口
 * @Author 01415355
 * @Date 2023/4/28 09:34
 */
public interface HtmlTagProcessor {

    /**
     *  前置处理,用于标签处理器之前执行
     * @param richText
     */
    default void preHandle(RichText richText) {

    }

    /**
     *
     * @param richText
     */
    default void preChildHandle(RichText richText){

    }

    default void postHandle(RichText richText) {

    }

}
