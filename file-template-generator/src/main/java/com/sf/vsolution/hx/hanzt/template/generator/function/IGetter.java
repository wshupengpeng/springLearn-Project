package com.sf.vsolution.hx.hanzt.template.generator.function;

import java.io.Serializable;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/14 11:15
 */
public interface IGetter<T> extends Serializable {

    Object get(T t);
}
