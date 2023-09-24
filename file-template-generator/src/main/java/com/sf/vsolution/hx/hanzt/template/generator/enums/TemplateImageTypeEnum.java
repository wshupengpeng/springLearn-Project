package com.sf.vsolution.hx.hanzt.template.generator.enums;

public enum TemplateImageTypeEnum {
    QR_CODE("qrCode","二维码"),
    BAR_CODE("barCode","条形码");

    TemplateImageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
