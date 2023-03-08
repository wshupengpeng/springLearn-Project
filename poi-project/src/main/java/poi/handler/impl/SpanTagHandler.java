package poi.handler.impl;

import com.deepoove.poi.data.style.Style;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

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
    public void handler(DocumentParam documentParam) {
        // span标签会读取样式
        Node currentNode = documentParam.getCurrentNode();
        if(currentNode.hasAttr("style")){
            currentNode.attr("style");
//            Style style = new Style();
//            style.setFontFamily();
//            documentParam.setStyle()
        }
    }
}
