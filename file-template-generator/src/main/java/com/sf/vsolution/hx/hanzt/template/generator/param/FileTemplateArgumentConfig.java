package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.util.List;


/**
 * @Description: 模板参数配置
 * @Author 01415355
 * @Date 2023/5/8 15:40
 */
@Data
public class FileTemplateArgumentConfig {

    /**
     *  定义模板字段
     */
    private List<BizTemplateField> bizTemplateField;

}
