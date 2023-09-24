package com.sf.vsolution.hx.hanzt.template.html.resolver;

import com.sf.vsolution.hx.hanzt.template.html.enums.DynamicTypeEnum;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import com.sf.vsolution.hx.hanzt.template.html.param.TextFormatStyle;
import org.jsoup.nodes.Node;

import java.util.Optional;

/**
 * @author 01415355
 * @description: 段落定制参数解析器
 * @date 2023年09月15日
 * @version: 1.0
 */
public class CustomizeParagraphResolver implements TagArgumentResolver{

    private static final String DATA_HZT_TYPE = "data-hzt-type";

    @Override
    public void resolveArgument(RichText richText) {
        doResolve(richText);
    }

    private void doResolve(RichText richText) {
        Node currentNode = richText.getCurrentNode();
        if (currentNode.hasAttr(DATA_HZT_TYPE)) {
            String attr = currentNode.attr(DATA_HZT_TYPE);
            if(DynamicTypeEnum.PBREAK.getType().equalsIgnoreCase(attr)){
                TextFormatStyle.ParagraphAttribute paragraphAttribute = Optional.ofNullable(richText.getTextFormatStyle().getParagraphAttribute())
                        .orElseGet(TextFormatStyle.ParagraphAttribute::new);
                paragraphAttribute.setIsPageBreak(Boolean.TRUE);
                richText.getTextFormatStyle().setParagraphAttribute(paragraphAttribute);
                richText.insertNewParagraph();
            }
        }
    }
}
