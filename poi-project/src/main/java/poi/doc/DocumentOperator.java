package poi.doc;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Description: 解决word页眉页脚添加图片的问题
 * @Author 01415355
 * @Date 2023/6/16 16:46
 */
public class DocumentOperator {

    @Test
    public void createDocument() throws Exception {
        XWPFDocument document = new XWPFDocument();
        // 创建页眉
        CTP ctP = CTP.Factory.newInstance();

        XWPFParagraph p = new XWPFParagraph(ctP, document);

        XWPFRun r = p.createRun();

        r.setText("页眉内容");

        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);


        header.createParagraph();

        header.getParagraphArray(0).getCTP().set(ctP);


        // 创建页脚

        CTP ctP2 = CTP.Factory.newInstance();

        XWPFParagraph p2 = new XWPFParagraph(ctP2, document);

        XWPFRun r2 = p2.createRun();

        r2.setText("页脚内容");

        XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);

        footer.createParagraph();

        footer.getParagraphArray(0).getCTP().set(ctP2);

        document.write(new FileOutputStream("页眉页脚测试.docx"));

    }
}
