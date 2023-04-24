package poi.v2.handler.impl;

import lombok.extern.slf4j.Slf4j;
import poi.v2.AbstractHtmlTagHandlerV2;
import poi.v2.handler.param.RichText;

/**
 * @creater hpp
 * @Date 2023/3/6-21:34
 * @description:
 */
@Slf4j
public class BodyTagHandler extends AbstractHtmlTagHandlerV2 {

    @Override
    public String getTagName() {
        return "body";
    }

    @Override
    public void doHandler(RichText richText) {
        // nothing todo
    }



}
