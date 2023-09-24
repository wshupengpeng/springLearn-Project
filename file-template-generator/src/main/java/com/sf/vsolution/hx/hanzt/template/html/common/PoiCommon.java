package com.sf.vsolution.hx.hanzt.template.html.common;

import com.deepoove.poi.data.style.Style;
import com.sf.vsolution.hx.hanzt.template.html.param.TextFormatStyle;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/8 15:04
 */
public class PoiCommon {

    public static final Style DEFAULT_STYLE = new Style("宋体",10);

    public static final Integer LINE_SPACING_ONE_POINT_HALF = 360;

    public static final TextFormatStyle DEFAULT_TEXT_FORMAT_STYLE = new TextFormatStyle(DEFAULT_STYLE, ParagraphAlignment.CENTER);

    //常量，在文档中定义长度和高度的单位
    public static final int PER_LINE = 100;
    //每个字符的单位长度
    public static final int PER_CHART = 100;
    //1厘米≈567
    public static final int PER_CM = 567;
    //每一磅的单位长度
    public static final int PER_POUND = 20;
    //行距单位长度
    public static final int ONE_LINE = 240;

    public static final String FONT_SIZE = "font-size";

    public static final String FONT_FAMILY = "font-family";

    public static final String FONT_COLOR = "color";

    public static final String BACKGROUND_COLOR = "background-color";

    public static final String PARAGRAPH_MARGIN_PREFIX = "margin";

    public static final String FONT_WEIGHT = "font-weight";

    public static final String TEXT_ALIGN = "text-align";

    public static final String TEXT_DECORATION = "text-decoration";

    public static final String COLON = ":";

    public static final String SEMICOLON = ";";

    public static final String STYLE_ATTRIBUTE_KEY = "style";

    public static final Integer SPAN_DEFAULT_VALUE = 1;

    public static final String WIDTH = "width";
    public static final String HEIGHT = "height";

    public static final Style TABLE_STYLE = new Style();

    public static final int TABLE_FONT_SIZE = 9;
    public static final String TABLE_FONT_FAMILY = "宋体";
    public static final String TABLE_FONT_COLOR = "000000";
    static {
        TABLE_STYLE.setFontSize(TABLE_FONT_SIZE);
        TABLE_STYLE.setFontFamily(TABLE_FONT_FAMILY);
        TABLE_STYLE.setColor(TABLE_FONT_COLOR);
    }

    public static final Double A4_HEIGHT = 29.7d;

    public static final Double A4_WIDTH = 21d;

}
