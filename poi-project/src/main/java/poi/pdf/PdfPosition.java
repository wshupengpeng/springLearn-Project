package poi.pdf;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import lombok.extern.slf4j.Slf4j;
import poi.pdf.listener.CustomRenderListener;
import com.itextpdf.awt.geom.Rectangle2D.Float;
import java.io.*;
import java.util.*;

/**
 * @Description:
 *  pdf通过关键词查找对应的签署坐标
 *
 * @Author 01415355
 * @Date 2023/5/25 15:01
 */
@Slf4j
public class PdfPosition {

    public static void main(String[] args) throws Exception {

        String filepath = "D:\\hpp\\工作文档\\代码测试文档\\Pdf 测试抓取坐标.pdf";

        String searchWord = "helloworld";

        String descPdf = "D:\\hpp\\工作文档\\代码测试文档\\Pdf 测试抓取坐标结果.pdf";

        String imagePath = "D:\\hpp\\工作文档\\代码测试文档\\image.png";

//        float[] keyWordsByPath = PdfHelper.getKeyWordsByPath(filepath, searchWord);
//        Assert.isTrue(keyWordsByPath != null);

        PdfReader pdfReader = new PdfReader(filepath);

        // 获取pdf分页个数
        int numberOfPages = pdfReader.getNumberOfPages();

        PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
        CustomRenderListener customRenderListener = new CustomRenderListener();
        pdfReaderContentParser.processContent(numberOfPages,customRenderListener);
        // 去除空白字符
        String resultantText = customRenderListener.getResultantText().replaceAll("\\s","");
        Map<Integer, TextRenderInfo> renderIndexMap = customRenderListener.getRenderIndexMap();

        int beginIndex;
        int position = 0;
        List<float[]> positionList = new ArrayList<>();

        while ((beginIndex = resultantText.indexOf(searchWord)) != -1) {
            TextRenderInfo beginRenderInfo = renderIndexMap.get(beginIndex + position);
            TextRenderInfo endRenderInfo = renderIndexMap.get(beginIndex + position + searchWord.length() - 1);
            // 定义坐标和分页数据
            float[] floats = mergeRenderPosition(beginRenderInfo.getBaseline().getBoundingRectange(),
                    endRenderInfo.getBaseline().getBoundingRectange());
            floats[2] = 1;
            positionList.add(floats);
            position += beginIndex + searchWord.length();
            resultantText = resultantText.substring(beginIndex + searchWord.length());
        }
        Assert.isTrue(!CollectionUtil.isEmpty(positionList));
//        CustomRenderListener customRenderListener = new CustomRenderListener(searchWord);
//
//        List<float[]> positionList = new ArrayList<>();
//        // 遍历pdf分页
//        for (int i = 1; i <= numberOfPages; i++) {
//            // 添加页码
//            customRenderListener.setPage(i);
//            pdfReaderContentParser.processContent(i, customRenderListener);
//            float[] positions = customRenderListener.getPcoordinate();
//            if (!Objects.isNull(positions)) {
//                positionList.add(positions);
//                customRenderListener.clear();
//            }
//        }
//        Assert.isTrue(positionList.size() == 3,"预期结果不符合");
        // 验证是否正确，将对应坐标加上图片
        PdfStamper stamper= new PdfStamper(pdfReader, new FileOutputStream(descPdf));

        Image image = Image.getInstance(imagePath);
        image.scaleToFit(5f, 5f);

        for (float[] floats : positionList) {
            PdfContentByte under = stamper.getOverContent((int) floats[2]);
            image.setAbsolutePosition(floats[0], floats[1]);
            under.addImage(image);
        }

        stamper.close();
        pdfReader.close();
    }



    public static float[] mergeRenderPosition(Float beginFloat,Float endFloat){
        float[] position = new float[3];
        position[0] = (beginFloat.x + endFloat.x) / 2;
        position[1] = (beginFloat.y + endFloat.y) / 2;
//        position[0] = beginFloat.x;
//        position[1] = beginFloat.y;
        return position;
    }


}
