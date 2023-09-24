package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 动态引用解析数据,代表一个sheet页下的数据
 * @Author 01415355
 * @Date 2023/1/17 19:57
 */
@Data
public class DynamicReferenceData {
    /**
     * 动态解析数据
     */
    private List<DynamicReferenceRow> dynamicReflectRow = new ArrayList<>();
    /**
     *  动态解析头
     */
    private Map<String,Integer> headReferenceMap;

    /**
     *  sheet页
     */
    private Integer sheetNo;

    /**
     *  sheet页名称
     */
    private String sheetName;

}
