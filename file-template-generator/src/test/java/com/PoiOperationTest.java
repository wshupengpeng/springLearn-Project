package com;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.util.TableTools;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.generator.policy.CustomizeTablePolicy;
import com.sf.vsolution.hx.hanzt.template.generator.utils.PoiTlUtils;
import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import lombok.Data;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * @author 01415355
 * @description: poi操作测试
 * @date 2023年09月05日
 * @version: 1.0
 */

public class PoiOperationTest {

    private static final String BASE_DIR = "D:\\hpp\\poi\\";

    public static void setXWPFRunStyle(XWPFRun r1, String font, int fontSize) {
        r1.setFontSize(fontSize);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setEastAsia(font);
        fonts.setHAnsi(font);
    }

    private static void setInstrText(XWPFParagraph paragraph, String instrText, String fontFamily, int fontSize) {
        XWPFRun run = paragraph.createRun();//新的段落对象
        CTFldChar fldChar = run.getCTR().addNewFldChar();//新的CTFldChar对象
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
        CTText ctText = run.getCTR().addNewInstrText();
        ctText.setStringValue(instrText);
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        setXWPFRunStyle(run, fontFamily, fontSize);
        fldChar = run.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
    }

    @Test
    public void setPageMar() throws IOException {
        String fileName = "测试页边距.docx";
        String path = String.format("%s%s", BASE_DIR, fileName);
        System.out.println("path:" + path);
        FileOutputStream out = new FileOutputStream(new File(path));
        XWPFDocument document = new XWPFDocument();

        document.createStyles();

        CTSectPr addNewSectPr = document.getDocument().getBody().addNewSectPr();
        CTPageMar addNewPgMar = addNewSectPr.addNewPgMar();
        addNewPgMar.setLeft(BigInteger.valueOf((int) (567 * 3.17d)));
        addNewPgMar.setTop(BigInteger.valueOf((int) (567 * 2.54d)));
        addNewPgMar.setRight(BigInteger.valueOf((int) (567 * 2.7d)));
        addNewPgMar.setBottom(BigInteger.valueOf((int) (567 * 3.67d)));
        addNewPgMar.setHeader(BigInteger.valueOf((int) (567 * 1.54d)));
        addNewPgMar.setFooter(BigInteger.valueOf((int) (567 * 1.54d)));

        CTPageSz pgSz = addNewSectPr.addNewPgSz();
        pgSz.setW(BigInteger.valueOf((int) (PoiCommon.A4_WIDTH * PoiCommon.PER_CM)));
        pgSz.setH(BigInteger.valueOf((int) (PoiCommon.A4_HEIGHT * PoiCommon.PER_CM)));
        pgSz.setOrient(STPageOrientation.LANDSCAPE);
        // 第一行
        XWPFParagraph paragraph = document.createParagraph();

        XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();
        if (headerFooterPolicy == null){
            headerFooterPolicy = document.createHeaderFooterPolicy();
        }
        XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
        XWPFParagraph footerParagraph = footer.createParagraph();
        // 宋体, 小五号
        setInstrText(footerParagraph, "PAGE\\*MERGEFORMAT", "宋体", 9);
        //设置段落对象
        XWPFRun runSuf = footerParagraph.createRun();//新的段落对象
        runSuf.setText(" / ");
        setXWPFRunStyle(runSuf,"宋体",9);
        setInstrText(footerParagraph, "NUMPAGES\\* MERGEFORMAT", "宋体", 9);
        footerParagraph.setAlignment(ParagraphAlignment.CENTER);
        footerParagraph.getCTP().addNewPPr().addNewNumPr().addNewNumId().setVal(BigInteger.valueOf(1));

        // 设置段落的行间距属性
        XWPFRun run = paragraph.createRun();
        run.setText("测试页间距&nbsp;测试");
        run.setFontFamily("宋体");
        paragraph.setPageBreak(Boolean.TRUE);
        XWPFRun run1 = paragraph.createRun();
        for (int i = 0; i < 50; i++) {
            run1.addBreak();
            run1.setText("页间距"+i);
            run1.setFontFamily("宋体");
        }
        document.write(out);
        document.close();
    }

    @Test
    public void setRowSpacingMar() throws IOException {
        String fileName = "测试行间距.docx";
        String path = String.format("%s%s", BASE_DIR, fileName);
        System.out.println("path:" + path);
        FileOutputStream out = new FileOutputStream(new File(path));
        XWPFDocument document = new XWPFDocument();
        document.createStyles();
        XWPFHeaderFooterPolicy headerFooterPolicy = document.createHeaderFooterPolicy();
        CTSectPr addNewSectPr = document.getDocument().getBody().addNewSectPr();
//        CTPageMar addNewPgMar = addNewSectPr.addNewPgMar();
//        addNewPgMar.setLeft(BigInteger.valueOf(720L));
//        addNewPgMar.setTop(BigInteger.valueOf(720L));
//        addNewPgMar.setRight(BigInteger.valueOf(720L));
//        addNewPgMar.setBottom(BigInteger.valueOf(720L));
//        // 第一行
        addNewSectPr.addNewPgSz();
        CTPageSz pgSz = addNewSectPr.getPgSz();
        pgSz.setW(BigInteger.valueOf(1170));
        pgSz.setH(BigInteger.valueOf(1170));

        XWPFParagraph paragraph = document.createParagraph();
//        CTPPr paragraphProperties = paragraph.getCTP().addNewPPr();
//        // 设置段落的行间距属性
//        CTSpacing spacing = paragraphProperties.addNewSpacing();
//        spacing.setAfter(BigInteger.valueOf(0));  // 设置段后间距为0
//        spacing.setLine(BigInteger.valueOf(360));  // 设置行间距为1.5倍行距
//        paragraph.setSpacingLineRule(LineSpacingRule.EXACT);
//        paragraph.setSpacingBetween(1.5,);
//        XWPFRun run = paragraph.createRun();
//        run.setText("测试行间距&nbsp;测试");
//        run.setFontFamily("Times New Roman");
//        paragraph.setPageBreak(Boolean.TRUE);
        XWPFRun run1 = paragraph.createRun();
        Style style = new Style();
        style.setFontSize(10);
        style.setFontFamily("Times New Roman");
        TextRenderPolicy.Helper.renderTextRun(run1, new TextRenderData("abc", style));
        run1.addBreak();
//        // 第二行
//        paragraph = document.createParagraph();
//        // 设置段落的行间距属性
//        paragraphProperties = paragraph.getCTP().addNewPPr();
//        spacing = paragraphProperties.addNewSpacing();
//        spacing.setLine(BigInteger.valueOf(360));  // 设置行间距为1.5倍行距
//        run = paragraph.createRun();
//        run.setText("测试行间距");


        document.write(out);
        document.close();
    }

    @Test
    public void serialized() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = String.format("{\"time\":%s}", System.currentTimeMillis());
        Time time = objectMapper.readValue(json, Time.class);
        System.out.println(objectMapper.writer().writeValueAsString(time));
    }

    @Data
    public static final class Time {
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        private Date time;
    }

    @Test
    public void testAddCustomPolicy() throws IOException {
        String fileName = "测试表格插件.docx";
        String path = String.format("%s%s", BASE_DIR, fileName);
        String outputPath = String.format("%s%s", BASE_DIR, "测试表格插件_result.docx");

        Configure.ConfigureBuilder builder = new Configure.ConfigureBuilder();
        Configure configure = builder.addPlugin('~', new CustomizeTablePolicy()).build();
        Map<String, RenderData> renderDataMap = new HashMap<>();
        for(int i = 0; i < 3; i++){
            RowRenderData headRenderData = RowRenderData.build(new TextRenderData("标题头" + i), new TextRenderData("标题头2"));

            List<RowRenderData> dateRenderData = new ArrayList<>();
            for (int j = 0; j < 100; j++) {
                String text = String.format("第%s行第%s列", j, 0);
                String text1 = String.format("第%s行第%s列", j, 1);
                dateRenderData.add(RowRenderData.build(new TextRenderData(text), new TextRenderData(text1)));
            }

            MiniTableRenderData miniTableRenderData = new MiniTableRenderData(headRenderData, dateRenderData);

            renderDataMap.put(i + "", miniTableRenderData);
        }

        XWPFTemplate template = XWPFTemplate.compile(path, configure).render(renderDataMap);

        template.write(new FileOutputStream(outputPath));
    }

    @Test
    public void setHeaderAlways() throws IOException {
        String fileName = "表格表头测试.docx";
        String path = String.format("%s%s", BASE_DIR, fileName);
        System.out.println("path:" + path);
        FileOutputStream out = new FileOutputStream(new File(path));
        XWPFDocument document = new XWPFDocument();
        XWPFTable table = document.createTable(100, 8);
//        CTTblLayoutType ctTblLayoutType = table.getCTTbl().addNewTblPr().addNewTblLayout();
//        CTTblWidth ctTblWidth = table.getCTTbl().addNewTblPr().addNewTblW();
//        ctTblWidth.setW(BigInteger.valueOf(9072));
//        table.getCTTbl().addNewTblPr().addNewTblLayout().setType(STTblLayoutType.FIXED);
        List<XWPFTableRow> rows = table.getRows();
        float WIDTH_A4_FULL = 14.65F;
        TableTools.widthTable(table, WIDTH_A4_FULL, table.getRows().get(0).getTableCells().size());
        for (int i = 0; i < rows.size(); i++) {
            XWPFTableRow xwpfTableRow = rows.get(i);
            if (i == 0) {
                xwpfTableRow.setRepeatHeader(true);
            }
//            xwpfTableRow.setCantSplitRow(Boolean.FALSE);
            List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
            for (int j = 0; j < tableCells.size(); j++) {
                String cellText = FileConstant.EMPTY_STR;
                ParagraphAlignment paragraphAlignment = ParagraphAlignment.CENTER;
                if(i == 0){
                    cellText = "标题";
                }else{
                    cellText = "表格数据";
                }

                if (i != 0 && j % 2 == 0) {
                    cellText = "1000000000000.0000";
                    paragraphAlignment = ParagraphAlignment.LEFT;
                }
                tableCells.get(j).setText(cellText + i);
                tableCells.get(j).getParagraphs().get(0).setAlignment(paragraphAlignment);
            }
        }

        document.write(out);
    }


    @Test
    public void setParagraphSpacing() throws IOException {
        String fileName = "测试段落页边距.docx";
        String path = String.format("%s%s", BASE_DIR, fileName);
        System.out.println("path:" + path);
        FileOutputStream out = new FileOutputStream(new File(path));
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setSpacingBefore(157);
        paragraph.setSpacingAfter(157);
        XWPFRun run = paragraph.createRun();
        run.setText("第一个段落哦");
        CTRPr pr = run.getCTR().isSetRPr() ? run.getCTR().getRPr() : run.getCTR().addNewRPr();
        CTHpsMeasure ctSize = pr.isSetSz() ? pr.getSz() : pr.addNewSz();
        ctSize.setVal(BigInteger.valueOf(((int) (new BigInteger("2").longValue() * 10.5d))));

        XWPFParagraph paragraph1 = document.createParagraph();
//        paragraph1.setPageBreak(Boolean.TRUE);
        XWPFRun run1 = paragraph1.createRun();
        run1.setText("第二个段落哦");

        document.write(out);
    }


    @Test
    public void testPageNumberConvertPdfMissing() throws IOException {
        // 生成页码
//        setPageMar();
        String fileName = "null__33d1bb7284ad4fb4857cd3f35044beb4.docx";
        String targetName = "null__33d1bb7284ad4fb4857cd3f35044beb4.pdf";
        String srcPath = String.format("%s%s", BASE_DIR, fileName);
        String targetPath = String.format("%s%s", BASE_DIR, targetName);
        // pdf转换
        PoiTlUtils.wordToPdf(srcPath, targetPath);

    }

}
