package poi.v2.handler.impl;

import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.TextRenderPolicy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.v2.AbstractHtmlTagHandlerV2;
import poi.v2.handler.param.RichText;

/**
 * @Description: 文本解析处理类，用于处理html文本数据
 * @Author 01415355
 * @Date 2023/3/2 17:49
 */
@Slf4j
public class PTagHandler extends AbstractHtmlTagHandlerV2 {

    @Override
    public String getTagName() {
        return "p";
    }

    @Override
    public void doHandler(RichText richText) {
        log.info("p 标签开始处理");
        Node currentNode = richText.getCurrentNode();
        if (currentNode instanceof TextNode) {
            richText.insertNewParagraph();
            TextRenderPolicy.Helper.renderTextRun(richText.createRun(),
                    new TextRenderData(((TextNode) currentNode).text(), richText.getTextFormatStyle().getStyle()));
            if (richText.getTextFormatStyle().hasParagraphAlignment()) {
                richText.getCurrentParagraph().setAlignment(richText.getTextFormatStyle().getParagraphAlignment());
            }
            log.info("p标签内容:{}", ((TextNode) currentNode).text());
        }
        log.info("p标签处理完毕");
    }
}
