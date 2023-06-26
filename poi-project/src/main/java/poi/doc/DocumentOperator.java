package poi.doc;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description: 解决word页眉页脚添加图片的问题
 * @Author 01415355
 * @Date 2023/6/16 16:46
 */
@Slf4j
public class DocumentOperator {
    public static void main(String[] args) throws IOException {
        XWPFDocument docx = new XWPFDocument();//文档对象
        // 分栏
        CTBody body =  docx.getDocument().getBody();
        CTSectPr ctSectPr1 = body.addNewP().addNewPPr().addNewSectPr();
        createHeaderForSec(docx,ctSectPr1,"页眉111111111111111111111111111111111111111111",true);
//        CTP ctp = CTP.Factory.newInstance();
//        XWPFParagraph paragraph = new XWPFParagraph(ctp, docx);//段落对象
//        ctp.addNewR().addNewT().setStringValue("华丽的测试页眉2019051488888888");//设置页眉参数
//        ctp.addNewR().addNewT().setSpace(SpaceAttribute.Space.PRESERVE);
//        CTSectPr sectPr = docx.getDocument().getBody().isSetSectPr() ? docx.getDocument().getBody().getSectPr() : docx.getDocument().getBody().addNewSectPr();
//        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(docx, sectPr);
//        XWPFHeader header = policy.createHeader(STHdrFtr.DEFAULT, new XWPFParagraph[] { paragraph });
//        header.setXWPFDocument(docx);
        OutputStream os = new FileOutputStream("d:\\Test.docx");
        docx.write(os);//输出到本地
    }

    private static void createHeaderForSec(XWPFDocument document, CTSectPr ctSectPr1, String text,boolean withSplitLine) {
        // 页眉文字
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph paragraph = new XWPFParagraph(ctp, document);
        ctp.addNewR().addNewT().setStringValue(text);
        ctp.addNewR().addNewT().setSpace(SpaceAttribute.Space.DEFAULT);
//        XWPFRun run = paragraph.createRun();


        XWPFParagraph paragraph2 = null;
        if (withSplitLine) {
            // 分割线
            CTP splitLine = CTP.Factory.newInstance();
            paragraph2 = new XWPFParagraph(splitLine, document);
            CTBorder bottom = splitLine.addNewPPr().addNewPBdr().addNewTop();
            bottom.setVal(STBorder.Enum.forInt(3));
        }

        HdrDocument hdrDoc = HdrDocument.Factory.newInstance();
        XWPFHeader header = (XWPFHeader) document.createRelationship(XWPFRelation.HEADER, XWPFFactory.getInstance(), 1);

        XWPFRun run = header.createParagraph().createRun();
        run.setText("sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
//        run.setTextHighlightColor("lightGray");
        run.setFontSize(100);
        run.setFontFamily("Calibri");
        CTHdrFtr hdr = header._getHdrFtr();
        hdr.addNewP();
        hdr.setPArray(0, paragraph.getCTP());
        if (withSplitLine) {
            hdr.addNewP();
            hdr.setPArray(1, paragraph2.getCTP());
        }

        CTHdrFtrRef ctHdrFtrRef = ctSectPr1.addNewHeaderReference();
        ctHdrFtrRef.setType(STHdrFtr.DEFAULT);
        ctHdrFtrRef.setId(document.getRelationId(header));

        header.setHeaderFooter(hdr);
        hdrDoc.setHdr(hdr);
        header.setXWPFDocument(document);
    }


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

        XWPFHeader header1 = document.getHeaderFooterPolicy().getHeader(0);
        XWPFParagraph xwpfParagraph = header1.getParagraphs().get(0);
        xwpfParagraph.createRun().setText("测试123");
        String text = xwpfParagraph.getRuns().get(0).getText(0);
        System.out.println(text);
        // word转pdf必须设置字段
        document.write(new FileOutputStream("页眉页脚测试.docx"));

        File wordFile = new File("页眉页脚测试.docx");
        File pdfFile = new File("页眉页脚测试.pdf");

        IConverter converter = LocalConverter.builder().build();

        converter.convert(wordFile).as(DocumentType.MS_WORD)
                .to(pdfFile).as(DocumentType.PDF)
                .execute();

        converter.shutDown();

//        try(OutputStream out = new FileOutputStream("页眉页脚测试.pdf")){
//            XWPFDocument document1 = new XWPFDocument(new FileInputStream("D:\\hpp\\personspace\\github\\LeetCode-project\\word_docx_header_footer.docx"));
//            document.createStyles();
//            CTSectPr ctSectPr = null;
//            if(!document.getDocument().getBody().isSetSectPr()){
//                ctSectPr  = document.getDocument().getBody().addNewSectPr();
//            }else{
//                ctSectPr = document.getDocument().getBody().getSectPr();
//            }
//            if(!ctSectPr.isSetPgSz()){
//                ctSectPr.addNewPgSz();
//                CTPageSz pgSz = ctSectPr.getPgSz();
//                pgSz.setW(BigInteger.valueOf(15840));
//                pgSz.setH(BigInteger.valueOf(12240));
//                pgSz.setOrient(STPageOrientation.LANDSCAPE);
//            }
//            PdfOptions options = PdfOptions.create();
//            PdfConverter.getInstance().convert(document, out, options);
//        }catch (Exception e){
//            log.error("wordToPdf failed ");
//            e.printStackTrace();
//        }


    }
}
