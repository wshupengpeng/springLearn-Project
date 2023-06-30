package poi.doc.test;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/30 18:15
 */
public class FooterTest {


    public static void main(String[] args) {
        try {
            // file path
//            File is = new File("D:/test.docx");
//            FileInputStream fis = new FileInputStream(is);
            // document object
            XWPFDocument doc = new XWPFDocument();

            // calling method
            createFooter(doc);
            // or this method both ok!
            createDefaultFooter(doc);

            doc.createParagraph().createRun().addBreak(BreakType.PAGE);
            doc.createParagraph().createRun().addBreak(BreakType.PAGE);
            doc.createParagraph().createRun().addBreak(BreakType.PAGE);
            doc.createParagraph().createRun().addBreak(BreakType.PAGE);
            doc.createParagraph().createRun().addBreak(BreakType.PAGE);

            // output
            OutputStream os = new FileOutputStream("D:\\Test1.docx");
            doc.write(os);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createFooter(XWPFDocument doc) {

        // create footer
        doc.createHeaderFooterPolicy();
//        XWPFHeaderFooterPolicy policy = doc.getHeaderFooterPolicy();
//        CTP ctpFooter = CTP.Factory.newInstance();
        XWPFFooter footer = doc.createFooter(HeaderFooterType.FIRST);

        XWPFParagraph parsFooter = footer.createParagraph();

        // add style (s.th.)
        CTP ctpFooter = parsFooter.getCTP();
        CTPPr ctppr = ctpFooter.addNewPPr();
        CTString pst = ctppr.addNewPStyle();
        pst.setVal("style21");
        CTJc ctjc = ctppr.addNewJc();
        ctjc.setVal(STJc.CENTER);
        ctppr.addNewRPr();

        // add everything from the footerXXX.xml you need
        CTR ctr = ctpFooter.addNewR();
        ctr.addNewRPr();
        CTFldChar fch = ctr.addNewFldChar();
        fch.setFldCharType(STFldCharType.BEGIN);

        ctr = ctpFooter.addNewR();
        ctr.addNewInstrText().setStringValue(" PAGE ");

        ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);

        ctpFooter.addNewR().addNewT().setStringValue("1");

        ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);

//        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, doc);

//        policy.createFooter(XWPFHeaderFooterPolicy.FIRST, parsFooter);
    }

    public static void createDefaultFooter(final XWPFDocument document) {
//        CTP pageNo = CTP.Factory.newInstance();
//        XWPFParagraph footer = new XWPFParagraph(pageNo, document);
        XWPFParagraph footer = document.createFooter(HeaderFooterType.DEFAULT).createParagraph();
        CTP pageNo =footer.getCTP();

        CTPPr begin = pageNo.addNewPPr();
        begin.addNewPStyle().setVal("style21");
        begin.addNewJc().setVal(STJc.CENTER);

        pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.BEGIN);
        pageNo.addNewR().addNewInstrText().setStringValue("PAGE   \\* MERGEFORMAT");
        pageNo.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);

        CTR end = pageNo.addNewR();
        CTRPr endRPr = end.addNewRPr();
        endRPr.addNewNoProof();
        endRPr.addNewLang().setVal("zh-CN");
        end.addNewFldChar().setFldCharType(STFldCharType.END);

        CTSectPr sectPr = document.getDocument().getBody().isSetSectPr() ? document.getDocument().getBody().getSectPr() : document.getDocument().getBody().addNewSectPr();

//        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);
//        policy.createFooter(STHdrFtr.DEFAULT, new XWPFParagraph[] { footer });
    }
}
