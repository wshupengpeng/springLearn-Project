package com.sf.vsolution.hx.hanzt.template.html.param;

import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import lombok.Data;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.jsoup.nodes.Node;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/27 17:05
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
     * 解析的动态参数名称
     */
    private DynamicArgumentResult extendParams;

    private TextFormatStyle textFormatStyle;

    /**
     * 是否需要换行
     */
    private boolean needBreak = false;

    /**
     * 是否需要创建新的文本
     */
    private boolean isCreateRun = true;

    /**
     * 上一个参数是否是动态参数
     */
    private boolean preIsDynamicText = false;

    /**
     * 是否开启重复标题头,默认不开启
     */
    private boolean isRepeatHeader = Boolean.FALSE;

    /**
     * 行间距配置
     */
    private PoiDocumentConfig.LineSpacingConfig lineSpacingConfig;


    public XWPFRun createRun() {
        this.currentRun = this.getCurrentParagraph().createRun();
//        this.needBreak = true;
        return this.currentRun;
    }

    public XWPFParagraph insertNewParagraph(IBodyElement bodyElement) {
        if (bodyElement == null) {
            bodyElement = this.currentParagraph;
        }

        if (bodyElement.getBody() instanceof XWPFTableCell) {
            this.currentParagraph = ((XWPFTableCell) bodyElement.getBody()).addParagraph();
        } else {
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
        }

        // 设置行距
        addNewSpacing(this.currentParagraph);
        // 设置段落页边距
        addParagraphSpacingLine(this.currentParagraph);
        this.createRun();
//        this.needBreak = false;
        return this.currentParagraph;
    }

    private void addParagraphSpacingLine(XWPFParagraph currentParagraph) {
        if (textFormatStyle.hasParagraphAttribute()) {
            currentParagraph.setSpacingAfter((int) (textFormatStyle.getParagraphAttribute().getSpacingAfter() * PoiCommon.PER_POUND));
            currentParagraph.setSpacingBefore((int) (textFormatStyle.getParagraphAttribute().getSpacingBefore() * PoiCommon.PER_POUND));
            currentParagraph.setPageBreak(Optional.ofNullable(textFormatStyle.getParagraphAttribute()).map(TextFormatStyle.ParagraphAttribute::getIsPageBreak).orElse(Boolean.FALSE));
        }
    }

    private void addNewSpacing(XWPFParagraph paragraph) {
        CTPPr paragraphProperties = paragraph.getCTP().addNewPPr();
        // 设置段落的行间距属性
        CTSpacing spacing = paragraphProperties.addNewSpacing();
        // 设置段后间距为0
        spacing.setAfter(BigInteger.valueOf(0));
        if (Objects.nonNull(lineSpacingConfig) && Objects.nonNull(lineSpacingConfig.getLineSpacing())) {
            spacing.setLine(BigInteger.valueOf((int) (lineSpacingConfig.getLineSpacing() * PoiCommon.ONE_LINE)));
        } else {
            // 设置行间距为1.5倍行距
            spacing.setLine(BigInteger.valueOf(PoiCommon.LINE_SPACING_ONE_POINT_HALF));
        }
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

    public boolean isNeedBreak() {
        return needBreak;
    }
}
