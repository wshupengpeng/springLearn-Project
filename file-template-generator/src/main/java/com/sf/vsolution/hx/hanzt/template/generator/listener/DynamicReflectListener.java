package com.sf.vsolution.hx.hanzt.template.generator.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.sf.vsolution.hx.hanzt.template.generator.param.DynamicReferenceCol;
import com.sf.vsolution.hx.hanzt.template.generator.param.DynamicReferenceData;
import com.sf.vsolution.hx.hanzt.template.generator.param.DynamicReferenceRow;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 基础表格sheet监听
 * @Author 01415355
 * @Date 2023/5/8 11:19
 */
@Slf4j
public class DynamicReflectListener extends AnalysisEventListener<Map<Integer, String>> {

    private DynamicReferenceData dynamicReferenceData = new DynamicReferenceData();

    private Map<String, Integer> headReferenceMap;

    public DynamicReflectListener() {

    }

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        // 动态参数引用行数据
        DynamicReferenceRow dynamicReflectRow = new DynamicReferenceRow();
        // 动态参数引用列数据
        List<DynamicReferenceCol> dynamicReflectCols = new ArrayList<>();
        integerStringMap.forEach((colIndex, value) -> {
            DynamicReferenceCol dynamicReflectCol = new DynamicReferenceCol();
            dynamicReflectCol.setColIndex(colIndex);
            dynamicReflectCol.setExcelValue(value);
            dynamicReflectCols.add(dynamicReflectCol);
        });
        // 设置列
        dynamicReflectRow.setDynamicReflectCols(dynamicReflectCols);
        // 设置行数据
        dynamicReferenceData.getDynamicReflectRow().add(dynamicReflectRow);
        // 设置sheet页名称
        dynamicReferenceData.setSheetName(analysisContext.readSheetHolder().getSheetName());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("parse file template end , totalSize:{}", dynamicReferenceData.getDynamicReflectRow().size());
        dynamicReferenceData.setHeadReferenceMap(headReferenceMap);
    }


    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headReferenceMap = headMap
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));
    }

    public DynamicReferenceData getDynamicReflectData() {
        return dynamicReferenceData;
    }

}
