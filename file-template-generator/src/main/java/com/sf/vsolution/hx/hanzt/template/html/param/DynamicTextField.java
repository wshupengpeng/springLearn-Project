package com.sf.vsolution.hx.hanzt.template.html.param;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 动态文本参数属性
 * @Author 01415355
 * @Date 2023/7/13 17:06
 */
@Data
public class DynamicTextField {

    /**
     * 属性名称(动态字段代表excel表头)
     */
    private String fieldName;

    /**
     * 属性值（动态字段代表word占位符名称)
     */
    private String fieldValue;

    /**
     *  属性类型 0 - 动态字段, 1-固定字段
     */
    private Integer fieldType;

    /**
     * 模板id
     */
    private String templateId;

    private Integer fontSize;

    private String fontColor;

    private String fontFamily;

    /**
     *  是否映射字段 0-否,1-是
     */
    private Integer isReference;

    /**
     *  是否需要生成图片 0-不需要,1-需要
     *  如果当前数据需要生成图片,则需要查询 biz_template_image 表获取配置
     */
    private Integer isNeedGeneratorImage;


    /**
     *  引用表头名称
     */
    private String referenceName;

    /**
     *  数据类型,详细见FieldValueTypeEnum
     */
    private String fieldValueType;


    private List<DynamicReference> referenceList = new ArrayList<>();
}
