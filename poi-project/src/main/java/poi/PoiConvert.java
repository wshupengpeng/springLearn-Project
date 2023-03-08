package poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/2/22 16:53
 */
@Slf4j
public class PoiConvert {

    /**
     *  通过poifs无法转换base64image图片
     * @throws IOException
     */
    @Test
    public void htmlConvertWordByPoi() throws IOException {
        String srcFile = "D:\\hpp\\1.html";
        String destFile = "d:\\hpp\\1.doc";
        FileInputStream fileInputStream = new FileInputStream(srcFile);
        String str = " <!--[if gte mso 9]><xml><w:WordDocument><w:View>Print</w:View><w:TrackMoves>false</w:TrackMoves><w:TrackFormatting/><w:ValidateAgainstSchemas/><w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid><w:IgnoreMixedContent>false</w:IgnoreMixedContent><w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText><w:DoNotPromoteQF/><w:LidThemeOther>EN-US</w:LidThemeOther><w:LidThemeAsian>ZH-CN</w:LidThemeAsian><w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript><w:Compatibility><w:BreakWrappedTables/><w:SnapToGridInCell/><w:WrapTextWithPunct/><w:UseAsianBreakRules/><w:DontGrowAutofit/><w:SplitPgBreakAndParaMark/><w:DontVertAlignCellWithSp/><w:DontBreakConstrainedForcedTables/><w:DontVertAlignInTxbx/><w:Word11KerningPairs/><w:CachedColBalance/><w:UseFELayout/></w:Compatibility><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><m:mathPr><m:mathFont m:val='Cambria Math'/><m:brkBin m:val='before'/><m:brkBinSub m:val='--'/><m:smallFrac m:val='off'/><m:dispDef/><m:lMargin m:val='0'/> <m:rMargin m:val='0'/><m:defJc m:val='centerGroup'/><m:wrapIndent m:val='1440'/><m:intLim m:val='subSup'/><m:naryLim m:val='undOvr'/></m:mathPr></w:WordDocument></xml><![endif]-->";
        byte [] data = new byte[fileInputStream.available()];
        fileInputStream.read(data);
        String content = new String(data,"utf-8");
        String h = " <html xmlns:v='urn:schemas-microsoft-com:vml'xmlns:o='urn:schemas-microsoft-com:office:office'xmlns:w='urn:schemas-microsoft-com:office:word'xmlns:m='http://schemas.microsoft.com/office/2004/12/omml'xmlns='http://www.w3.org/TR/REC-html40'  ";
        content = h + "<head>"+"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+str+"</head><body>"+content+"</body> </html>";
        POIFSFileSystem poifs = new POIFSFileSystem();
        DirectoryEntry directory = poifs.getRoot();
        DocumentEntry documentEntry = directory.createDocument("WordDocument", new ByteArrayInputStream(content.getBytes()));
        FileOutputStream ostream = new FileOutputStream(destFile);
        poifs.writeFilesystem(ostream);
        fileInputStream.close();
        ostream.close();
    }

    @Test
    public void htmlConvertWordByJsonp()throws IOException{
        String srcFile = "D:\\hpp\\2.html";
        String destFile = "d:\\hpp\\2.doc";
        File file = new File(srcFile);
        Document parse = Jsoup.parse(file, "utf-8");
//        parseTableElement(parse.body());
        parseTextElement(parse.body());
    }

    /**
     *  解析文本数据
     * @param element
     */
    private void parseTextElement(Element element){
        List<Node> nodes = element.childNodes();
        ListIterator<Node> nodeListIterator = nodes.listIterator();
        while(nodeListIterator.hasNext()){
            Node next = nodeListIterator.next();
            if(next instanceof Element){
                parseTextElement((Element) next);
            }
        }
        Tag tag = element.tag();
        if(tag.getName().equals("body") || "br".equals(element.tagName())) return;
        String text = element.text();
        log.info("tagName:{},text:{}",tag.getName(), text);
    }

    /**
     *  解析表格数据
     * @param element
     */
    private void parseTableElement(Element element){
        List<Node> nodes = element.childNodes();
        ListIterator<Node> nodeListIterator = nodes.listIterator();
        while(nodeListIterator.hasNext()){
            Node next = nodeListIterator.next();
            if(next instanceof Element){
                parseTableElement((Element) next);
            }
        }
        //1 解析当前元素
        if(element instanceof Node){
            if ("tr".equals(element.tagName())
                    || "tbody".equals(element.tagName())
                    || "table".equals(element.tagName())
                    || "br".equals(element.tagName())
                    || "body".equals(element.tagName())) {
//                log.info("换行符");
                return;
            }
            if(element.hasText()){
                log.info("当前标签：{}，文本内容：{}",element.tagName(),element.text());
            }
            return;
        }
        element.children().forEach(this::parseTableElement);
        //2 解析当前元素的兄弟元素
        Element nextElementSibling = element.nextElementSibling();
        if(Objects.isNull(nextElementSibling)){
            return;
        }
        parseTableElement(nextElementSibling);
    }

    @Test
    public void createTable() throws IOException {
        XWPFDocument xwpfDocument = new XWPFDocument();
        List<String[]> tableList = createTableList();
        XWPFTable table = xwpfDocument.createTable(tableList.size(), tableList.get(0).length);
//        XWPFTable table = xwpfDocument.createTable();
        CTTbl ctTbl = table.getCTTbl();
        CTTblPr tblPr = ctTbl.getTblPr();
        CTTblLayoutType ctTblLayoutType = tblPr.addNewTblLayout();
        ctTblLayoutType.setType(STTblLayoutType.AUTOFIT);
        CTRow ctRow = ctTbl.addNewTr();
//        XWPFTableRow xwpfTableRow = new XWPFTableRow(ctRow,table);
//        table.addRow(xwpfTableRow);
        // 填充数据
        int length = tableList.size();
        for (int i = 0; i < 10; i++) {
            XWPFTableRow row = table.getRow(i);
            List<XWPFTableCell> cells = row.getTableCells();
            for (int j = 0; j < cells.size(); j++) {
//                cells.get(j).addParagraph().createRun().setText(tableList.get(i)[j]);
                cells.get(j).setText(tableList.get(i)[j]);
            }
        }
        xwpfDocument.write(new FileOutputStream("d:/demo.docx"));
    }

    private List<String[]> createTableList(){
        List<String[]> tables = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            String[] row = new String[10];
            for(int j = 0; j < 10; j++){
                row[j] = String.format("第%d行,第%d列", i, j);
            }
            tables.add(row);
        }
        return tables;
    }

}
