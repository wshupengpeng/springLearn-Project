package poi.v2.handler.param;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.jsoup.nodes.Node;
import poi.handler.param.TextFormatStyle;

/**
 * @Description: 富文本解析参数
 * @Author 01415355
 * @Date 2023/4/18 10:46
 */
@Data
public class RichText {
    /**
     * 当前的html元素
     */
    private Node currentNode;
    /**
     * 当前定位到的段落
     */
    private XWPFParagraph currentParagraph;
    /**
     * 当前定位的块
     */
    private XWPFRun currentRun;
    /**
     * 文档对象
     */
    private XWPFDocument doc;

    /**
     * 是否跳过迭代子节点
     */
    private Boolean continueItr = false;
    /**
     * 其他可能需要的业务参数
     */
    private Object extendParams;

    /**
     * 字体类型
     */
//    private Style style;

    private TextFormatStyle textFormatStyle;

    /**
     * 段落第一个标签不用换行
     */
    private boolean enableBreak = true;


    public XWPFRun createRun() {
        this.currentRun = this.getCurrentParagraph().createRun();
        this.enableBreak = true;
        return this.currentRun;
    }

    public XWPFParagraph insertNewParagraph(IBodyElement bodyElement) {
        if (bodyElement == null) {
            bodyElement = this.currentParagraph;
        }
        int pos = bodyElement.getBody().getBodyElements().indexOf(bodyElement);
        XmlCursor xmlCursor = null;
        if ((pos + 1) >= bodyElement.getBody().getBodyElements().size()) {
            this.currentParagraph = this.doc.createParagraph();
        } else {
            IBodyElement next = bodyElement.getBody().getBodyElements().get(pos + 1);

            if (next instanceof XWPFTableCell) {
                xmlCursor = ((XWPFTableCell) next).getCTTc().newCursor();
            } else if (next instanceof XWPFParagraph) {
                xmlCursor = ((XWPFParagraph) next).getCTP().newCursor();
            }
            this.currentParagraph = bodyElement.getBody().insertNewParagraph(xmlCursor);
        }

        this.createRun();
        this.enableBreak = false;
        return this.currentParagraph;
    }

    public XWPFParagraph insertNewParagraph() {
        return insertNewParagraph(null);
    }

    public void removeParagraph(XWPFParagraph paragraph) {
        if (paragraph == null) {
            paragraph = this.currentParagraph;
        }
        int posOfParagraph = doc.getPosOfParagraph(paragraph);
        doc.removeBodyElement(posOfParagraph);
    }

    public void removeParagraph() {
        removeParagraph(null);
    }


//    public void resetStyle() {
//        textFormatStyle = null;
//    }
//
//    public boolean hasStyle() {
//        return hasTextFormat() && Objects.nonNull(textFormatStyle.getStyle());
//    }
//
//    public boolean hasParagraphAlignment() {
//        return hasTextFormat() && Objects.nonNull(textFormatStyle.getParagraphAlignment());
//    }
//
//    public boolean hasTextFormat() {
//        return Objects.nonNull(textFormatStyle);
//    }

}
