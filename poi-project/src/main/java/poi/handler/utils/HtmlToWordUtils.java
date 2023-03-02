package poi.handler.utils;

import poi.handler.AbstractHtmlTagHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: html标签处理类持有类
 * @Author 01415355
 * @Date 2023/3/2 17:56
 */
public class HtmlToWordUtils {

    private static ConcurrentHashMap<String, AbstractHtmlTagHandler> handlerMap = new ConcurrentHashMap<>();

    public static void put(String tagName, AbstractHtmlTagHandler handler) {
        handlerMap.put(tagName, handler);
    }
}
