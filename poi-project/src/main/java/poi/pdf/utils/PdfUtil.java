package poi.pdf.utils;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import lombok.extern.slf4j.Slf4j;
import poi.pdf.listener.CustomRenderListener;
import poi.pdf.param.CoordinateRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: pdf工具类
 * @Author 01415355
 * @Date 2023/5/26 9:11
 */
@Slf4j
public class PdfUtil {

    /**
     * 获取pdf中关键词坐标
     *
     * @param pdfPath    需要查找pdf地址
     * @param searchWord 搜索词
     * @return
     */
    public static List<CoordinateRecord> searchWordByPdfPath(String pdfPath, String searchWord) {
        // float 分别记录三个字段
        List<CoordinateRecord> coordinateList = new ArrayList<>();
        try {
            PdfReader pdfReader = new PdfReader(pdfPath);

            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);

            CustomRenderListener customRenderListener = new CustomRenderListener();

            int numberOfPages = pdfReader.getNumberOfPages();

            for (int i = 1; i <= numberOfPages; i++) {
                // 解析当前页
                pdfReaderContentParser.processContent(i, customRenderListener);
                // 去除空白字符
                String resultantText = customRenderListener.getResultantText().replaceAll("\\s", "");
                // 获取对应TextRenderInfo和下标关系
                Map<Integer, TextRenderInfo> renderIndexMap = customRenderListener.getRenderIndexMap();

                int beginIndex;
                int offset = 0;
                while ((beginIndex = resultantText.indexOf(searchWord)) != -1) {
                    TextRenderInfo beginRenderInfo = renderIndexMap.get(beginIndex + offset);
                    TextRenderInfo endRenderInfo = renderIndexMap.get(beginIndex + offset + searchWord.length() - 1);
                    // 定义坐标和分页数据
                    CoordinateRecord coordinateRecord = mergeRenderPosition(beginRenderInfo.getBaseline().getBoundingRectange(),
                            endRenderInfo.getBaseline().getBoundingRectange());
                    // 记录分页坐标
                    coordinateRecord.setPageNum(i);
                    coordinateList.add(coordinateRecord);
                    // 更新偏移量
                    offset += beginIndex + searchWord.length();
                    resultantText = resultantText.substring(beginIndex + searchWord.length());
                }
            }
        } catch (Exception e) {
            log.error("解析pdf出错,错误原因为:", e);
        }
        return coordinateList;
    }

    public static CoordinateRecord mergeRenderPosition(Rectangle2D.Float beginFloat, Rectangle2D.Float endFloat) {
        CoordinateRecord coordinateRecord = new CoordinateRecord();
        coordinateRecord.setX((beginFloat.x + endFloat.x) / 2);
        coordinateRecord.setY((beginFloat.y + endFloat.y) / 2);
        return coordinateRecord;
    }
}
