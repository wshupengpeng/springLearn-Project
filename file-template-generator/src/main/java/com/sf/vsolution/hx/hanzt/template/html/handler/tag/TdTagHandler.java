package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import lombok.extern.slf4j.Slf4j;


/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/21 16:11
 */
@Slf4j
public class TdTagHandler extends AbstractHtmlTagHandler {


    @Override
    public String getTagName() {
        return "td";
    }

    @Override
    public void doHandle(RichText richText) {

    }

}
