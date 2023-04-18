package poi.handler.impl;

import lombok.extern.slf4j.Slf4j;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;

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
    public void doHandler(DocumentParam documentParam) {
//        Style style = documentParam.getStyle().getStyle();
//        if(Objects.isNull(style)){
//            style = new Style();
//        }
        documentParam.getTextFormatStyle().getStyle().setUnderLine(true);

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
