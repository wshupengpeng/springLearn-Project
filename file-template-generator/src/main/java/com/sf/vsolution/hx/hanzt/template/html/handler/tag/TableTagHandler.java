package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import com.deepoove.poi.util.TableTools;
import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.CellMergeRecord;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import com.sf.vsolution.hx.hanzt.template.html.param.TextFormatStyle;
import com.sf.vsolution.hx.hanzt.template.html.parse.RichTextParser;
import com.sf.vsolution.hx.hanzt.template.html.utils.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGrid;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.util.*;

/**
 * @creater hpp
 * @Date 2023/3/6-21:39
 * @description:
 */
@Slf4j
public class TableTagHandler extends AbstractHtmlTagHandler {

    private static final String ROW_SPAN = "rowspan";
    private static final String COL_SPAN = "colspan";

    public static final float WIDTH_A4_FULL = 14.65F;
    public static final float WIDTH_A4_NARROW_FULL = 18.45F;
    public static final float WIDTH_A4_MEDIUM_FULL = 17.17F;
    public static final float WIDTH_A4_EXTEND_FULL = 10.83F;

    @Override
    public String getTagName() {
        return "table";
    }

    @Override
    public void doHandle(RichText richText) {
        Node currentNode = richText.getCurrentNode();
        Element tableEle = (Element) currentNode;

        // 获取表格行数
        Elements tr = tableEle.getElementsByTag("tr");
        // 获取当前表头字段
//        Elements th = tableEle.getElementsByTag("th");
        // 获取表格内容
//        Elements td = tableEle.getElementsByTag("td");


        XWPFTable table = richText.getDoc().createTable();
        CTTblGrid grid = table.getCTTbl().getTblGrid();
        if (grid == null) {
            table.getCTTbl().addNewTblGrid();
        }

        table.removeRow(0);
        // 创建单元格,并获取合并记录
        List<CellMergeRecord> cellAndGetMergeRecords = createTableCellAndGetMergeRecord(tr, table, richText);

        for (int i = 0; i < table.getRows().size(); i++) {
            // 转换pdf的时候如果没有这个可能会报空指针
            XWPFTableRow row = table.getRow(i);
            for (XWPFTableCell cell : row.getTableCells()) {
                CTTblWidth ctTblWidth = cell.getCTTc().addNewTcPr().addNewTcW();
//                ctTblWidth.setW(new BigInteger(width));
                ctTblWidth.setType(STTblWidth.DXA);
            }
            log.info("create table row:{},colSize:{}",i,table.getRow(i).getTableCells().size());
        }

        for (CellMergeRecord cellAndGetMergeRecord : cellAndGetMergeRecords) {
            for (int i = cellAndGetMergeRecord.getRowStartIndex(); i < cellAndGetMergeRecord.getRowEndIndex(); i++) {
                XWPFTableRow row = table.getRow(i);
                for (int j = cellAndGetMergeRecord.getColStartIndex(); j < cellAndGetMergeRecord.getColEndIndex(); j++) {
                    XWPFTableCell cell = row.getCell(j);
                    if(j == cellAndGetMergeRecord.getColStartIndex()){
                        cell.getCTTc().getTcPr().getHMerge().setVal(STMerge.RESTART);
                    }else{
                        cell.getCTTc().getTcPr().getHMerge().setVal(STMerge.CONTINUE);
                    }

                    if(i == cellAndGetMergeRecord.getRowStartIndex()){
                        cell.getCTTc().getTcPr().getVMerge().setVal(STMerge.RESTART);
                    }else{
                        cell.getCTTc().getTcPr().getVMerge().setVal(STMerge.CONTINUE);
                    }
                }
            }
        }

        //设置整个表格大小
        TableTools.widthTable(table, WIDTH_A4_FULL, table.getRows().get(0).getTableCells().size());


        richText.setCurrentParagraph(richText.getDoc().createParagraph());
        // 跳过子节点迭代
        richText.setContinueItr(true);
    }


    /**
     * 解析table获取表行数
     * @param tr
     * @return
     */
    public List<List<Element>> getRowElementList(Elements tr){
        Iterator<Element> iterator = tr.iterator();
        Element next;
        List<List<Element>> rowList = new ArrayList<>();
        while(iterator.hasNext()){
            List<Element> row = new ArrayList<>();
            next = iterator.next();
            Elements th = next.select("th");
            if (!th.isEmpty()) {
                th.stream().forEach(element -> {
                    row.add(element);
                });
            }
            Elements td = next.select("td");
            if(!td.isEmpty()){
                td.stream().forEach(element -> {
                    row.add(element);
                });
            }
            rowList.add(row);
        }
        return rowList;
    }

    /**
     *  创建table cell 并且获取合并记录
     *  按照wangeditor方式进行解析，不一定适用于所有富文本
     * @param tr
     * @param table
     * @return
     */
    public List<CellMergeRecord> createTableCellAndGetMergeRecord(Elements tr, XWPFTable table, RichText richText) {
        List<List<Element>> rowElementList = getRowElementList(tr);
        List<CellMergeRecord> mergeRecordList = new ArrayList<>();
        // 记录rowspan和colspan 占位记录
        Map<String,Integer> placeholderMap = new HashMap<>();
        for (int i = 0; i < rowElementList.size(); i++) {
            List<Element> cols = rowElementList.get(i);
            int colOffset = 0;
            int placeholderIndex = 0;
            for (int j = 0; j < cols.size(); j++) {
                Element element = cols.get(j);
                // 获取rowspan和colspan
                Integer rowSpan = JsoupUtils.getAttribute(element, ROW_SPAN,
                        Integer::valueOf, PoiCommon.SPAN_DEFAULT_VALUE);
                Integer colSpan = JsoupUtils.getAttribute(element, COL_SPAN,
                        Integer::valueOf, PoiCommon.SPAN_DEFAULT_VALUE);


                XWPFTableRow xwpfTableRow = table.getRow(i);
                if (xwpfTableRow == null) {
                    xwpfTableRow = table.insertNewTableRow(i);
                }

                String placeholderKey =  String.format("%s-%s", i, j + colOffset);
                if (rowSpan > 1 || colSpan > 1) {
                    // 合并单元格记录,为下一步合并做准备
                    CellMergeRecord cellMergeRecord = new CellMergeRecord();
                    cellMergeRecord.setRowStartIndex(i);
                    cellMergeRecord.setRowEndIndex(i + rowSpan);
                    cellMergeRecord.setColStartIndex(j + colOffset);
                    cellMergeRecord.setColEndIndex(j + colOffset + colSpan);
                    mergeRecordList.add(cellMergeRecord);

                    for (int row = 0; row < rowSpan; row++) {
                        if(row == 0){
                            for (int col = 0; col < colSpan; col++) {
                                // placeholderKey 是记录startRow 和 colStart 的位置
                                placeholderKey = String.format("%s-%s", row, col + j + colOffset);
                                placeholderIndex = ceateCell(placeholderMap, i, xwpfTableRow, placeholderKey);
                            }
                        }else{
                            // 记录下一行需要添加的单元格位置
                            placeholderKey = String.format("%s-%s", i + row, j + colOffset);
                            placeholderMap.put(placeholderKey, j + colOffset + colSpan);
                        }
                    }
                } else {
                    placeholderIndex = ceateCell(placeholderMap, i, xwpfTableRow, placeholderKey);
                }


                XWPFTableCell cell = xwpfTableRow.getCell(placeholderIndex == 0 ? j + colOffset : placeholderIndex);
                XWPFParagraph xwpfParagraph = cell.addParagraph();
//                XWPFParagraph xwpfParagraph = richText.insertNewParagraph();
                RichText childRichText = new RichText();
                childRichText.setTextFormatStyle((TextFormatStyle) richText.getTextFormatStyle().clone());
                childRichText.setCurrentParagraph(xwpfParagraph);
                childRichText.setDoc(richText.getDoc());
                childRichText.setCurrentNode(element);
                RichTextParser.parseTag(element.tagName(), childRichText);

                colOffset += (colSpan - 1 + placeholderIndex);
            }
        }

        return mergeRecordList;
    }

    private int ceateCell(Map<String, Integer> placeholderMap, int row, XWPFTableRow xwpfTableRow, String placeholderKey) {
        int colEndIndex = 0;
        while (placeholderMap.containsKey(placeholderKey)) {
            // 如果placeholderMap 有当前记录,则获取colEndIndex添加单元格
            colEndIndex = placeholderMap.get(placeholderKey);
            addCell(placeholderKey, colEndIndex, xwpfTableRow);
            placeholderMap.remove(placeholderKey);
            placeholderKey = String.format("%s-%s", row, colEndIndex);
        }
        setMergeEnum(xwpfTableRow.createCell(), STMerge.RESTART);
        return colEndIndex;
    }

    private void addCell(String placeholderKey,Integer colEndIndex, XWPFTableRow xwpfTableRow) {
        String[] split = placeholderKey.split("-");
        for (int i = Integer.valueOf(split[1]); i < colEndIndex; i++) {
            setMergeEnum(xwpfTableRow.createCell(), STMerge.RESTART);
        }
    }


    private void setMergeEnum(XWPFTableCell cell,STMerge.Enum mergeEnum){
        if (cell.getCTTc().getTcPr() == null) {
            cell.getCTTc().addNewTcPr();
        }

        if (cell.getCTTc().getTcPr().getHMerge() == null) {
            cell.getCTTc().getTcPr().addNewHMerge();
        }


        if (cell.getCTTc().getTcPr().getVMerge() == null) {
            cell.getCTTc().getTcPr().addNewVMerge();
        }

        cell.getCTTc().getTcPr().getHMerge().setVal(mergeEnum);
        cell.getCTTc().getTcPr().getVMerge().setVal(mergeEnum);
    }

}
