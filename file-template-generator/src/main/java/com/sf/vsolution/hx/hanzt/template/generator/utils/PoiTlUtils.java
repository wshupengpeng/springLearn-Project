package com.sf.vsolution.hx.hanzt.template.generator.utils;

import com.deepoove.poi.data.style.Style;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import com.sf.vsolution.hx.hanzt.template.generator.param.ImageConfig;
import com.sf.vsolution.hx.hanzt.template.generator.provider.CustomizeFontProvider;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;

/**
 * @author 01410083
 * @description poi-tl用模板生成word及相关工具类
 * @date 2021/10/12
 */
@Slf4j
public class PoiTlUtils {
    /**表格相关样式设置 */
    public static final int MIN_ROW_SIZE = 4;
    public static final int ADDRESS_MAX_LINE_LENGTH = 24;
    public static final int TABLE_FONT_SIZE = 7;
    public static final int FONT_SIZE = 9;
    public static final String TABLE_FONT_FAMILY = "SimSun";
    public static final String TABLE_FONT_COLOR = "000000";
    public static final float TABLE_WIDTH = 16.00f;
    public static final Style TEXT_STYLE = new Style();
    public static final Style TABLE_STYLE = new Style();

    static {
        TEXT_STYLE.setFontSize(FONT_SIZE);
        TEXT_STYLE.setFontFamily(TABLE_FONT_FAMILY);
        TEXT_STYLE.setColor(TABLE_FONT_COLOR);
        TABLE_STYLE.setFontSize(TABLE_FONT_SIZE);
        TABLE_STYLE.setFontFamily(TABLE_FONT_FAMILY);
        TABLE_STYLE.setColor(TABLE_FONT_COLOR);
    }


    /**
     * 将word转换为pdf
     * @param docPath
     * @param pdfPath
     * @throws IOException
     */
    public static void wordToPdf(String docPath,String pdfPath) {
        try(InputStream doc = new FileInputStream(docPath);
            XWPFDocument document= new XWPFDocument(doc);
            OutputStream out = new FileOutputStream(pdfPath)){
            setFontType(document);
            PdfOptions options = PdfOptions.create();
            options.fontProvider(CustomizeFontProvider.getInstance());
            PdfConverter.getInstance().convert(document, out, options);

        }catch (Exception e){
            log.error("wordToPdf failed ", e);
        }
    }

    /***
     * 设置字体颜色,防止转换发生异常
     * @Description:
     * @param xwpfDocument
     * @return:
     */
    private static void setFontType(XWPFDocument xwpfDocument) {
        //转换文档中文字字体
        List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
        if(paragraphs != null && paragraphs.size()>0){
            for (XWPFParagraph paragraph : paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();
                if(runs !=null && runs.size()>0){
                    for (XWPFRun run : runs) {
                        if(StringUtils.isEmpty(run.getColor())){
                            run.setColor("000000");
                        }
                    }
                }
            }
        }
        //转换表格里的字体 我也不想俄罗斯套娃但是不套真不能设置字体
        List<XWPFTable> tables = xwpfDocument.getTables();
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rows = table.getRows();
            for (XWPFTableRow row : rows) {
                List<XWPFTableCell> tableCells = row.getTableCells();
                for (XWPFTableCell tableCell : tableCells) {
                    List<XWPFParagraph> paragraphs1 = tableCell.getParagraphs();
                    for (XWPFParagraph xwpfParagraph : paragraphs1) {
                        List<XWPFRun> runs = xwpfParagraph.getRuns();
                        for (XWPFRun run : runs) {
                            if(StringUtils.isEmpty(run.getColor())){
                                run.setColor("000000");
                            }
                        }
                    }
                }
            }
        }
    }


    public static void addImgOnPdf(String templatePath, ImageConfig imageConfig){
        PdfReader reader = null;
        PdfStamper stamper = null;
        // 读取放置图片的规则
        // 读取模板文件
        try(InputStream input = new FileInputStream(new File(templatePath))){
            reader = new PdfReader(input);
            stamper= new PdfStamper(reader, new FileOutputStream(
                    templatePath));
            // 提取pdf中的表单
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));


            // 读图片
            Image image = Image.getInstance(imageConfig.getImagePath());
            // 获取操作的页面
            PdfContentByte under = stamper.getOverContent(1);
            //以下参数是根据模板要求适配的
            // 根据域的大小缩放图片
            image.scaleToFit(Float.parseFloat(imageConfig.getFitWidth()), Float.parseFloat(imageConfig.getFitHeight()));
            // 添加图片
            image.setAbsolutePosition(Float.parseFloat(imageConfig.getAbsoluteX()), Float.parseFloat(imageConfig.getAbsoluteY()));
            under.addImage(image);
            stamper.close();
            reader.close();
        } catch (Exception e) {
            log.error("将二维码添加到pdf上失败",e);
        }finally {
            if(reader != null){
                try {
                    reader.close();
                }catch (Exception e){
                    log.error("close pdfReader failed");
                    reader = null;
                }
            }
            if(stamper != null){
                try {
                    stamper.close();
                }catch (Exception e){
                    log.error("close PdfStamper failed");
                    stamper = null;
                }
            }
        }

    }

}

