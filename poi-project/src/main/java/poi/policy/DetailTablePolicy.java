package poi.policy;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;
import com.deepoove.poi.util.TableTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description: poi插件策略
 * @Author 01415355
 * @Date 2023/4/24 9:15
 */
@Slf4j
public class DetailTablePolicy extends AbstractRenderPolicy<DetailTable> {
    @Override
    public void doRender(RenderContext<DetailTable> renderContext) throws Exception {
        logger.info("插件导入成功");
        XWPFRun run = renderContext.getRun();
        DetailTable data = renderContext.getData();
        NiceXWPFDocument doc = (NiceXWPFDocument)run.getParent().getDocument();

        // 转换为minitable
        String[] head = data.getData().get(0).toArray(new String[data.getData().get(0).size()]);
        MiniTableRenderData renderData = new MiniTableRenderData(RowRenderData.build(head),
                data.getData().stream().map((row) -> RowRenderData.build(row.toArray(new String[row.size()]))).collect(Collectors.toList()));

        renderTable(doc, run, renderData);
        logger.info("data:{}", JSONObject.toJSONString(data));
//        render(renderContext.getEleTemplate(),renderContext.getData(),renderContext.getXWPFDocument());
    }

    private static void renderTable(NiceXWPFDocument doc, XWPFRun run,
                                    MiniTableRenderData tableData) {
        // 1.计算行和列
        int row = tableData.getDatas().size(), col = 0;
        if (!tableData.isSetHeader()) {
            col = getMaxColumFromData(tableData.getDatas());
        } else {
            row++;
            col = tableData.getHeader().size();
        }

        // 2.创建表格
        XWPFTable table = doc.insertNewTable(run, row, col);
        TableTools.initBasicTable(table, col, tableData.getWidth(), tableData.getStyle());

        // 3.渲染数据
        int startRow = 0;
        if (tableData.isSetHeader()) MiniTableRenderPolicy.Helper.renderRow(table, startRow++, tableData.getHeader());
        for (RowRenderData data : tableData.getDatas()) {
            MiniTableRenderPolicy.Helper.renderRow(table, startRow++, data);
        }

        // 合并单元格

    }

//    public static void renderRow(XWPFTable table, int row, RowRenderData rowData) {
//        if (null != rowData && rowData.size() > 0) {
//            XWPFTableRow tableRow = table.getRow(row);
//            Objects.requireNonNull(tableRow, "Row " + row + " do not exist in the table");
//            TableStyle rowStyle = rowData.getRowStyle();
//            List<CellRenderData> cellList = rowData.getCellDatas();
//            XWPFTableCell cell = null;
//
//            for(int i = 0; i < cellList.size(); ++i) {
//                cell = tableRow.getCell(i);
//                if (null == cell) {
//                    log.warn("Extra cell data at row {}, but no extra cell: col {}", row, cell);
//                    break;
//                }
//
//                renderCell(cell, (CellRenderData)cellList.get(i), rowStyle);
//            }
//
//        }
//    }
//
//    public static void renderCell(XWPFTableCell cell, CellRenderData cellData, TableStyle rowStyle) {
//        TableStyle cellStyle = null == cellData.getCellStyle() ? rowStyle : cellData.getCellStyle();
//        if (null != cellStyle && null != cellStyle.getBackgroundColor()) {
//            cell.setColor(cellStyle.getBackgroundColor());
//        }
//
//        TextRenderData renderData = cellData.getRenderData();
//        String cellText = renderData.getText();
//        if (!StringUtils.isBlank(cellText)) {
//            CTTc ctTc = cell.getCTTc();
//            CTP ctP = ctTc.sizeOfPArray() == 0 ? ctTc.addNewP() : ctTc.getPArray(0);
//            XWPFParagraph par = new XWPFParagraph(ctP, cell);
//            StyleUtils.styleTableParagraph(par, cellStyle);
//            String text = renderData.getText();
//            String[] fragment = text.split("\\n", -1);
//            if (fragment.length <= 1) {
//                com.deepoove.poi.policy.TextRenderPolicy.Helper.renderTextRun(par.createRun(), renderData);
//            } else {
//                for(int j = 0; j < fragment.length; ++j) {
//                    if (0 != j) {
//                        par = cell.addParagraph();
//                        StyleUtils.styleTableParagraph(par, cellStyle);
//                    }
//
//                    XWPFRun run = par.createRun();
//                    StyleUtils.styleRun(run, renderData.getStyle());
//                    run.setText(fragment[j]);
//                }
//            }
//
//        }
//    }
//
    private static int getMaxColumFromData(List<RowRenderData> datas) {
        int maxColom = 0;
        Iterator var2 = datas.iterator();

        while(var2.hasNext()) {
            RowRenderData obj = (RowRenderData)var2.next();
            if (null != obj && obj.size() > maxColom) {
                maxColom = obj.size();
            }
        }

        return maxColom;
    }

    public void render(XWPFTable table, Object o) {
        if(o == null || !(o instanceof DetailTable)) return;

        DetailTable detailTable = (DetailTable) o;

        List<List<String>> data = detailTable.getData();

        // 循环插入行
        for (int i = 0; i < data.size(); i++) {
            XWPFTableRow insertNewTableRow = table.createRow();
            for (int j = 0; j < data.get(i).size(); j++){
                insertNewTableRow.createCell().setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            }

            RowRenderData renderData = RowRenderData.build(data.get(i).toArray(new String[data.get(i).size()]));

            MiniTableRenderPolicy.Helper.renderRow(table, i, renderData);
        }

        // 合并单元格


    }

//    @Override
//    public void render(ElementTemplate elementTemplate, Object data, XWPFTemplate template) {
//        NiceXWPFDocument doc = template.getXWPFDocument();
//        RunTemplate runTemplate = (RunTemplate)elementTemplate;
//        XWPFRun run = runTemplate.getRun();
//        run.setText("", 0);
//
//        try {
////            if (!TableTools.isInsideTable(run)) {
////                throw new IllegalStateException("The template tag " + runTemplate.getSource() + " must be inside a table");
////            } else {
//            XmlCursor newCursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
//            newCursor.toParent();
//            newCursor.toParent();
//            newCursor.toParent();
//            XmlObject object = newCursor.getObject();
//            XWPFTable table = doc.getTableByCTTbl((CTTbl) object);
////            this.render(table, data);
////            }
//        } catch (Exception var10) {
//            throw new RenderException("dynamic table error:" + var10.getMessage(), var10);
//        }
//    }
}
