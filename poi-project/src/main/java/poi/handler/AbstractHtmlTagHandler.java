package poi.handler;

import com.deepoove.poi.util.StyleUtils;
import org.jsoup.nodes.Node;
import poi.handler.common.PoiCommon;
import poi.handler.param.DocumentParam;
import poi.handler.param.TextFormatStyle;
import poi.handler.resolver.HandlerArgumentResolver;
import poi.handler.utils.JsoupUtils;
import poi.handler.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public static List<HandlerArgumentResolver> resolverList = new ArrayList<>();

    /**
     * 获取当前处理类可以处理的tag标签
     * @return
     */
    public abstract String getTagName() ;

    public void handler(DocumentParam documentParam){
        onPreHandler(documentParam);
        doHandler(documentParam);
        onAfterHandler(documentParam);
    }

    public abstract void doHandler(DocumentParam documentParam);

    public void onPreHandler(DocumentParam documentParam){
        // 解析style属性
        resolverList.stream().forEach(resolver->{
            if(resolver.isSupport(documentParam.getCurrentNode())) {
                resolver.resolveArgument(documentParam);
            }
        });

//        Node currentNode = documentParam.getCurrentNode();
//        if (currentNode.hasAttr(PoiCommon.STYLE_ATTRIBUTE_KEY)) {
//            TextFormatStyle textFormatStyle = JsoupUtils.parseStyle(currentNode.attr(PoiCommon.STYLE_ATTRIBUTE_KEY));
//            if(Objects.isNull(documentParam.getCurrentRun())){
//                documentParam.createRun();
//            }
//
//            StyleUtils.styleRun(documentParam.getCurrentRun(), ObjectUtils.getFirstNonNull(textFormatStyle.getStyle(),
//                    Optional.ofNullable(documentParam.getParentStyle()).ifPresent();));
//
//            documentParam.getCurrentParagraph().setAlignment(documentParam.getStyle().getParagraphAlignment());

//            if (Objects.isNull(documentParam.getStyle()) || Objects.isNull(documentParam.getCurrentRun())) {
//                documentParam.setStyle(JsoupUtils.parseStyle(currentNode.attr(PoiCommon.STYLE_ATTRIBUTE_KEY)));
//            }else{
//                documentParam.getCurrentParagraph().setAlignment(documentParam.getStyle().getParagraphAlignment());
//                StyleUtils.styleRun(documentParam.getCurrentRun(), documentParam.getStyle().getStyle());
//            }
//        }
    }


    public void onAfterHandler(DocumentParam documentParam){

    };

}