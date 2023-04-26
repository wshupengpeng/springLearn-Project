package poi.v2;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import poi.handler.common.PoiCommon;
import poi.handler.param.TextFormatStyle;
import poi.handler.utils.JsoupUtils;
import poi.v2.handler.RichTextParser;
import poi.v2.handler.param.RichText;

import java.util.Objects;

/**
 * @Description:
 * 思路： 通过Jsoup解析html标签，通过抽象类AbstractHtmlTagHandler实现不同标签解析的实现类,
 * 从而使用XWPFDocument将html转换为word,在此情况下，可以自定义前端标签，通过标签来定义动态表格
 * 参数,以及动态字段参数。
 *
 *
 * 步骤一： 实现基本html解析为word,包含 文字、表格、图片（当需要适配新的word格式时，可以通过实现AbstractHtmlTagHandler抽象类，进行动态扩展）
 * 步骤二： 实现自定义标签的动态表格、动态文字、动态图片
 * 步骤三： 通过poi-tl实现动态字段占位符填充
 * @Author 01415355
 * @Date 2023/2/24 16:59
 */
@Slf4j
public abstract class AbstractHtmlTagHandlerV2 {

    public AbstractHtmlTagHandlerV2() {
        RichTextParser.tagHandlerMap.put(getTagName(), this);
    }

    /**
     * 获取当前处理类可以处理的tag标签,支持正则匹配以及全匹配
     * @return
     */
    public abstract String getTagName() ;

    /**
     * 处理富文本数据
     * @param richText
     */
    public abstract void doHandler(RichText richText);


    /**
     *  解析处理标签文本
     * @param richText
     */
    public void handler(RichText richText){
        // 用于处理标签的style属性,不需要每个标签都去读一下,抽取公共的方法
        // 如果需要执行特殊方法,则由每个实现类自行实行
        preHandler(richText);

        // 当前方法由具体实现类自行实现，用于处理当前标签的特殊操作
        doHandler(richText);

        // 用于当前标签是否需要继续迭代子标签
        postHandler(richText);
    }

    private void preHandler(RichText richText){
        Node currentNode = richText.getCurrentNode();
        TextFormatStyle textFormatStyle = ifNullSetParentOrDefaultTextStyle(richText.getTextFormatStyle(), new TextFormatStyle());

        // 判断当前标签是否含有style属性
        if(currentNode.hasAttr(PoiCommon.STYLE_ATTRIBUTE_KEY)){
            // 如果有的话,则读取当前style属性,解析其内容
            //todo 目前想到的参数就这些，如果后面参数比较多，可以定义参数解析器接口,通过实现接口完成不同参数的解析规则和内容，具体看后面的参数情况
            textFormatStyle = JsoupUtils.parseStyle(currentNode.attr(PoiCommon.STYLE_ATTRIBUTE_KEY));
            ifNullSetParentOrDefaultTextStyle(richText.getTextFormatStyle(), textFormatStyle);
        }
        richText.setTextFormatStyle(textFormatStyle);
    }

    private TextFormatStyle ifNullSetParentOrDefaultTextStyle(TextFormatStyle parentTextFormatStyle, TextFormatStyle textFormatStyle) {
        //判断是否含有字体参数
        if (!textFormatStyle.hasStyle()) {
            // 如果不含有字体字段,则取父标签的字体字段
            if (Objects.nonNull(parentTextFormatStyle)
                    && parentTextFormatStyle.hasStyle()) {
                textFormatStyle.setStyle(parentTextFormatStyle.getStyle());
            } else {
                // 如果父标签也没有字体字段,则取系统默认字段和大小
                textFormatStyle.setStyle(PoiCommon.DEFAULT_STYLE);
            }
        }

        // 判断是否含有段落字段
        if(!textFormatStyle.hasParagraphAlignment()){
            if (Objects.nonNull(parentTextFormatStyle)
                    && parentTextFormatStyle.hasParagraphAlignment()) {
                textFormatStyle.setParagraphAlignment(parentTextFormatStyle.getParagraphAlignment());
            }
        }
        return textFormatStyle;
    }

    private void postHandler(RichText richText){
        Node currentNode = richText.getCurrentNode();
        if(currentNode instanceof Element && !richText.getContinueItr()){
            // 如果是Element代表还有子元素
            TextFormatStyle textFormatStyle = richText.getTextFormatStyle();
            currentNode.childNodes().stream().forEach(childNode->{
//                RichText childRichText = new RichText();
//                BeanUtil.copyProperties(richText, childRichText);
                richText.setTextFormatStyle(textFormatStyle);
                richText.setCurrentNode(childNode);

                if(childNode instanceof Element){
                    parseTag(((Element) childNode).tagName(), richText);
                }else if(childNode instanceof TextNode){
                    parseTag("", richText);
                }
            });
        }
        richText.setContinueItr(Boolean.FALSE);
    }

    protected void parseTag(String tagName, RichText richText) {
        AbstractHtmlTagHandlerV2 handler = RichTextParser.getHandler(tagName);

        if(Objects.isNull(handler)){
            log.info("未定义标签:{}处理类,请定义后进行处理", tagName);
            return;
        }

        handler.handler(richText);
    }

}