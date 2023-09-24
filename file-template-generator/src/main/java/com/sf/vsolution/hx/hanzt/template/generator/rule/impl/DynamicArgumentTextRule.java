package com.sf.vsolution.hx.hanzt.template.generator.rule.impl;

import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.sf.vsolution.hx.hanzt.template.generator.param.BizTemplateField;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateCol;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateConfiguration;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateRenderData;
import com.sf.vsolution.hx.hanzt.template.generator.rule.DynamicArgumentRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @Description: 动态参数文本规则
 * @Author 01415355
 * @Date 2023/5/8 16:44
 */
@Slf4j
public class DynamicArgumentTextRule implements DynamicArgumentRule {

    @Override
    public void rule(FileTemplateConfiguration configuration, FileTemplateCol fileTemplateCol, FileTemplateRenderData fileTemplateRenderData) {
        fileTemplateRenderData.getRenderDataMap().put(fileTemplateCol.getFieldValue(), new TextRenderData(fileTemplateCol.getExcelValue(), getStyle(fileTemplateCol)));
    }

    @Override
    public boolean isMatchRule(FileTemplateCol fileTemplateCol) {
        return Objects.nonNull(fileTemplateCol);
    }

    private Style getStyle(BizTemplateField bizTemplateField) {
        Style style = new Style();
        style.setColor(bizTemplateField.getFontColor());
        style.setFontSize(bizTemplateField.getFontSize());
        style.setFontFamily(bizTemplateField.getFontFamily());
        return style;
    }
}
