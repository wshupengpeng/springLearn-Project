package poi.handler.impl;

import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.policy.TextRenderPolicy;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.common.PoiCommon;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * 用于定义下划线标签
 */
@Slf4j
public class UTagHandler extends AbstractHtmlTagHandler {
    public UTagHandler() {
        HtmlToWordUtils.put(getTagName(),this);
    }

    @Override
    public String getTagName() {
        return "u";
    }

    @Override
    public void handler(DocumentParam documentParam) {
        Style style = documentParam.getStyle();
        if(Objects.isNull(style)){
            style = new Style();
        }
        style.setUnderLine(true);

        Node currentNode = documentParam.getCurrentNode();
        if(currentNode instanceof TextNode){
            TextRenderPolicy.Helper.renderTextRun(documentParam.createRun(),
                    new TextRenderData(((TextNode) currentNode).text(),
                            Optional.ofNullable(documentParam.getStyle()).orElse(PoiCommon.DEFAULT_STYLE)));
        }else{
            currentNode.childNodes().forEach(childNode -> HtmlToWordUtils.parseTagByName(documentParam, childNode));
        }
    }
}
