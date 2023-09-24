package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

/**
 * @Description: 基础模板图片参数
 * @Author 01415355
 * @Date 2023/7/16 14:34
 */
@Data
public class BaseTemplateImage {

    /**
     * 记录图片横坐标
     */
    private String absoluteX;

    /**
     * 记录图片纵坐标
     */
    private String absoluteY;

    /**
     * 自适应图片缩放宽度
     */
    private String fitWidth;

    /**
     * 自适应图片缩放宽度
     */
    private String fitHeight;

    /**
     * 图片地址
     */
    private String imageUrl;
}
