package poi.handler.impl;

import cn.hutool.core.util.ReUtil;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.policy.TextRenderPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.common.PoiCommon;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;

import java.util.Optional;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/10 10:09
 */
public class HTagHandler extends AbstractHtmlTagHandler {

    private static final int MAX_TITLE_FONT_SIZE = 28;

    @Override
    public String getTagName() {
        return "h[1-5]";
    }

    @Override
    public void doHandler(DocumentParam documentParam) {
        Node currentNode = documentParam.getCurrentNode();

        Style style = documentParam.getTextFormatStyle().getStyle();
        style.setBold(true);
        style.setFontSize(MAX_TITLE_FONT_SIZE - (ReUtil.getFirstNumber(((Element) currentNode).tagName()) - 1) * 2);



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
