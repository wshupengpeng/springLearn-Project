package com.sf.vsolution.hx.hanzt.template.html.param;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.deepoove.poi.data.style.Style;
import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

import java.util.Objects;

/**
 * @Description: 文本格式风格，用于定义文本类型
 * @Author 01415355
 * @Date 2023/4/10 11:37
 */
@Data
public class TextFormatStyle {
    /**
     *  定义字体颜色大小等参数
     */
    private Style style = PoiCommon.DEFAULT_STYLE;

    /**
     *  定义段落对齐方式
     */
    private ParagraphAlignment paragraphAlignment;

    /**
     *  设置段落属性
     */
    private ParagraphAttribute paragraphAttribute;


    /**
     *  设置背景色
     */
    private String backgroundColor;

    @Data
    public static final class ParagraphAttribute{

        private Double spacingBefore = 0.0d;

        private Double spacingAfter = 0.0d;

        private Boolean isPageBreak;
    }



    public TextFormatStyle() {
    }

    public TextFormatStyle(Style style, ParagraphAlignment paragraphAlignment) {
        this.style = style;
        this.paragraphAlignment = paragraphAlignment;
    }

    public boolean hasStyle(){
        return Objects.nonNull(style);
    }

    public boolean hasParagraphAlignment(){
        return Objects.nonNull(paragraphAlignment);
    }

    public boolean hasBackgroundColor(){
        return StringUtils.isNotBlank(backgroundColor);
    }

    public boolean hasParagraphAttribute() {
        return Objects.nonNull(paragraphAttribute);
    }

    @Override
    public Object clone(){

        TextFormatStyle clone = new TextFormatStyle();

        BeanUtil.copyProperties(this, clone, CopyOptions.create());

        if(Objects.nonNull(this.style)){
            Style cloneStyle = new Style();
            BeanUtil.copyProperties(this.style, cloneStyle);
            clone.setStyle(cloneStyle);
        }

        if (hasParagraphAttribute()) {
            ParagraphAttribute cloneParagraphAttribute = new ParagraphAttribute();
            BeanUtil.copyProperties(getParagraphAttribute(), cloneParagraphAttribute);
            clone.setParagraphAttribute(cloneParagraphAttribute);
        }

        return clone;
    }
}

