package poi;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.util.TableTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblLayoutType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblLayoutType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Description: poi基本操作学习
 * @Author 01415355
 * @Date 2023/3/6 9:20
 */
@Slf4j
public class PoiBaseOperator {

    public static final XWPFDocument doc = new XWPFDocument();

    public static final String descPath = "d://hpp//test.doc";

    @Before
    public void before(){
        File file = new File(descPath);
        file.deleteOnExit();
    }


    @After
    public void after() throws IOException {
        doc.write(new FileOutputStream(descPath));
    }

    @Test
    public void operatorXWPFParagraph() {
        // 创建段落
        XWPFParagraph xwpfParagraph = doc.createParagraph();
        // 设置段落属性
        // 对齐方式
        xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);
        // 边框
        xwpfParagraph.setBorderBottom(Borders.DOUBLE);
        xwpfParagraph.setBorderTop(Borders.DOUBLE);
        xwpfParagraph.setBorderRight(Borders.DOUBLE);
        xwpfParagraph.setBorderLeft(Borders.DOUBLE);
        xwpfParagraph.setBorderBetween(Borders.SINGLE);


        // 基本操作元素
        XWPFRun xwpfRun = xwpfParagraph.createRun();
        // 在当前段落中插入文本
        xwpfRun.setBold(true);
        xwpfRun.setColor("67C359");
        xwpfRun.setItalic(true);
        xwpfRun.setShadow(true);
        xwpfRun.setFontSize(20);
        // 获取文字
        String text = xwpfParagraph.getText();

        // 操作表格
        // 获取当前段落的run基本操作对象
        XWPFTable table = doc.createTable(5, 5);
        doc.insertTable(1,table);
        // 设置表格宽度
        XWPFParagraph xwpfParagraph1 = table.getRow(0).getCell(0).addParagraph();
        xwpfParagraph1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run1 = xwpfParagraph1.createRun();
        TextRenderPolicy.Helper.renderTextRun(run1, new TextRenderData(text, new Style()));
        CTTblLayoutType type = table.getCTTbl().getTblPr().addNewTblLayout();
        type.setType(STTblLayoutType.AUTOFIT);
        //设置整个表格大小
        TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, 5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                XWPFTableCell cell = table.getRow(i).getCell(j);
                if(j % 2 == 0){
                    cell.setWidth("50");
                    cell.setText("这是50格式文字123");
               }else{
                    cell.setWidth("10");
                    cell.setText("这是10格式文字123");
                }
            }
        }
        // 合并表格,下标从0开始
        TableTools.mergeCellsHorizonal(table,1,1,2);
        List<XWPFParagraph> paragraphs = doc.getParagraphs();
        paragraphs.forEach(p -> {
            String text1 = p.getParagraphText();
            log.info("test:{}", text1);
        });
    }

    @Test
    public void mergeTable(){
        XWPFTable table = doc.createTable(6,6);
        // 设置行距
        table.setWidthType(TableWidthType.AUTO);
        TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, 5);
        // 测试单元格合并，必须先restart 再 continue，否则合并不生效
//        XWPFTableCell cell = table.getRow(0).getCell(0);
//        cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
//        cell.setText("continue");
//        XWPFTableCell cell1 = table.getRow(0).getCell(1);
//        cell1.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
//        cell1.setText("restart");
        boolean skipFlag = false;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                XWPFTableCell cell = table.getRow(i).getCell(j);
                // 双数数合并列
                if(j % 2 == 0){
                    if(cell.getCTTc().getTcPr() == null){
                        log.info("TcPr is null");
                        cell.getCTTc().addNewTcPr();
                        if(cell.getCTTc().getTcPr().getHMerge() == null){
                            log.info("HMerge is null");
                            cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.RESTART);
                        }else{
                            cell.getCTTc().getTcPr().getHMerge().setVal(STMerge.RESTART);
                        }
                    }else{
                        if(cell.getCTTc().getTcPr().getHMerge() == null){
                            log.info("HMerge is null");
                            cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.RESTART);
                        }else{
                            cell.getCTTc().getTcPr().getHMerge().setVal(STMerge.RESTART);
                        }
                    }
                    if(skipFlag) {
                        skipFlag = false;
                        continue;
                    }
                    cell.setText("restart");
//                    String text = cell.getText();
//                    if(!StringUtils.isBlank(text)){
//                        log.info("contains text");
//                        cell.setText("recover text");
//                    }
                }else{
                    if(cell.getCTTc().getTcPr() == null){
                        log.info("TcPr is null");
                        cell.getCTTc().addNewTcPr();
                        if(cell.getCTTc().getTcPr().getHMerge() == null){
                            log.info("HMerge is null");
                            cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
                        }else{
                            cell.getCTTc().getTcPr().getHMerge().setVal(STMerge.CONTINUE);
                        }
                    }else{
                        if(cell.getCTTc().getTcPr().getHMerge() == null){
                            log.info("HMerge is null");
                            cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
                        }else{
                            cell.getCTTc().getTcPr().getHMerge().setVal(STMerge.CONTINUE);
                        }
                    }
//                    cell.setText("continue");
                }
            }
            skipFlag = !skipFlag;
        }

    }



}
