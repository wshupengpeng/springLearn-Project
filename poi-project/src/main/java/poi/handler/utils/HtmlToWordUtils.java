package poi.handler.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

import java.util.concurrent.ConcurrentHashMap;

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


    public static void parseHtmlToWord(String content){
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
        // 获取处理类
        AbstractHtmlTagHandler abstractHtmlTagHandler = handlerMap.get(body.tagName());
        abstractHtmlTagHandler.handler(documentParam);
    }


    public static void parseTagByName(DocumentParam documentParam, Node node) {
        DocumentParam broParam = new DocumentParam();
        broParam.setDoc(documentParam.getDoc())
                .setCurrentParagraph(documentParam.getCurrentParagraph())
                .setCurrentNode(node);
        AbstractHtmlTagHandler abstractHtmlTagHandler = HtmlToWordUtils.handlerMap.get(((Element) node).tagName());
        abstractHtmlTagHandler.handler(broParam);
    }

}
