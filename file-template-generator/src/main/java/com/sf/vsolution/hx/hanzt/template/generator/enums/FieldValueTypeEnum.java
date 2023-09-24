package com.sf.vsolution.hx.hanzt.template.generator.enums;

/**
 * @Description: 枚举excel表格类型
 * @Author 01415355
 * @Date 2023/1/30 15:02
 */
public enum FieldValueTypeEnum {
    INTEGER("integer",0),
    DOUBLE("double",1),
    STRING("string",2);

    FieldValueTypeEnum(String desc, Integer type) {
        this.desc = desc;
        this.type = type;
    }

    private String desc;
    private Integer type;


    public static FieldValueTypeEnum getFieldValueTypeByDesc(String desc){
        for (FieldValueTypeEnum valueTypeEnum : values()) {
            if(valueTypeEnum.desc.equals(desc)){
                return valueTypeEnum;
            }
        }
        return STRING;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getType() {
        return type;
    }
}
