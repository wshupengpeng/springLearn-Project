package com.sf.vsolution.hx.hanzt.template.html.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import com.sf.vsolution.hx.hanzt.template.html.param.TextFormatStyle;
import com.sf.vsolution.hx.hanzt.template.html.parse.RichTextParser;
import com.sf.vsolution.hx.hanzt.template.html.processor.HtmlTagProcessor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
 * @Date 2023/4/27 16:59
 */
@Slf4j
public abstract class AbstractHtmlTagHandler {

    private static List<HtmlTagProcessor> tagProcessorList = new ArrayList<>();

    public AbstractHtmlTagHandler() {
        RichTextParser.tagHandlerMap.put(getTagName(), this);
    }

    static {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(ClassUtil.getPackage(HtmlTagProcessor.class), HtmlTagProcessor.class);
        if (CollUtil.isNotEmpty(classes)) {
            for (Class clazz:classes) {
                HtmlTagProcessor resolver = (HtmlTagProcessor) ReflectUtil.newInstance(clazz);
                tagProcessorList.add(resolver);
            }
        }
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
    public abstract void doHandle(RichText richText);

    /**
     *  解析处理标签文本
     * @param richText
     */
    public void handler(RichText richText){
        // 用于处理标签的style属性,不需要每个标签都去读一下,抽取公共的方法
        // 如果需要执行特殊方法,则由每个实现类自行实行
        doPreHandle(richText);
        // 当前方法由具体实现类自行实现，用于处理当前标签的特殊操作
        doHandle(richText);
        // 前置子标签处理
        doPreChildHandle(richText);
        // 处理子标签
        doChildHandle(richText);
        // 后置处理
        doPostHandle(richText);
    }

    private void doPreChildHandle(RichText richText){
        for (int i = tagProcessorList.size() - 1; i >= 0; i--) {
            tagProcessorList.get(i).preChildHandle(richText);
        }
    }

    private void doChildHandle(RichText richText){
        Node currentNode = richText.getCurrentNode();
        if(currentNode instanceof Element && !richText.getContinueItr()){
            // 如果是Element代表还有子元素
            TextFormatStyle textFormatStyle = richText.getTextFormatStyle();
            currentNode.childNodes().stream().forEach(childNode->{
                richText.setTextFormatStyle(textFormatStyle);
                richText.setCurrentNode(childNode);

                if(childNode instanceof Element){
                    RichTextParser.parseTag(((Element) childNode).tagName(), richText);
                }else if(childNode instanceof TextNode){
                    RichTextParser.parseTag("", richText);
                }
            });
        }
        richText.setContinueItr(Boolean.FALSE);
    }

    private void doPostHandle(RichText richText) {
        for (int i = tagProcessorList.size() - 1; i >= 0; i--) {
            tagProcessorList.get(i).postHandle(richText);
        }
    }

    private void doPreHandle(RichText richText) {
        for (HtmlTagProcessor htmlTagProcessor : tagProcessorList) {
            htmlTagProcessor.preHandle(richText);
        }
    }


}