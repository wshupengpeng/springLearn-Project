package com.sf.vsolution.hx.hanzt.template.generator.utils;

import com.sf.vsolution.hx.hanzt.template.generator.function.BeanCopyUtilCallBack;
import com.sf.vsolution.hx.hanzt.template.generator.function.IGetter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * @Description
 * @auther Shen Yang
 * @create 2019-10-17 17:48
 */
@Slf4j
public class BeanUtil {

	private static Map<Class, SerializedLambda> CLASS_LAMDBA_CACHE = new ConcurrentHashMap<>();


	/**
	 * @param input 输入集合
	 * @param clazz  输出集合类型
	 * @param <E>   输入集合类型
	 * @param <T>   输出集合类型
	 * @return 返回集合
	 */
	public static <E, T> List<T> convertList2List(List<E> input, Class<T> clazz) {
		List<T> output = new ArrayList<>();
		if (!CollectionUtils.isEmpty(input)) {
			for (E source : input) {
				T target = BeanUtils.instantiateClass(clazz);
				BeanUtils.copyProperties(source, target);
				output.add(target);
			}
		}
		return output;
	}

    public static <S,T> List<T> copyListProperties(List<S> sourceList
            , Supplier<T> supplier){
        return copyListProperties(sourceList,supplier,null);
    }

	public static <S,T> List<T> copyListProperties(List<S> sourceList
            , Supplier<T> supplier, BeanCopyUtilCallBack<S,T> callBack){
		List<T> list = new ArrayList<>(sourceList.size());
		for (S s : sourceList) {
            T target = supplier.get();
            BeanUtils.copyProperties(s,target);
            list.add(target);
            if(callBack != null){
                callBack.callBack(s,target);
            }
		}
		return list;
	}


	public static <S, T> T copyProperties(S source, Supplier<T> supplier, BeanCopyUtilCallBack<S, T> callBack) {
		T target = supplier.get();
		BeanUtils.copyProperties(source, target);
		if (callBack != null) {
			callBack.callBack(source, target);
		}
		return target;
	}

	public static <S, T> T copyProperties(S source, Supplier<T> supplier) {
		return copyProperties(source,supplier,null);
	}


	public static <T,S> T copyBeanProperties(Supplier<T> supplier,S source, BeanCopyUtilCallBack<S,T> callBack){
		T target = supplier.get();
		BeanUtils.copyProperties(source,target);
		if (callBack != null) {
			callBack.callBack(source, target);
		}
		return target;
	}

	public static <T,S> T copyBeanProperties(Supplier<T> supplier,S source){
		return copyBeanProperties(supplier,source,null);
	}


	public static <T> String convertToFieldName(IGetter<T> fn){
		SerializedLambda serializedLambda = getSerializedLambda(fn);
		String implMethodName = serializedLambda.getImplMethodName();
		String prefix = null;
		if(implMethodName.startsWith("get")){
			prefix = "get";
		}else if(implMethodName.startsWith("is")){
			prefix = "is";
		}
		if(prefix == null){
			log.warn("无效的set方法:{}", implMethodName);
		}
		return implMethodName.replace(prefix,"");
	}


	public static SerializedLambda getSerializedLambda(Serializable fn){
		SerializedLambda serializedLambda = CLASS_LAMDBA_CACHE.get(fn.getClass());
		if(serializedLambda == null){
			try {
				Method writeReplace = fn.getClass().getDeclaredMethod("writeReplace");
				writeReplace.setAccessible(Boolean.TRUE);
				serializedLambda = (SerializedLambda) writeReplace.invoke(fn);
				CLASS_LAMDBA_CACHE.put(fn.getClass(),serializedLambda);
			}catch (Exception e){
				log.info("getSerializedLambda failed,erorr:", e);
			}
		}
		return serializedLambda;
	}

}
