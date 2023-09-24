package com.sf.vsolution.hx.hanzt.template.generator.enums;

/**
 * @author 01415355
 * @description: 系统支持的字体
 * @date 2023年09月13日
 * @version: 1.0
 */
public enum FontsEnum {
    SIMSUN("宋体"),
    SIMHEI("黑体"),
    TIMES_NEW_ROMAN("Times New Roman"),
    ;

    FontsEnum(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    private String fontFamily;


    public String getFontFamily() {
        return fontFamily;
    }
}
