package poi;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.util.TableTools;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblLayoutType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblLayoutType;
import poi.handler.common.PoiCommon;
import poi.handler.impl.TableTagHandler;
import poi.handler.utils.HtmlToWordUtils;
import poi.handler.utils.JsoupUtils;
import sun.security.krb5.internal.PAData;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @Description: poi基本操作学习
 * @Author 01415355
 * @Date 2023/3/6 9:20
 */
@Slf4j
public class PoiBaseOperator {

    public static final XWPFDocument doc = new XWPFDocument();

    public static final String descPath = "d://hpp//test.doc";

    public static final String srcPath = "d://hpp//2.html";

    public static final String srcPath_home = "d://测试数据/poi.html";

    public static final String descPath_home = "d://测试数据/poi.doc";


    @Before
    public void before(){
//        File file = new File(descPath);
        File file = new File(descPath_home);
        file.deleteOnExit();
    }


    @After
    public void after() throws IOException {
//        doc.write(new FileOutputStream(descPath));
        doc.write(new FileOutputStream(descPath_home));
    }

    @Test
    public void writerDoc(){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.createRun().setText("test");
        XWPFTable table = doc.createTable();
//        doc.insertTable(1,table);
    }

    @Test
    public void operatorXWPFParagraph() {
        // 创建段落
        XWPFParagraph xwpfParagraph = doc.createParagraph();
        // 设置段落属性
        // 对齐方式
        xwpfParagraph.setAlignment(ParagraphAlignment.CENTER);
        // 边框
//        xwpfParagraph.setBorderBottom(Borders.DOUBLE);
//        xwpfParagraph.setBorderTop(Borders.DOUBLE);
//        xwpfParagraph.setBorderRight(Borders.DOUBLE);
//        xwpfParagraph.setBorderLeft(Borders.DOUBLE);
//        xwpfParagraph.setBorderBetween(Borders.SINGLE);


        // 基本操作元素
        XWPFRun xwpfRun = xwpfParagraph.createRun();
        // 在当前段落中插入文本
//        xwpfRun.setBold(true);
//        xwpfRun.setColor("67C359");
//        xwpfRun.setItalic(true);
//        xwpfRun.setShadow(true);
//        xwpfRun.setFontSize(20);
        xwpfRun.setText("你好");
        // 获取文字
//        String text = xwpfParagraph.getText();

        // 操作表格
        // 获取当前段落的run基本操作对象
        XWPFTable table = doc.createTable(5, 5);
//        doc.insertTable(1,table);
        // 设置表格宽度
//        XWPFParagraph xwpfParagraph1 = table.getRow(0).getCell(0).addParagraph();
//        xwpfParagraph1.setAlignment(ParagraphAlignment.CENTER);
//        XWPFRun run1 = xwpfParagraph1.createRun();
//        TextRenderPolicy.Helper.renderTextRun(run1, new TextRenderData(text, new Style()));
//        CTTblLayoutType type = table.getCTTbl().getTblPr().addNewTblLayout();
//        type.setType(STTblLayoutType.AUTOFIT);
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
        TableTools.mergeCellsHorizonal(table,1,2,3);
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

    @Test
    public void operatorTableAttr() throws IOException {
        Document parse = Jsoup.parse(new File(srcPath));
        Element body = parse.body();
        // body 不含有兄弟节点
        // 遍历子节点
        Elements table = body.getElementsByTag("table");

        String html = table.html();
//        log.info(html);
        log.info("element size:{}",table.size());
        // table只有一个标签
        for (Element element : table) {
            Elements tr = element.getElementsByTag("tr");
            Elements th = element.getElementsByTag("th");
            Elements td = element.getElementsByTag("td");
//            Elements tr = element.select("tr");
            tr.forEach(this::printElement);
            th.forEach(this::printElement);
            td.forEach(this::printElement);
//            Attributes attributes = element.attributes();
//            for (Attribute attribute : attributes) {
//                log.info("attribute:{}",attribute);
//            }
//            Elements tr = element.getElementsByAttribute("tr");
//            Elements th = element.getElementsByAttribute("th");
//            Elements td = element.getElementsByAttribute("td");
//            tr.forEach(this::printElement);
//            th.forEach(this::printElement);
//            td.forEach(this::printElement);
        }

    }


    public void printElement(Element element){
        List<Node> childNodes = element.childNodes();
        childNodes.forEach(node->{
            if(node instanceof Element){
                Element childElement = (Element) node;
                log.info("childTagName:{},childText:{}", childElement.tagName(), childElement.text());
            }
        });
        log.info("tagName:{},text:{}", element.tagName(), element.text());
    }


    @Test
    public void printPTag() throws IOException {
        Document parse = Jsoup.parse(new File(srcPath));
        Element body = parse.body();

        Elements p1 = body.select("p");
        Elements p = body.getElementsByTag("p");

        for (Element element : p) {
            List<Node> childNodes = element.childNodes();
            childNodes.forEach(this::printNode);
        }
    }

    public void printNode(Node node){
        if(node instanceof  Element){
            Element child = (Element)node;
            log.info("childNode instanceOf Element,TagName:{},Text:{}", child.tagName(), child.text());
        }
        if(node instanceof TextNode){
            log.info("childNode instanceOf TextNode,TagName:{},Text:{}", node.nodeName(), ((TextNode) node).text());
        }
    }


    @Test
    public void parseStyle() throws IOException{
        Document parse = Jsoup.parse(new File(srcPath_home));

        Element body = parse.body();

        Elements allPTag = body.getElementsByTag("p");

        // 创建段落
        XWPFParagraph xwpfParagraph = doc.createParagraph();
        assert xwpfParagraph != null;

//        for (Element element : allPTag) {
//            element.childNodes().forEach(this::printNode);
            Elements span = body.getElementsByTag("span");

            String styleAttr = span.attr("style");

            Elements strong = body.getElementsByTag("strong");

            Style style = JsoupUtils.parseStyle(styleAttr);
            XWPFRun run = xwpfParagraph.createRun();

            TextRenderPolicy.Helper.renderTextRun(run,
                new TextRenderData(strong.text(),
                        Optional.ofNullable(style).orElse(PoiCommon.DEFAULT_STYLE)));
            assert run != null;
//            run.setBold(style.isBold());
            run.setColor(style.getColor().replaceAll("#",""));
            run.setText(strong.text());
//            run.setUnderline(style.isUnderLine());
//        }


    }

    @Test
    public void parseHtml() throws Exception {
        Document parse = Jsoup.parse(new File(srcPath));
        XWPFDocument xwpfDocument = HtmlToWordUtils.parseHtmlToWord(parse.outerHtml());
        xwpfDocument.write(new FileOutputStream("d://hpp//test1.doc"));
    }


    @Test
    public void createTable() throws IOException {
        Document parse = Jsoup.parse(new File(srcPath));
        Element body = parse.body();
        Elements table = body.getElementsByTag("table");
//        Element element = table.get(0);
//        Elements th = element.getElementsByTag("th");
//        Elements tr = element.getElementsByTag("tr");
//        XWPFTable xwpfTable = doc.createTable(tr.size(), th.size());
//
//        Elements tbody = element.getElementsByTag("tr");
        Element tbody = table.first().getElementsByTag("tbody").first();
        Elements tr = tbody.getElementsByTag("tr");
        Iterator<Element> iterator = tr.iterator();
//        for (Node childNode : tbody.get(0).childNodes()) {
//           Element childEle =  (Element) childNode;
//
//            int rowspan = Integer.parseInt(childEle.attr("rowspan"));
//            int colspan = Integer.parseInt(childEle.attr("colspan"));
//            // 方案一,获取所有colspan和rowspan之和后的实际行数和列数,而是按照行读取
//            // 直接在table中通过mergeCol和mergeRow进行合并
//            // 手动在table中添加
//
//
//            // 方案二,初始化之前，获取所有colspan和rowspan之和后的实际行数和列数,
//            // 通过获取实际列数和行数，通过mergeFlag进行合并操作
//        }
    }

}
