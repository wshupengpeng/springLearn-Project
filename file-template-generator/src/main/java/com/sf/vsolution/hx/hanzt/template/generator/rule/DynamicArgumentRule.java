package com.sf.vsolution.hx.hanzt.template.generator.rule;

import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateCol;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateConfiguration;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateRenderData;

/**
 * @Description: 动态参数规则定义接口
 * @Author 01415355
 * @Date 2023/5/08 16:37
 */
public interface DynamicArgumentRule {

    void rule(FileTemplateConfiguration configuration,FileTemplateCol fileTemplateCol, FileTemplateRenderData fileTemplateRenderData);

    boolean isMatchRule(FileTemplateCol fileTemplateCol);
}
