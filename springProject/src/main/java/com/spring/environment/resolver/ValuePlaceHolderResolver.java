package com.spring.environment.resolver;

/**
 * @creater hpp
 * @Date 2023/5/29-21:55
 * @description:  解析value占位符
 */
public class ValuePlaceHolderResolver {

    private static final String ARGUMENT_PREFIX = "${";

    private static final String ARGUMENT_MIDDLE = ":";

    private static final String ARGUMENT_END = "}";

    public static String parseValuePlaceHolder(String name){
        int begin = name.indexOf(ARGUMENT_PREFIX);

        if(begin == -1){
            throw new RuntimeException("no search place holder");
        }

        int mid = name.indexOf(ARGUMENT_MIDDLE);
        int end = name.indexOf(ARGUMENT_END);
        if(mid == -1){
            return name.substring(begin, end);
        }
        return name.substring(begin, mid);
    }

}
