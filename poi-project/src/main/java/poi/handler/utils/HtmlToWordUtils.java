package poi.handler.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

import java.util.List;
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


    public static void parseHtmlToWord(String content){
        // 解析当前文本
        Document parse = Jsoup.parse(content);
        Element body = parse.body();
        List<Node> nodes = body.childNodes();
        // 创建word文档
        XWPFDocument doc = new XWPFDocument();
        for(Node node : nodes){
            DocumentParam documentParam = new DocumentParam();
            documentParam.setCurrentNode(node);
            documentParam.setDoc(doc);
            documentParam.setCurrentParagraph(doc.createParagraph());

        }
    }



    public static void parseTag(DocumentParam documentParam){
        Node currentNode = documentParam.getCurrentNode();
        List<Node> nodes = currentNode.childNodes();
        for (Node node : nodes) {

        }
        if (currentNode instanceof TextNode) {

        }
    }
}
