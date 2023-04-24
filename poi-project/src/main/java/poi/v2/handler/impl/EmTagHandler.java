package poi.v2.handler.impl;

import lombok.extern.slf4j.Slf4j;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;
import poi.v2.AbstractHtmlTagHandlerV2;
import poi.v2.handler.param.RichText;

@Slf4j
public class EmTagHandler extends AbstractHtmlTagHandlerV2 {


    @Override
    public String getTagName() {
        return "em";
    }

    @Override
    public void doHandler(RichText richText) {
        richText.getTextFormatStyle().getStyle().setItalic(true);
    }
}
