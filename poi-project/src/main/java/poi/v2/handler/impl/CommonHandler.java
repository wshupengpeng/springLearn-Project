package poi.v2.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.TextRenderPolicy;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;
import poi.v2.AbstractHtmlTagHandlerV2;
import poi.v2.handler.param.RichText;

/**
 * @Description: 处理文本信息
 *  标签解析后,为TextNode的节点均在当前处理类进行解析,绘制赋值。
 * @Author 01415355
 * @Date 2023/3/8 14:52
 */
@Slf4j
public class CommonHandler extends AbstractHtmlTagHandlerV2 {


    @Override
    public String getTagName() {
        return "";
    }

    @Override
    public void doHandler(RichText richText) {
        // 处理当前文本数据
        Node currentNode = richText.getCurrentNode();

        if(richText.getTextFormatStyle().hasParagraphAlignment()){
            richText.getCurrentParagraph().setAlignment(richText.getTextFormatStyle().getParagraphAlignment());
        }
        if(currentNode instanceof TextNode){
            TextRenderPolicy.Helper.renderTextRun(richText.createRun(),
                    new TextRenderData(((TextNode) currentNode).text(), richText.getTextFormatStyle().getStyle()));
            log.info("text:{},style:{}", ((TextNode) currentNode).text(),JSONObject.toJSONString(richText.getTextFormatStyle().getStyle()));
        }
    }

}
