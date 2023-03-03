package poi.handler.param;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.jsoup.nodes.Node;

/**
 * 处理器参数
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2021/12/9 10:47
 */
@Data
@Accessors(chain = true)
public class DocumentParam {
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
     * 是否继续迭代子节点
     */
    private Boolean continueItr = true;
    /**
     * 其他可能需要的业务参数
     */
    private Object extendParams;

    /**
     * 段落第一个标签不用换行
     */
    private boolean enableBreak = true;


    public XWPFRun createRun(){
        this.currentRun = this.getCurrentParagraph().createRun();
        this.enableBreak = true;
        return this.currentRun;
    }

    public XWPFParagraph insertNewParagraph(IBodyElement bodyElement){
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

    public XWPFParagraph insertNewParagraph(){
        return insertNewParagraph(null);
    }

    public void removeParagraph(XWPFParagraph paragraph){
        if (paragraph == null) {
            paragraph = this.currentParagraph;
        }
        int posOfParagraph = doc.getPosOfParagraph(paragraph);
        doc.removeBodyElement(posOfParagraph);
    }

    public void removeParagraph(){
        removeParagraph(null);
    }
}
