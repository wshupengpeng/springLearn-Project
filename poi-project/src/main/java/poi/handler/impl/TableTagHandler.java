package poi.handler.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

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

        // 汇总行列
        int row = tr.size();

        int col = td.size();



    }


}
