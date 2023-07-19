package com.mongo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @creater hpp
 * @Date 2023/7/19-19:49
 * @description:
 */
@Data
//@Document(collation = "test")
@Document
@CompoundIndexes({@CompoundIndex(name = "idx_no", unique = true, def = "{'no':1}")})
public class DynamicData {
    /**
     * 固定字段1
     */
    @TextIndexed
    private String no;

    /**
     * 固定字段2
     */
    @TextIndexed
    private String bankName;

    /**
     * 固定字段3
     */
    @TextIndexed
    private String companyName;

    /**
     * 动态表格字段
     */
    private List<DynamicParam> referenceList;


    /**
     * 动态文本字段
     */
    private List<DynamicParam> dynamicTextList;

    @Data
    public static final class DynamicParam {

        private String titleName;

        private String titleValue;

        private Integer titleIndex;

    }
}
