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

@Slf4j
public class EmTagHandler extends AbstractHtmlTagHandler {

    public EmTagHandler() {
        HtmlToWordUtils.put(getTagName(),this);
    }

    @Override
    public String getTagName() {
        return "em";
    }

    @Override
    public void doHandler(DocumentParam documentParam) {
        documentParam.getTextFormatStyle().getStyle().setItalic(true);
//        Style style = documentParam.getStyle().getStyle();
//        if(Objects.isNull(style)){
//            style = new Style();
//        }
//        style.setItalic(true);
//        Node currentNode = documentParam.getCurrentNode();
//        if(currentNode instanceof TextNode){
//
//            documentParam.getCurrentParagraph().setAlignment(documentParam.getStyle().getParagraphAlignment());
//
//            TextRenderPolicy.Helper.renderTextRun(documentParam.createRun(),
//                    new TextRenderData(((TextNode) currentNode).text(),
//                            Optional.ofNullable(documentParam.getStyle().getStyle()).orElse(PoiCommon.DEFAULT_STYLE)));
//        }else{
//            currentNode.childNodes().forEach(childNode -> HtmlToWordUtils.parseTagByName(documentParam, childNode));
//        }
    }
}
