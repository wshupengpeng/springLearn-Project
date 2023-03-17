package poi.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;
import poi.handler.utils.HtmlToWordUtils;

import java.util.*;

/**
 * @creater hpp
 * @Date 2023/3/6-21:39
 * @description:
 */
@Slf4j
public class TableTagHandler extends AbstractHtmlTagHandler {
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


        List<List<Element>> rowElementList = getRowElementList(tr);

        XWPFTable table = documentParam.getDoc().createTable();
        // 方式一,按照rowspan和colspan的值进行手动创建cell单元格
        int rowOffset = 0;

        for (int i = 0; i < rowElementList.size(); i++) {
            List<Element> row = rowElementList.get(i);
            int colOffset = 0;
            for (int j = 0; j < row.size(); j++) {
                Element col = row.get(j);
                int rowspan = Integer.parseInt(col.hasAttr("rowspan") ? col.attr("rowspan") : "1");
                int colspan = Integer.parseInt(col.hasAttr("colspan") ? col.attr("colspan") : "1");
                XWPFTableRow xwpfTableRow;
                Map<Integer, Boolean> mergeMap = new HashMap();

                if ((xwpfTableRow = table.getRow(i + rowOffset)) == null) {
                    xwpfTableRow = table.insertNewTableRow(i);
                    if (rowspan > 1) {
                        for (int rowNum = 1; rowNum < rowspan; rowNum++) {
                            xwpfTableRow = table.insertNewTableRow(rowNum + i + rowOffset);
                            mergeMap.put(rowNum, Boolean.TRUE);
                        }
                    }
                }

                for(int rowNum = 0; rowNum < rowspan; rowNum++){
                    xwpfTableRow = table.getRow(i + rowOffset + rowNum);
                    XWPFTableCell cell;
                    for(int cellNum = 0; cellNum < colspan; cellNum++){
                        if ((cell = xwpfTableRow.getCell(j + colOffset + cellNum)) == null) {
                            cell = xwpfTableRow.createCell();

                            if(cell.getCTTc().getTcPr() == null) {
                                cell.getCTTc().addNewTcPr();
                            }

                            if(cell.getCTTc().getTcPr().getHMerge() == null){
                                cell.getCTTc().getTcPr().addNewHMerge();
                            }

                            if(mergeMap.getOrDefault(rowNum,Boolean.FALSE)){
                                cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
                            }else{
                                cell.getCTTc().getTcPr().addNewHMerge().setVal(STMerge.RESTART);
                            }
                        }
                    }
                }

                // 添加数据
                XWPFTableCell cell = table.getRow(i + rowOffset).getCell(j + colOffset);
                XWPFParagraph xwpfParagraph = cell.addParagraph();
                DocumentParam tableDocumentParam = new DocumentParam();
                tableDocumentParam.setStyle(documentParam.getStyle());
                tableDocumentParam.setCurrentParagraph(xwpfParagraph);
                tableDocumentParam.setDoc(documentParam.getDoc());
                HtmlToWordUtils.parseTagByName(tableDocumentParam, col);

                rowOffset += rowspan-1;
                colOffset += colspan-1;
            }
        }

        // 汇总行列
//        int row = tr.size();
//
//        int col = td.size();
    }


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
        }
        return rowList;
    }


}
