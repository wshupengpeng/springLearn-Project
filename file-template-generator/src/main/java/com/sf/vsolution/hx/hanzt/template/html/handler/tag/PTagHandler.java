package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

/**
 * @Description: 文本解析处理类，用于处理html文本数据
 * @Author 01415355
 * @Date 2023/3/2 17:49
 */
@Slf4j
public class PTagHandler extends AbstractHtmlTagHandler {

    @Override
    public String getTagName() {
        return "p";
    }

    @Override
    public void doHandle(RichText richText) {
        log.info("p 标签开始处理");
        // 如果是解析的第一行,则不需要重新插入段落
        if(richText.isNeedBreak()){
            richText.insertNewParagraph();
        }
        Node currentNode = richText.getCurrentNode();
        if (currentNode instanceof TextNode) {
            TextRenderPolicy.Helper.renderTextRun(richText.createRun(),
                    new TextRenderData(((TextNode) currentNode).text(), richText.getTextFormatStyle().getStyle()));
            if (richText.getTextFormatStyle().hasParagraphAlignment()) {
                richText.getCurrentParagraph().setAlignment(richText.getTextFormatStyle().getParagraphAlignment());
            }
            log.info("p标签内容:{}", ((TextNode) currentNode).text());
        }
        // 第一行读取后,每一个p标签都需要重新换行
        richText.setNeedBreak(Boolean.TRUE);
        log.info("p标签处理完毕");
    }
}
