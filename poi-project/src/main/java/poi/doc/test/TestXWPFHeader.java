package poi.doc.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Assert;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static org.junit.Assert.*;

/**
 * @Description: 官方代码测试用例
 * @Author 01415355
 * @Date 2023/6/29 14:03
 */
@Slf4j
public class TestXWPFHeader {

    @Test
    public void testSimpleHeader() throws IOException {
        try (XWPFDocument sampleDoc =new XWPFDocument(new FileInputStream("headerFooter.docx"))) {

            XWPFHeaderFooterPolicy policy = sampleDoc.getHeaderFooterPolicy();

            XWPFHeader header = policy.getDefaultHeader();
            XWPFFooter footer = policy.getDefaultFooter();
        }
    }

    @Test
    public void testImageInHeader() throws IOException {
        try (XWPFDocument sampleDoc = new XWPFDocument(new FileInputStream("headerFooter.docx"))) {

            XWPFHeaderFooterPolicy policy = sampleDoc.getHeaderFooterPolicy();

            XWPFHeader header = policy.getDefaultHeader();

        }
    }

    @Test
    public void testSetHeader() throws IOException {
        try (XWPFDocument sampleDoc = new XWPFDocument(new FileInputStream("headerFooter.docx"))) {
            // no header is set (yet)
            XWPFHeaderFooterPolicy policy = sampleDoc.getHeaderFooterPolicy();

            CTP ctP1 = CTP.Factory.newInstance();
            CTR ctR1 = ctP1.addNewR();
            CTText t = ctR1.addNewT();
            String tText = "Paragraph in header";
            t.setStringValue(tText);

            // Commented MB 23 May 2010
            //CTP ctP2 = CTP.Factory.newInstance();
            //CTR ctR2 = ctP2.addNewR();
            //CTText t2 = ctR2.addNewT();
            //t2.setStringValue("Second paragraph.. for footer");

            // Create two paragraphs for insertion into the footer.
            // Previously only one was inserted MB 23 May 2010
            CTP ctP2 = CTP.Factory.newInstance();
            CTR ctR2 = ctP2.addNewR();
            CTText t2 = ctR2.addNewT();
            t2.setStringValue("First paragraph for the footer");

            CTP ctP3 = CTP.Factory.newInstance();
            CTR ctR3 = ctP3.addNewR();
            CTText t3 = ctR3.addNewT();
            t3.setStringValue("Second paragraph for the footer");

            XWPFParagraph p1 = new XWPFParagraph(ctP1, sampleDoc);
            XWPFParagraph[] pars = new XWPFParagraph[1];
            pars[0] = p1;

            XWPFParagraph p2 = new XWPFParagraph(ctP2, sampleDoc);
            XWPFParagraph p3 = new XWPFParagraph(ctP3, sampleDoc);
            XWPFParagraph[] pars2 = new XWPFParagraph[2];
            pars2[0] = p2;
            pars2[1] = p3;

            // Set headers
            XWPFHeader headerD = policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, pars);
            XWPFHeader headerF = policy.createHeader(XWPFHeaderFooterPolicy.FIRST);
            // Set a default footer and capture the returned XWPFFooter object.
            XWPFFooter footerD = policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, pars2);
            XWPFFooter footerF = policy.createFooter(XWPFHeaderFooterPolicy.FIRST);

            // Ensure the headers and footer were set correctly....
            assertNotNull(policy.getDefaultHeader());
            assertNotNull(policy.getFirstPageHeader());
            assertNotNull(policy.getDefaultFooter());
            assertNotNull(policy.getFirstPageFooter());
            // ....and that the footer object captured above contains two
            // paragraphs of text.
            assertEquals(2, footerD.getParagraphs().size());
            assertEquals(0, footerF.getParagraphs().size());

            // Check the header created with the paragraph got them, and the one
            // created without got none
            assertEquals(1, headerD.getParagraphs().size());
            assertEquals(tText, headerD.getParagraphs().get(0).getText());

            assertEquals(0, headerF.getParagraphs().size());

            // As an additional check, recover the defaults footer and
            // make sure that it contains two paragraphs of text and that
            // both do hold what is expected.
            footerD = policy.getDefaultFooter();
            XWPFParagraph[] paras = footerD.getParagraphs().toArray(new XWPFParagraph[0]);

            assertEquals(2, paras.length);
            assertEquals("First paragraph for the footer", paras[0].getText());
            assertEquals("Second paragraph for the footer", paras[1].getText());


            // Add some text to the empty header
            String fText1 = "New Text!";
            String fText2 = "More Text!";
            headerF.createParagraph().insertNewRun(0).setText(fText1);
            headerF.createParagraph().insertNewRun(0).setText(fText2);
            // headerF.getParagraphs().get(0).insertNewRun(0).setText(fText1);

            // Check it
            assertEquals(tText, headerD.getParagraphs().get(0).getText());
            assertEquals(fText1, headerF.getParagraphs().get(0).getText());
            assertEquals(fText2, headerF.getParagraphs().get(1).getText());


            // Save, re-open, ensure it's all still there
//            sampleDoc.write(new FileOutputStream("createHeaderAndFooter.docx"));
//            XWPFDocument reopened = XWPFTestDataSamples.writeOutAndReadBack(sampleDoc);
//            policy = reopened.getHeaderFooterPolicy();
//            assertNotNull(policy.getDefaultHeader());
//            assertNotNull(policy.getFirstPageHeader());
//            assertNull(policy.getEvenPageHeader());
//            assertNotNull(policy.getDefaultFooter());
//            assertNotNull(policy.getFirstPageFooter());
//            assertNull(policy.getEvenPageFooter());

            // Check the new headers still have their text
            headerD = policy.getDefaultHeader();
            headerF = policy.getFirstPageHeader();
            assertEquals(tText, headerD.getParagraphs().get(0).getText());
            assertEquals(fText1, headerF.getParagraphs().get(0).getText());
            assertEquals(fText2, headerF.getParagraphs().get(1).getText());

            // Check the new footers have their new text too
            footerD = policy.getDefaultFooter();
            paras = footerD.getParagraphs().toArray(new XWPFParagraph[0]);
            footerF = policy.getFirstPageFooter();

            assertEquals(2, paras.length);
            assertEquals("First paragraph for the footer", paras[0].getText());
            assertEquals("Second paragraph for the footer", paras[1].getText());
            assertEquals(1, footerF.getParagraphs().size());
            sampleDoc.write(new FileOutputStream("createHeaderAndFooter.docx"));
        }
    }

    @Test
    public void testSetWatermark() throws IOException {
        try (XWPFDocument sampleDoc = new XWPFDocument(new FileInputStream(""))) {

            // No header is set (yet)
            XWPFHeaderFooterPolicy policy = sampleDoc.getHeaderFooterPolicy();
            assertNull(policy.getDefaultHeader());
            assertNull(policy.getFirstPageHeader());
            assertNull(policy.getDefaultFooter());

            policy.createWatermark("DRAFT");

            assertNotNull(policy.getDefaultHeader());
            assertNotNull(policy.getFirstPageHeader());
            assertNotNull(policy.getEvenPageHeader());

            // Re-open, and check
//            XWPFDocument reopened = XWPFTestDataSamples.writeOutAndReadBack(sampleDoc);
//            policy = reopened.getHeaderFooterPolicy();
//
//            assertNotNull(policy.getDefaultHeader());
//            assertNotNull(policy.getFirstPageHeader());
//            assertNotNull(policy.getEvenPageHeader());
        }
    }

    @Test
    public void testSetWatermarkOnEmptyDoc() throws IOException {
        try (XWPFDocument sampleDoc = new XWPFDocument()) {

            // No header is set (yet)
            XWPFHeaderFooterPolicy policy = sampleDoc.getHeaderFooterPolicy();
            assertNull(policy);

            policy = sampleDoc.createHeaderFooterPolicy();
            policy.createWatermark("DRAFT");

            assertNotNull(policy.getDefaultHeader());
            assertNotNull(policy.getFirstPageHeader());
            assertNotNull(policy.getEvenPageHeader());

            // Re-open, and check
//            XWPFDocument reopened = XWPFTestDataSamples.writeOutAndReadBack(sampleDoc);
//            policy = reopened.getHeaderFooterPolicy();
//
//            assertNotNull(policy.getDefaultHeader());
//            assertNotNull(policy.getFirstPageHeader());
//            assertNotNull(policy.getEvenPageHeader());
        }
    }

//    @Disabled
    void testAddPictureData() {
        // TODO
    }

//    @Disabled
    void testGetAllPictures() {
        // TODO
    }

//    @Disabled
    void testGetAllPackagePictures() {
        // TODO
    }

//    @Disabled
    void testGetPictureDataById() {
        // TODO
    }

    @Test
    public void bug60293() throws Exception {
        //test handling of non-standard header/footer options
//        try (XWPFDocument xwpf = XWPFTestDataSamples.openSampleDocument("60293.docx")) {
//            assertEquals(3, xwpf.getHeaderList().size());
//        }
    }
}
