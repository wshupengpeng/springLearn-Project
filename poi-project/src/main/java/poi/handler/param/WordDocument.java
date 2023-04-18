package poi.handler.param;

import lombok.Data;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/11 18:11
 */
@Data
public class WordDocument {
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
}
