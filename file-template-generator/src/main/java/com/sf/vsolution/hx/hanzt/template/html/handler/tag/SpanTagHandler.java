package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/8 15:40
 */
@Slf4j
public class SpanTagHandler extends AbstractHtmlTagHandler {

    @Override
    public String getTagName() {
        return "span";
    }

    @Override
    public void doHandle(RichText richText) {

    }

}
