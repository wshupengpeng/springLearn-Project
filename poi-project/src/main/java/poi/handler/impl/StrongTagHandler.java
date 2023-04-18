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
 *  用于加粗标签
 */
@Slf4j
public class StrongTagHandler extends AbstractHtmlTagHandler {
    public StrongTagHandler() {
        HtmlToWordUtils.put(getTagName(),this);
    }

    @Override
    public String getTagName() {
        return "strong";
    }

    @Override
    public void doHandler(DocumentParam documentParam) {
//        Style style = documentParam.getStyle().getStyle();
//        if(Objects.isNull(style)){
//            style = new Style();
//        }

        documentParam.getTextFormatStyle().getStyle().setBold(true);
//        Node currentNode = documentParam.getCurrentNode();
//        if(currentNode instanceof TextNode){
//            documentParam.getCurrentParagraph().setAlignment(documentParam.getStyle().getParagraphAlignment());
//            TextRenderPolicy.Helper.renderTextRun(documentParam.createRun(),
//                    new TextRenderData(((TextNode) currentNode).text(),
//                            Optional.ofNullable(documentParam.getStyle().getStyle()).orElse(PoiCommon.DEFAULT_STYLE)));
//        }else{
//            currentNode.childNodes().forEach(childNode -> HtmlToWordUtils.parseTagByName(documentParam, childNode));
//        }
    }
}
