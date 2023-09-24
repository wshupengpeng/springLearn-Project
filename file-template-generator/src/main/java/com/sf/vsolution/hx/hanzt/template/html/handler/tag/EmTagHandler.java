package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmTagHandler extends AbstractHtmlTagHandler {


    @Override
    public String getTagName() {
        return "em";
    }

    @Override
    public void doHandle(RichText richText) {
        richText.getTextFormatStyle().getStyle().setItalic(true);
    }
}
