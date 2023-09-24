package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import lombok.extern.slf4j.Slf4j;

/**
 *  用于加粗标签
 */
@Slf4j
public class StrongTagHandler extends AbstractHtmlTagHandler {
    @Override
    public String getTagName() {
        return "strong";
    }

    @Override
    public void doHandle(RichText richText) {
        richText.getTextFormatStyle().getStyle().setBold(true);
    }

}
