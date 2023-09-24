package com.sf.vsolution.hx.hanzt.template.generator.policy;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import java.util.List;

/**
 * @author 01415355
 * @description: 表格定制化插件
 * 为了实现客户的表格标题分页展示
 * @date 2023年09月14日
 * @version: 1.0
 */
@Slf4j
public class CustomizeTablePolicy extends MiniTableRenderPolicy {

    @Override
    protected void afterRender(RenderContext<MiniTableRenderData> context) {
        super.afterRender(context);
        
        XmlCursor cursor = context.getRun().getCTR().newCursor();
        cursor.beginElement("tbl", CTTbl.type.getName().getNamespaceURI());
        cursor.toParent();
        cursor.toParent();

        XmlObject o = cursor.getObject();
        XWPFTable xwpfTable = null;

        while (!(o instanceof CTTbl) ||
                (xwpfTable = context.getXWPFDocument().getTable((CTTbl) o)) == null) {
            cursor.toPrevSibling();
            o = cursor.getObject();
        }

        if(xwpfTable == null){
            List<XWPFTable> tables = ((XWPFParagraph) context.getRun().getParent()).getBody().getTables();
            xwpfTable = tables.get(tables.size() - 1);
        }

        List<XWPFTableRow> rows = xwpfTable.getRows();

        if(CollectionUtils.isNotEmpty(rows)){
            XWPFTableRow xwpfTableRow = rows.get(0);
            xwpfTableRow.setRepeatHeader(Boolean.TRUE);
        }
    }
}
