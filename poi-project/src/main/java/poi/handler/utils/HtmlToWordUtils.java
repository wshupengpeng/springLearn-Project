package poi.handler.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

import java.util.Enumeration;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @Description: html标签处理类持有类
 * @Author 01415355
 * @Date 2023/3/2 17:56
 */
@Slf4j
public class HtmlToWordUtils {

    public static ConcurrentHashMap<String, AbstractHtmlTagHandler> handlerMap = new ConcurrentHashMap<>();

    public static void put(String tagName, AbstractHtmlTagHandler handler) {
        handlerMap.put(tagName, handler);
    }

    static {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(ClassUtil.getPackage(AbstractHtmlTagHandler.class), AbstractHtmlTagHandler.class);
        if (CollUtil.isNotEmpty(classes)) {
            for (Class clazz:classes) {
                AbstractHtmlTagHandler handler = (AbstractHtmlTagHandler) ReflectUtil.newInstance(clazz);
                handlerMap.put(handler.getTagName(), handler);
            }
        }
    }

    public static XWPFDocument parseHtmlToWord(String content){
        // 解析当前文本
        Document parse = Jsoup.parse(content);
        Element body = parse.body();
        // 创建word文档
        XWPFDocument doc = new XWPFDocument();
        // 组装实体
        DocumentParam documentParam = new DocumentParam();
        documentParam.setCurrentNode(body);
        documentParam.setDoc(doc);
        documentParam.setCurrentParagraph(doc.createParagraph());
//        documentParam.createRun();
        // 获取处理类
        parseTagByName(documentParam);
//        AbstractHtmlTagHandler abstractHtmlTagHandler = handlerMap.get(body.tagName());
//        abstractHtmlTagHandler.handler(documentParam);
        return doc;
    }


    public static void parseTagByName(DocumentParam documentParam) {
        Node node = documentParam.getCurrentNode();
        AbstractHtmlTagHandler abstractHtmlTagHandler = handlerMap.get("");
        if(node instanceof Element){
            abstractHtmlTagHandler = getMatchTagHandler(((Element) node).tagName());
            if(Objects.nonNull(abstractHtmlTagHandler)){
                abstractHtmlTagHandler.handler(documentParam);
            }
            log.info("tagName:{}",((Element) node).tagName());
        }else if(node instanceof TextNode){
            abstractHtmlTagHandler.handler(documentParam);
        }

        if(node.childNodes().size() > 0){
            node.childNodes().forEach(childNode->{
                documentParam.setCurrentNode(childNode);
                parseTagByName(documentParam);
            });
        }
    }

    private static AbstractHtmlTagHandler getMatchTagHandler(String tagName) {
        Enumeration<String> keys = handlerMap.keys();
        String key;
        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            if (tagName.equalsIgnoreCase(key) || tagName.matches(key)) {
                return handlerMap.get(key);
            }
        }
        return null;
    }

}
