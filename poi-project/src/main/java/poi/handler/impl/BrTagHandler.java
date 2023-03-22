package poi.handler.impl;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/3/21 14:26
 */
public class BrTagHandler extends AbstractHtmlTagHandler {
    @Override
    public String getTagName() {
        return "br";
    }

    @Override
    public void handler(DocumentParam documentParam) {
        XWPFParagraph currentParagraph = documentParam.getCurrentParagraph();
        documentParam.getCurrentRun().getCTR().addNewBr();
    }
}
