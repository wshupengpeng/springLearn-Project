package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 模板解析excel结果数据
 * @Author 01415355
 * @Date 2023/1/17 20:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileTemplateParseExcelResult extends FileTemplateGeneratorResult {
    /**
     * excel导入模板解析结果
     */
    private List<FileTemplateRow> templateRowList;

    /**
     *  固定图片配置
     */
    private List<FixedTemplateImage> fixedImageList;


    private Integer totalNum;
}
