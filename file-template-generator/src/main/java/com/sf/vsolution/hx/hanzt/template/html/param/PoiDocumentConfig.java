package com.sf.vsolution.hx.hanzt.template.html.param;

import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 01415355
 * @description: poi 参数配置
 * @date 2023年09月11日
 * @version: 1.0
 */
@Data
public class PoiDocumentConfig {

    /**
     *  页边距配置
     */
    private PageMarginsConfig pageMarginsConfig;

    /**
     *  行间距
     */
    private LineSpacingConfig lineSpacingConfig;

    /**
     *  页眉页脚配置
     */
    private FooterAndHeaderConfig footerAndHeader;

    /**
     *  是否开启重复标题头配置
     */
    private boolean isRepeatHeader = Boolean.FALSE;



    @Data
    public static final class FooterAndHeaderConfig {
        /**
         *  设置页脚,单位cm
         */
        private Double footer;
        /**
         *  设置页眉,单位cm
         */
        private Double header;

        /**
         *  是否包含页码
         */
        private Boolean hasPageNum;

    }

    @Data
    public static final class LineSpacingConfig {
        /**
         *  行间距,倍数,默认1.5倍行间距
         */
        private Double lineSpacing;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class PageMarginsConfig{
        /**
         *  左边间距,单位cm
         */
        private Double left;

        /**
         *  上边距,单位cm
         */
        private Double top;

        /**
         * 右边距,单位cm
         */
        private Double right;

        /**
         *  下边距,单位cm
         */
        private Double bottom;

        /**
         *  页宽,单位cm
         *  默认A4大小
         */
        private Double pageWidth = PoiCommon.A4_WIDTH;

        /**
         *  页高,单位cm
         *  默认A4大小
         */
        private Double pageHeight = PoiCommon.A4_HEIGHT;
    }




}
