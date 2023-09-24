package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 01415355
 * @since 2022-06-23
 */
@Data
public class BizTemplateField implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性名称(动态字段代表excel表头，固定字段标识word模板占位符名称)
     */
    private String fieldName;

    /**
     * 属性值（动态字段代表word占位符名称，固定字段标识word填充值)
     */
    private String fieldValue;
    /**
     *  属性类型 0 - 动态字段, 1-固定字段
     *  动态字段读取用户数据
     *  固定字段直接获取默认值
     *  类型不同导致fieldName 和fieldValue含义不同，后续可以加冗余字段加以区分
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
     *  是否映射字段 0-否,1-是,如果是映射字段则需要查询biz_template_reflect表
     *  获取映射配置
     */
    private Integer isReference;

    /**
     * 当前字段是否需要拼接文件名称 0-不需要,1-需要
     */
    private Integer isNeedAppendFileName;


    /**
     * 如果需要拼接文件名称，则需要此字段定义顺序
     */
    private Integer fileNameSort;

    /**
     *  是否需要生成图片 0-不需要,1-需要
     *  如果当前数据需要生成图片,则需要查询 biz_template_image 表获取配置
     */
    private Integer isNeedGeneratorImage;

    /**
     *  引用excel表头
     */
    private String referenceName;

    /**
     *  数据类型,详细见FieldValueTypeEnum
     */
    private String fieldValueType;

    private List<BizTemplateReference> referenceList = new ArrayList<>();

    private List<DynamicTemplateImage> imageList = new ArrayList<>();

}
