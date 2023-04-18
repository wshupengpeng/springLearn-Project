package poi.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.TextRenderPolicy;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.common.PoiCommon;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @Description: 处理文本信息
 *  标签解析后,为TextNode的节点均在当前处理类进行解析,绘制赋值。
 * @Author 01415355
 * @Date 2023/3/8 14:52
 */
@Slf4j
public class CommonHandler extends AbstractHtmlTagHandler {

    public CommonHandler() {
        HtmlToWordUtils.put(getTagName(),this);
    }

    @Override
    public String getTagName() {
        return "";
    }

    @Override
    public void doHandler(DocumentParam documentParam) {
        // 处理当前文本数据
        Node currentNode = documentParam.getCurrentNode();

        if(documentParam.hasParagraphAlignment()){
            documentParam.getCurrentParagraph().setAlignment(documentParam.getTextFormatStyle().getParagraphAlignment());
        }
        if(currentNode instanceof TextNode){
            TextRenderPolicy.Helper.renderTextRun(documentParam.createRun(),
                    new TextRenderData(((TextNode) currentNode).text(), documentParam.getTextFormatStyle().getStyle()));
            log.info("text:{},style:{}", ((TextNode) currentNode).text(),JSONObject.toJSONString(documentParam.getTextFormatStyle().getStyle()));
        }

//        if(currentNode instanceof TextNode){
//            TextRenderPolicy.Helper.renderTextRun(documentParam.createRun(),
//                    new TextRenderData(((TextNode) currentNode).text(),
//                            Optional.ofNullable(documentParam.getStyle().getStyle()).orElse(PoiCommon.DEFAULT_STYLE)));
//        }
    }
}
