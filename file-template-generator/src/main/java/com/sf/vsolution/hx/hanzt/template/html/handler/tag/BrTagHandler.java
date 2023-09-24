package com.sf.vsolution.hx.hanzt.template.html.handler.tag;


import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;

import java.util.Objects;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/21 14:26
 */
public class BrTagHandler extends AbstractHtmlTagHandler {
    @Override
    public String getTagName() {
        return "br";
    }

    @Override
    public void doHandle(RichText richText) {
        if(Objects.isNull(richText.getCurrentRun())){
            richText.createRun();
        }
        richText.getCurrentRun().getCTR().addNewBr();
    }
}
