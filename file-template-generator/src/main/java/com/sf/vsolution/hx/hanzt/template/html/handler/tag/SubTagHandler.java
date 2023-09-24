package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.VerticalAlign;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/4 17:22
 */
@Slf4j
public class SubTagHandler extends AbstractHtmlTagHandler {
    @Override
    public String getTagName() {
        return "sub";
    }

    @Override
    public void doHandle(RichText richText) {
        richText.createRun();
        richText.getCurrentRun().setSubscript(VerticalAlign.SUBSCRIPT);
        richText.setCreateRun(Boolean.FALSE);
    }
}
