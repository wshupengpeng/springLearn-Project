package poi.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.common.PoiCommon;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;
import poi.handler.utils.JsoupUtils;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/8 15:40
 */
@Slf4j
public class SpanTagHandler extends AbstractHtmlTagHandler {
    @Override
    public String getTagName() {
        return "span";
    }

    @Override
    public void handler(DocumentParam documentParam) {
        // span标签会读取样式
        Node currentNode = documentParam.getCurrentNode();
        if(currentNode.hasAttr(PoiCommon.STYLE_ATTRIBUTE_KEY)){
            documentParam.setStyle(JsoupUtils.parseStyle(currentNode.attr(PoiCommon.STYLE_ATTRIBUTE_KEY)));
        }
        currentNode.childNodes().forEach(childNode -> HtmlToWordUtils.parseTagByName(documentParam, childNode));
    }
}
