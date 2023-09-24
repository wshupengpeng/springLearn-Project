package com.sf.vsolution.hx.hanzt.template.html.resolver;

import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import com.sf.vsolution.hx.hanzt.template.html.param.TextFormatStyle;
import com.sf.vsolution.hx.hanzt.template.html.utils.JsoupUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/12 10:56
 */
public class StyleArgumentResolver implements TagArgumentResolver {

    public static final String STYLE_ATTRIBUTE_KEY = "style";

    private List<String> notSupportTransferTag = Arrays.asList("div", "p");

    @Override
    public void resolveArgument(RichText richText) {
        parseStyle(richText);
    }

    private void parseStyle(RichText richText) {
        Node currentNode = richText.getCurrentNode();
        TextFormatStyle textFormatStyle = new TextFormatStyle();

        if(currentNode instanceof TextNode){
            textFormatStyle = richText.getTextFormatStyle();
        }else if (currentNode instanceof Element) {
            textFormatStyle = filterNotSupportTransferTag(richText, (Element) currentNode, textFormatStyle);
        }

        // 判断当前标签是否含有style属性
        if (currentNode.hasAttr(STYLE_ATTRIBUTE_KEY)) {
            // 如果有的话,则读取当前style属性,解析其内容
            textFormatStyle = JsoupUtils.parseStyle(currentNode.attr(STYLE_ATTRIBUTE_KEY), textFormatStyle);
        }
        richText.setTextFormatStyle(textFormatStyle);
    }

    private TextFormatStyle filterNotSupportTransferTag(RichText richText, Element currentNode, TextFormatStyle textFormatStyle) {
        boolean isTransfer = Boolean.TRUE;
        for (String regex : notSupportTransferTag) {
            if (regex.equalsIgnoreCase(currentNode.tagName())
                    || currentNode.tagName().matches(regex)) {
                isTransfer = Boolean.FALSE;
                break;
            }
        }
        return isTransfer ? (TextFormatStyle) richText.getTextFormatStyle().clone() : textFormatStyle;
    }

}
