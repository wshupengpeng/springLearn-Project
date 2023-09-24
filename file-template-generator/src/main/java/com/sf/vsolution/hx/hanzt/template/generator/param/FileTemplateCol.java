package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.util.List;

/**
 * @Description: 文件模板读取excel一行数据
 * @Author 01415355
 * @Date 2023/1/17 17:10
 */
@Data
public class FileTemplateCol extends BizTemplateField{

    /**
     *  excel导入数据value
     */
    private String excelValue;

    private List<DynamicReferenceData> dynamicReferenceDataList;
}
