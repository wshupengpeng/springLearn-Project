package poi.handler.common;

import com.deepoove.poi.data.style.Style;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import poi.handler.param.TextFormatStyle;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/8 15:04
 */
public class PoiCommon {

    public static final Style DEFAULT_STYLE = new Style("宋体",10);

    public static final TextFormatStyle DEFAULT_TEXT_FORMAT_STYLE = new TextFormatStyle(DEFAULT_STYLE, ParagraphAlignment.CENTER);


    public static final String FONT_SIZE = "font-size";

    public static final String FONT_FAMILY = "font-family";

    public static final String FONT_COLOR = "color";

    public static final String TXET_ALIGN = "text-align";

    public static final String TEXT_DECORATION = "text-decoration";

    public static final String COLON = ":";

    public static final String SEMICOLON = ";";

    public static final String STYLE_ATTRIBUTE_KEY = "style";

    public static final Integer SPAN_DEFAULT_VALUE = 1;

}
