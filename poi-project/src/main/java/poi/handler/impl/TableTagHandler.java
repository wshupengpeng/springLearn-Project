package poi.handler.impl;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.util.TableTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.common.PoiCommon;
import poi.handler.param.CellMergeRecord;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;
import poi.handler.utils.JsoupUtils;

import java.util.*;
import java.util.function.Function;

/**
 * @creater hpp
 * @Date 2023/3/6-21:39
 * @description:
 */
@Slf4j
public class TableTagHandler extends AbstractHtmlTagHandler {

    public TableTagHandler() {
        HtmlToWordUtils.put(getTagName(),this);
    }

    private static final String ROW_SPAN = "rowspan";
    private static final String COL_SPAN = "colspan";

    @Override
    public String getTagName() {
        return "table";
    }

    @Override
    public void handler(DocumentParam documentParam) {
        Node currentNode = documentParam.getCurrentNode();
        Element tableEle = (Element) currentNode;

        // 获取表格行数
        Elements tr = tableEle.getElementsByTag("tr");
        // 获取当前表头字段
        Elements th = tableEle.getElementsByTag("th");
        // 获取表格内容
        Elements td = tableEle.getElementsByTag("td");


        XWPFTable table = documentParam.getDoc().createTable();
        table.removeRow(0);
        // 创建单元格,并获取合并记录
        List<CellMergeRecord> cellAndGetMergeRecords = createTableCellAndGetMergeRecord(tr, table, documentParam);

        for (int i = 0; i < table.getRows().size(); i++) {
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
//                    XWPFTableCell cell = row.getCell(j);
//                    if(i == cellAndGetMergeRecord.getRowStartIndex() && j == cellAndGetMergeRecord.getColStartIndex()){
////                        cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.RESTART);
//                        continue;
//                    }
//                    cell.getCTTc().getTcPr().getHMerge().setVal(STMerge.CONTINUE);
//                    cell.getCTTc().getTcPr().getVMerge().setVal(STMerge.CONTINUE);
//                    log.info("continue row:{},col:{},HMerge:{},VMerge:{}", i, j,
//                            cell.getCTTc().getTcPr().getHMerge().getVal(), cell.getCTTc().getTcPr().getVMerge().getVal());
                }
            }
        }

        for (int i = 0; i < table.getRows().size(); i++) {
            for(int j = 0; j < table.getRow(i).getTableCells().size(); j++){
                log.info("i:{},j:{},HmergeValue:{},VmergeValue:{}", i, j,
                        table.getRow(i).getCell(j).getCTTc().getTcPr().getHMerge().getVal().toString(),
                        table.getRow(i).getCell(j).getCTTc().getTcPr().getVMerge().getVal());
            }
        }

        // 方式一,按照rowspan和colspan的值进行手动创建cell单元格
//        int colOffset = 0;
//        int rowOffset = 0;
//        Map<Integer,Integer> offsetMap = new HashMap<>();
//        for (int i = 0; i < rowElementList.size(); i++) {
//            List<Element> row = rowElementList.get(i);
//            for (int j = 0; j < row.size(); j++) {
//                int colOffset = offsetMap.getOrDefault(i, 0);
//                Element col = row.get(j);
//                int rowspan = Integer.parseInt(col.hasAttr("rowspan") ? col.attr("rowspan") : "1");
//                int colspan = Integer.parseInt(col.hasAttr("colspan") ? col.attr("colspan") : "1");
//                XWPFTableRow xwpfTableRow;
//                Map<Integer, Boolean> mergeMap = new HashMap();
//
//                if ((xwpfTableRow = table.getRow(i)) == null) {
//                    xwpfTableRow = table.insertNewTableRow(i);
//                }
//
//                if (rowspan > 1) {
//                    for (int rowNum = 1; rowNum < rowspan; rowNum++) {
//                        xwpfTableRow = table.insertNewTableRow(rowNum + i );
//                        mergeMap.put(rowNum, Boolean.TRUE);
//                    }
//                }
//
//                for(int rowNum = 0; rowNum < rowspan; rowNum++){
//                    xwpfTableRow = table.getRow(i + rowNum);
//                    XWPFTableCell cell;
//                    for(int cellNum = 0; cellNum < colspan; cellNum++){
//                        if ((cell = xwpfTableRow.getCell(j + colOffset + cellNum)) == null) {
//                            cell = xwpfTableRow.createCell();
//                        }
//                        if(cell.getCTTc().getTcPr() == null) {
//                            cell.getCTTc().addNewTcPr();
//                        }
//
//                        if(cell.getCTTc().getTcPr().getHMerge() == null){
//                            cell.getCTTc().getTcPr().addNewHMerge();
//                        }
//
//                        if(mergeMap.getOrDefault(rowNum,Boolean.FALSE) || cellNum != 0){
//                            cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
//                            log.info("row:{},col:{},val:{}",i  + rowNum,j + colOffset + cellNum,"continue");
//                        }else{
//                            cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.RESTART);
//                            log.info("row:{},col:{},val:{}",i  + rowNum,j + colOffset + cellNum,"restart");
//                        }
//                    }
//                }
//
//                // 添加数据
//                XWPFTableCell cell = table.getRow(i).getCell(j + colOffset);
//                XWPFParagraph xwpfParagraph = cell.addParagraph();
//                DocumentParam tableDocumentParam = new DocumentParam();
//                tableDocumentParam.setStyle(documentParam.getStyle());
//                tableDocumentParam.setCurrentParagraph(xwpfParagraph);
//                tableDocumentParam.setDoc(documentParam.getDoc());
//                HtmlToWordUtils.parseTagByName(tableDocumentParam, col);
//
//                // 更新偏移量
//                for(int k = 0; k < rowspan; k++){
//                    offsetMap.put(k, colspan-1);
//                }
////                rowOffset += rowspan-1;
////                colOffset += colspan-1;
////                log.info("rowOffset:{},colOffset:{}", rowOffset, colOffset);
//            }
//        }
        //设置整个表格大小
        TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, table.getRows().get(0).getTableCells().size());

        documentParam.setCurrentParagraph(documentParam.getDoc().createParagraph());
        // 汇总行列
//        int row = tr.size();
//
//        int col = td.size();
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
    public List<CellMergeRecord> createTableCellAndGetMergeRecord(Elements tr, XWPFTable table, DocumentParam documentParam) {
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
                                placeholderIndex = ceateMergeCell(placeholderMap, i, xwpfTableRow, placeholderKey);
                            }
                        }else{
                            // 记录下一行需要添加的单元格位置
                            placeholderKey = String.format("%s-%s", i + row, j + colOffset);
                            placeholderMap.put(placeholderKey, j + colOffset + colSpan);
                        }
                    }
                } else {
                    placeholderIndex = ceateMergeCell(placeholderMap, i, xwpfTableRow, placeholderKey);
                }


                XWPFTableCell cell = xwpfTableRow.getCell(placeholderIndex == 0 ? j + colOffset : placeholderIndex);
//                XWPFTableCell cell = table.getRow(i).getTableCells().get(table.getRow(i).getTableCells().size() - 1);
                log.info("i:{},col:{},value:{}", i, placeholderIndex == 0 ? j + colOffset : placeholderIndex,element.text());
                XWPFParagraph xwpfParagraph = cell.addParagraph();
                DocumentParam tableDocumentParam = new DocumentParam();
                tableDocumentParam.setStyle(documentParam.getStyle());
                tableDocumentParam.setCurrentParagraph(xwpfParagraph);
                tableDocumentParam.setDoc(documentParam.getDoc());
                HtmlToWordUtils.parseTagByName(tableDocumentParam, element);

                colOffset += (colSpan - 1 + placeholderIndex);
            }
        }

        return mergeRecordList;
    }

    private int ceateMergeCell(Map<String, Integer> placeholderMap, int row, XWPFTableRow xwpfTableRow, String placeholderKey) {
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
