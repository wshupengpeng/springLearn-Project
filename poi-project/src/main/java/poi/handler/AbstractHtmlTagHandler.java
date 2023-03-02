package poi.handler;

import poi.handler.param.DocumentParam;

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
public abstract class AbstractHtmlTagHandler {

    /**
     * 获取当前处理类可以处理的tag标签
     * @return
     */
    public abstract String getTagName() ;


    public abstract void handler(DocumentParam documentParam);

}