package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

/**
 *  图片配置类
 * @author 01415355
 */
@Data
public class ImageConfig {

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

    private String imagePath;

    /**
     * 是否开启全局生效
     * 开启这个参数,会将图片加入到pdf所有页
     */
    private Integer fullPageEffect;

}
