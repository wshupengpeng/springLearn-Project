package poi.handler.resolver;

import com.deepoove.poi.data.style.Style;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.jsoup.nodes.Node;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.common.PoiCommon;
import poi.handler.param.DocumentParam;
import poi.handler.param.TextFormatStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/12 10:56
 */
public class StyleArgumentResolver extends HandlerArgumentResolver {

//    public StyleArgumentResolver() {
//        AbstractHtmlTagHandler.resolverList.add(this);
//    }

    public static final String STYLE_ATTRIBUTE_KEY = "style";

    @Override
    public boolean isSupport(Node currentNode) {
        return currentNode.hasAttr(STYLE_ATTRIBUTE_KEY);
    }

    @Override
    public void resolveArgument(DocumentParam documentParam) {
        Node currentNode = documentParam.getCurrentNode();
        String attributeStyle = currentNode.attr(STYLE_ATTRIBUTE_KEY);
        String[] splits = attributeStyle.split(PoiCommon.SEMICOLON);
        Style style = new Style();
        TextFormatStyle textFormatStyle = new TextFormatStyle();
        textFormatStyle.setStyle(style);
        for (String split : splits) {
            if (split.contains(PoiCommon.FONT_SIZE)) {
                style.setFontSize(Integer.parseInt(
                        split.substring(split.indexOf(PoiCommon.COLON) + 2)
                                .replace("px", "")));
            }

            if (split.contains(PoiCommon.FONT_FAMILY)) {
                style.setFontFamily(split.substring(split.indexOf(PoiCommon.COLON) + 2));
            }

            if (split.contains(PoiCommon.FONT_COLOR)) {
                style.setColor(rgbToHex(split));
            }

            if (split.contains(PoiCommon.TXET_ALIGN)) {
                textFormatStyle.setParagraphAlignment(getParagraphAlignment(split));
            }
        }
        documentParam.setTextFormatStyle(textFormatStyle);
    }

    private static ParagraphAlignment getParagraphAlignment(String split) {
        switch (split.substring(split.indexOf(PoiCommon.COLON))) {
            case "center":
                return ParagraphAlignment.CENTER;
            case "right":
                return ParagraphAlignment.RIGHT;
            default:
                return ParagraphAlignment.LEFT;
        }
    }

    public static String rgbToHex(String colorAttribute) {
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(colorAttribute);

        int[] rgb = new int[3];
        int i = 0;
        while (matcher.find()) {
            rgb[i++] = Integer.parseInt(matcher.group());
        }

        // 代表三色参数都在
        if (i == 3) {
            return String.format("%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
        }
        return null;
    }
}
