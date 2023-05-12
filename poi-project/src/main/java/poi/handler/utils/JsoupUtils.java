package poi.handler.utils;

import cn.hutool.core.lang.Assert;
import com.deepoove.poi.data.style.Style;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.jsoup.nodes.Element;
import poi.handler.common.PoiCommon;
import poi.handler.param.TextFormatStyle;

import java.awt.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/8 17:06
 */
public class JsoupUtils {


    public static TextFormatStyle parseStyle(String attributeStyle){
        String[] splits = attributeStyle.split(PoiCommon.SEMICOLON);

        TextFormatStyle textFormatStyle = new TextFormatStyle();

        for (String split : splits) {
            if(split.contains(PoiCommon.FONT_SIZE)){
                textFormatStyle.getStyle().setFontSize(Integer.parseInt(
                        split.substring(split.indexOf(PoiCommon.COLON) + 2)
                                .replace("px", "")));
            }

            if(split.contains(PoiCommon.FONT_FAMILY)){
                textFormatStyle.getStyle().setFontFamily(split.substring(split.indexOf(PoiCommon.COLON) + 2));
            }

            if(split.contains(PoiCommon.FONT_COLOR)){
                textFormatStyle.getStyle().setColor(split.substring(split.indexOf(PoiCommon.COLON) + 2));
            }

            if(split.contains(PoiCommon.TXET_ALIGN)){
                textFormatStyle.setParagraphAlignment(getParagraphAlignment(split.substring(split.indexOf(PoiCommon.COLON) + 2)));
            }

            if(split.contains(PoiCommon.TXET_ALIGN) && split.contains("underline")){
                textFormatStyle.getStyle().setUnderLine(true);
            }

        }
        return textFormatStyle;
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

    public static  <T> T getAttribute(Element element, String attrKey, Function<String, T> convertTypeFn, T defaultValue) {
        Assert.notNull(convertTypeFn);
        if (element.hasAttr(attrKey)) {
            return convertTypeFn.apply(element.attr(attrKey));
        }
        return defaultValue;
    }

    public static void main(String[] args) {
        String input = "#e03e2d";
//        boolean matches = Pattern.matches("\\([0-9]+\\)", input);
//        System.out.println(matches);
//        boolean matches = regex.matches("(^[0-9]+[,)])");
//        System.out.println(matches);
        String s = rgbToHex(input);
        Style style = new Style();
        style.setColor(input);
        System.out.println(Color.decode(input));
//        Color color = new Color(231,95,51);
//        String format = String.format("#%02X%02X%02X", 231, 95, 51);
//        System.out.println(format);
//
        Color decode = Color.decode(input);
        System.out.println(decode);
    }

    public static String rgbToHex(String colorAttribute){
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(colorAttribute);

        int[] rgb = new int[3];
        int i = 0;
        while(matcher.find()){
            rgb[i++] = Integer.parseInt(matcher.group());
        }

        // 代表三色参数都在
        if(i == 3){
            return String.format("%02X%02X%02X", rgb[0], rgb[1], rgb[2]);
        }
        return null;
    }


}
