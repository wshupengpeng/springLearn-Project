package poi.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;

import java.util.List;

/**
 * @Description: 文本解析处理类，用于处理html文本数据
 * @Author 01415355
 * @Date 2023/3/2 17:49
 */
@Slf4j
public class PTagHandler extends AbstractHtmlTagHandler {


    @Override
    public String getTagName() {
        return "p";
    }

    @Override
    public void handler(DocumentParam documentParam) {
        // 解析标签值
        Node currentNode = documentParam.getCurrentNode();
//        if(currentNode instanceof TextNode){
//            if(log.isDebugEnabled()){
//                log.debug("解析p标签,解析内容：{}", ((TextNode) currentNode).getWholeText());
//            }
//            XWPFRun run = documentParam.getCurrentParagraph().createRun();
//            run.setText(((TextNode) currentNode).getWholeText());
//            //todo 字体格式等设置
//        }else{
        List<Node> childNodes = currentNode.childNodes();
        childNodes.forEach(childNode -> HtmlToWordUtils.parseTagByName(documentParam, currentNode));
        // p标签是块元素,如果有p标签则直接换行
        documentParam.createRun().getCTR().addNewBr();
//        }
        log.info("当前处理类：{},解析标签成功", this.getClass().getSimpleName());
    }
}
