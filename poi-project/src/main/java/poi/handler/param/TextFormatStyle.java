package poi.handler.param;

import cn.hutool.core.bean.BeanUtil;
import com.deepoove.poi.data.style.Style;
import lombok.Data;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import poi.handler.common.PoiCommon;

import java.util.Objects;

/**
 * @Description: 文本格式风格，用于定义文本类型
 * @Author 01415355
 * @Date 2023/4/10 11:37
 */
@Data
public class TextFormatStyle {
    /**
     *  定义字体颜色大小等参数
     */
    private Style style;

    /**
     *  定义段落对齐方式
     */
    private ParagraphAlignment paragraphAlignment;

    public TextFormatStyle() {
    }

    public TextFormatStyle(Style style, ParagraphAlignment paragraphAlignment) {
        this.style = style;
        this.paragraphAlignment = paragraphAlignment;
    }

    public boolean hasStyle(){
        return Objects.nonNull(style);
    }

    public boolean hasParagraphAlignment(){
        return Objects.nonNull(paragraphAlignment);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        TextFormatStyle clone = new TextFormatStyle();

        if(Objects.nonNull(this.style)){
            Style cloneStyle = new Style();
            BeanUtil.copyProperties(this.style, cloneStyle);
            clone.setStyle(cloneStyle);
        }

        clone.setParagraphAlignment(paragraphAlignment);

        return clone;
    }
}

