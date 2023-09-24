package com.sf.vsolution.hx.hanzt.template.html.utils;

import cn.hutool.core.lang.Assert;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import com.sf.vsolution.hx.hanzt.template.html.param.TextFormatStyle;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.jsoup.nodes.Node;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/8 17:06
 */
public class JsoupUtils {


    public static TextFormatStyle parseStyle(String attributeStyle, TextFormatStyle textFormatStyle) {
        String[] splits = attributeStyle.split(PoiCommon.SEMICOLON);

        for (String split : splits) {
            if(split.trim().startsWith(PoiCommon.FONT_SIZE)){
                textFormatStyle.getStyle().setFontSize(
//                        split.substring(split.indexOf(PoiCommon.COLON) + 2)
                        parseFontSize(split.split(PoiCommon.COLON)[1].trim()));
            }

            if(split.trim().startsWith(PoiCommon.FONT_FAMILY)){
                textFormatStyle.getStyle().setFontFamily(split.split(PoiCommon.COLON)[1].trim());
            }

            if(split.trim().startsWith(PoiCommon.FONT_COLOR)){
                textFormatStyle.getStyle().setColor(split.split(PoiCommon.COLON)[1].trim().replace("#",""));
            }

            if(split.trim().startsWith(PoiCommon.TEXT_ALIGN)){
                textFormatStyle.setParagraphAlignment(getParagraphAlignment(split.split(PoiCommon.COLON)[1].trim()));
            }

            // 设置下划线
            if(split.trim().startsWith(PoiCommon.TEXT_DECORATION) && split.contains("underline")){
                textFormatStyle.getStyle().setUnderLine(Boolean.TRUE);
            }
            // 设置删除线
            if(split.trim().startsWith(PoiCommon.TEXT_DECORATION) && split.contains("line-through")){
                textFormatStyle.getStyle().setStrike(Boolean.TRUE);
            }

            // 设置背景色
            if(split.trim().startsWith(PoiCommon.BACKGROUND_COLOR)){
                textFormatStyle.setBackgroundColor(split.substring(split.indexOf(PoiCommon.COLON) + 2).replace("#",""));
            }

            // 字体加粗配置
            if(split.trim().startsWith(PoiCommon.FONT_WEIGHT)){
                textFormatStyle.getStyle().setBold(split.contains("bold"));
            }

            // 设置段落属性
            if(split.trim().startsWith(PoiCommon.PARAGRAPH_MARGIN_PREFIX)){
                TextFormatStyle.ParagraphAttribute paragraphAttribute = Optional.ofNullable(textFormatStyle.getParagraphAttribute())
                        .orElseGet(TextFormatStyle.ParagraphAttribute::new);

                if(split.trim().contains("top")){
                    paragraphAttribute.setSpacingBefore(Double.valueOf(split.substring(split.indexOf(PoiCommon.COLON) + 2).replaceAll("pt", FileConstant.EMPTY_STR)));
                }

                if(split.trim().contains("bottom")){
                    paragraphAttribute.setSpacingAfter(Double.valueOf(split.substring(split.indexOf(PoiCommon.COLON) + 2).replaceAll("pt", FileConstant.EMPTY_STR)));
                }
                textFormatStyle.setParagraphAttribute(paragraphAttribute);
            }

        }
        return textFormatStyle;
    }

    private static Integer parseFontSize(String fontSize) {

        if(fontSize.contains("px")){
            return Integer.parseInt(fontSize.replaceAll("px", "")) * 3/4;
        }else if(fontSize.contains("pt")){
            return (int) Math.round(Double.parseDouble(fontSize.replaceAll("pt", "")));
        }

        return null;
    }

    private static ParagraphAlignment getParagraphAlignment(String alignment) {
        switch (alignment) {
            case "center":
                return ParagraphAlignment.CENTER;
            case "right":
                return ParagraphAlignment.RIGHT;
            case "justify":
                return ParagraphAlignment.BOTH;
            default:
                return ParagraphAlignment.LEFT;
        }
    }

    public static  <T> T getAttribute(Node node, String attrKey, Function<String, T> convertTypeFn, T defaultValue) {
        Assert.notNull(convertTypeFn);
        if (node.hasAttr(attrKey)) {
            return convertTypeFn.apply(node.attr(attrKey));
        }
        return defaultValue;
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
