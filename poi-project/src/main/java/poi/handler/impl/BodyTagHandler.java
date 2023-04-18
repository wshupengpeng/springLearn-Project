//package poi.handler.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.jsoup.nodes.Node;
//import poi.handler.AbstractHtmlTagHandler;
//import poi.handler.param.DocumentParam;
//import poi.handler.utils.HtmlToWordUtils;
//
//import java.util.List;
//
///**
// * @creater hpp
// * @Date 2023/3/6-21:34
// * @description:
// */
//@Slf4j
//public class BodyTagHandler extends AbstractHtmlTagHandler {
//
//    public BodyTagHandler() {
//        HtmlToWordUtils.put(getTagName(),this);
//    }
//
//    @Override
//    public String getTagName() {
//        return "body";
//    }
//
//    @Override
//    public void doHandler(DocumentParam documentParam) {
//        // 获取解析html的Node对象
//        Node currentNode = documentParam.getCurrentNode();
//        // 获取当前对象的子节点
//        List<Node> nodes = currentNode.childNodes();
//        for (Node node : nodes) {
//            documentParam.setCurrentNode(node);
//            HtmlToWordUtils.parseTagByName(documentParam);
//        }
//
//        // 遍历兄弟节点
////        Node broNode;
////        while ((broNode = currentNode.nextSibling()) != null) {
////            HtmlToWordUtils.parseTagByName(documentParam);
////        }
//    }
//
//
//}
