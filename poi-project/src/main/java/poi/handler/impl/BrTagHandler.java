package poi.handler.impl;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

import java.util.Objects;

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
    public void doHandler(DocumentParam documentParam) {
        if(Objects.isNull(documentParam.getCurrentRun())){
//            XWPFParagraph currentParagraph = documentParam.getCurrentParagraph();
            documentParam.createRun();
        }
        documentParam.getCurrentRun().getCTR().addNewBr();
    }
}
