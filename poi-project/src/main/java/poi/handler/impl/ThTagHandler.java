package poi.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/21 16:10
 */
@Slf4j
public class ThTagHandler extends AbstractHtmlTagHandler {

    public ThTagHandler() {
        HtmlToWordUtils.put(getTagName(),this);
    }

    @Override
    public String getTagName() {
        return "th";
    }

    @Override
    public void doHandler(DocumentParam documentParam) {
//        Node currentNode = documentParam.getCurrentNode();
//        currentNode.childNodes().stream().forEach(childNode-> HtmlToWordUtils.parseTagByName(documentParam,childNode));
    }
}
