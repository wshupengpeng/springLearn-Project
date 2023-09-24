package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;

import java.util.Objects;

/**
 * @author 01415355
 * @ClassName DivTagHandler
 * @description: TODO
 * @date 2023年09月06日
 * @version: 1.0
 */
public class DivTagHandler extends AbstractHtmlTagHandler {
    @Override
    public String getTagName() {
        return "div";
    }

    @Override
    public void doHandle(RichText richText) {
        // 如果是解析的第一行,则不需要重新插入段落
        if(richText.isNeedBreak()){
            richText.insertNewParagraph();
        }

        if(Objects.isNull(richText.getCurrentRun())){
            richText.createRun();
        }
    }
}
