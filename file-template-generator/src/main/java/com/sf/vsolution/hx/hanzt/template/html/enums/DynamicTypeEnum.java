package com.sf.vsolution.hx.hanzt.template.html.enums;

/**
 * @Author 01415355
 * @Description: 动态参数类型枚举
 * @Date 2023/5/5 14:06
 */
public enum DynamicTypeEnum {
    TEXT("text"),
    QRCODE("qrcode"),
    BARCODE("barcode"),
    PBREAK("pbreak"),
    TABLE("table");

    DynamicTypeEnum(String type) {
        this.type = type;
    }

    private String type;


    public static DynamicTypeEnum getDynamicTypeEnumByType(String type){
        for (DynamicTypeEnum typeEnum : values()) {
            if(typeEnum.type.equalsIgnoreCase(type)){
                return typeEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }
}
