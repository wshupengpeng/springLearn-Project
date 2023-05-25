package poi.pdf.listener;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.awt.geom.Rectangle2D.Float;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/5/25 15:14
 */
@Data
public class CustomRenderListener extends SimpleTextExtractionStrategy {

    private Map<Integer,TextRenderInfo> renderIndexMap = new HashMap<>();

    private int renderIndex;


    @Override
    public void renderText(TextRenderInfo renderInfo) {
        super.renderText(renderInfo);
        // 记录坐标和文本之间的关系
        renderIndexMap.put(renderIndex++, renderInfo);
    }
    

}
