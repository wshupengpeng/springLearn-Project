package poi.handler.impl;

import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 文本解析处理类，用于处理html文本数据
 * @Author 01415355
 * @Date 2023/3/2 17:49
 */
public class PTagHandler extends AbstractHtmlTagHandler {


    @Override
    public String getTagName() {
        return "p";
    }

    @Override
    public void handler(DocumentParam documentParam) {
        // 解析标签值
    }
}
