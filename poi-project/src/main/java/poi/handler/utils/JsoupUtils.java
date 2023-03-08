package poi.handler.utils;

import com.deepoove.poi.data.style.Style;
import poi.handler.common.PoiCommon;

import java.awt.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/8 17:06
 */
public class JsoupUtils {


    public static Style parseStyle(String attributeStyle){
        String[] splits = attributeStyle.split(PoiCommon.SEMICOLON);
        Style style = new Style();
        for (String split : splits) {
            if(split.contains(PoiCommon.FONT_SIZE)){
                style.setFontSize(Integer.parseInt(
                        split.substring(split.indexOf(PoiCommon.COLON) + 2)
                                .replace("px", "")));
            }

            if(split.contains(PoiCommon.FONT_FAMILY)){
                style.setFontFamily(split.substring(split.indexOf(PoiCommon.COLON) + 2));
            }

            if(split.contains(PoiCommon.FONT_COLOR)){
                style.setColor(split);
            }
        }
        return style;
    }

    public static void main(String[] args) {
        String input = "rgb(231, 95, 51)";
//        boolean matches = Pattern.matches("\\([0-9]+\\)", input);
//        System.out.println(matches);
//        boolean matches = regex.matches("(^[0-9]+[,)])");
//        System.out.println(matches);
        rgbToHex(input);
    }

    public static String rgbToHex(String colorAttribute){
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(colorAttribute);

        matcher.start();
        int[] rgb = new int[3];
        int i = 0;
        while(matcher.find()){
            rgb[i++] = Integer.parseInt(matcher.group());
        }
        Color color = new Color(rgb[0], rgb[1], rgb[2]);
        return null;
    }


}
