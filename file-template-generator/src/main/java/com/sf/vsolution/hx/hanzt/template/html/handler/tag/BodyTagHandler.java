package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import lombok.extern.slf4j.Slf4j;

/**
 * @creater hpp
 * @Date 2023/3/6-21:34
 * @description:
 */
@Slf4j
public class BodyTagHandler extends AbstractHtmlTagHandler {

    @Override
    public String getTagName() {
        return "body";
    }

    @Override
    public void doHandle(RichText richText) {
        // nothing todo
    }

}
