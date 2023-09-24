package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import cn.hutool.core.util.ReUtil;
import com.deepoove.poi.data.style.Style;
import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

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
    public void doHandle(RichText richText) {
        Node currentNode = richText.getCurrentNode();
        // 初始化后当前参数均为
        if(richText.isNeedBreak()){
            richText.insertNewParagraph();
        }

        Style style = richText.getTextFormatStyle().getStyle();
        style.setBold(true);
        style.setFontSize(MAX_TITLE_FONT_SIZE - (ReUtil.getFirstNumber(((Element) currentNode).tagName()) - 1) * 2);

        richText.setNeedBreak(Boolean.TRUE);
    }

}
