package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

/**
 * @Description: 处理文本信息
 *  标签解析后,为TextNode的节点均在当前处理类进行解析,绘制赋值。
 * @Author 01415355
 * @Date 2023/3/8 14:52
 */
@Slf4j
public class CommonHandler extends AbstractHtmlTagHandler {


    @Override
    public String getTagName() {
        return "";
    }

    @Override
    public void doHandle(RichText richText) {
        // 处理当前文本数据
        Node currentNode = richText.getCurrentNode();

        if(richText.getTextFormatStyle().hasParagraphAlignment()){
            richText.getCurrentParagraph().setAlignment(richText.getTextFormatStyle().getParagraphAlignment());
        }
        if(currentNode instanceof TextNode){
            TextRenderPolicy.Helper.renderTextRun(richText.isCreateRun() ? richText.createRun() : richText.getCurrentRun(),
                    new TextRenderData(((TextNode) currentNode).getWholeText(), richText.getTextFormatStyle().getStyle()));
            if(richText.getTextFormatStyle().hasBackgroundColor()){
                richText.getCurrentRun().getCTR().addNewRPr().addNewShd().setFill(richText.getTextFormatStyle().getBackgroundColor());
            }
            log.info("text:{},style:{}", ((TextNode) currentNode).text(),JSONObject.toJSONString(richText.getTextFormatStyle().getStyle()));
        }
        richText.setCreateRun(Boolean.TRUE);
    }

}
