package poi.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

        for (int i = 0; i < rowElementList.size(); i++) {
            List<Element> row = rowElementList.get(i);
            for (int j = 0; j < row.size(); j++) {
                Element col = row.get(j);
                if(col.hasAttr("rowspan")){
                    int rowspan = Integer.parseInt(col.attr("rowspan"));

                }
            }
        }

        // 汇总行列
        int row = tr.size();

        int col = td.size();







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
//                    Element rowspan = element.attr("rowspan", "1");
//                    Element colspan = element.attr("colspan", "1");
                });
            }
        }
        return rowList;
    }


}
