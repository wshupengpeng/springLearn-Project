package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 动态参数模板信息
 * @author 01415355
 * @since 2022-06-23
 */
@Data
public class DynamicTemplateImage extends BaseTemplateImage implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *  图片类型
     *  qrCode: 二维码
     *  barCode: 条形码
     */
    private String imageType;

    /**
     *  图片占位符名称
     */
    private String imagePlaceHolder;

}
