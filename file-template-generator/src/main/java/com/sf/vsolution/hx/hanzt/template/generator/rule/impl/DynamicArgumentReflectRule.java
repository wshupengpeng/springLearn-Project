package com.sf.vsolution.hx.hanzt.template.generator.rule.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.generator.param.*;
import com.sf.vsolution.hx.hanzt.template.generator.rule.DynamicArgumentRule;
import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: 动态参数引用规则
 * @Author 01415355
 * @Date 2023/5/8 16:45
 */
@Slf4j
public class DynamicArgumentReflectRule implements DynamicArgumentRule {
    @Override
    public void rule(FileTemplateConfiguration configuration, FileTemplateCol fileTemplateCol, FileTemplateRenderData fileTemplateRenderData) {
        // 1 读取配置
        List<BizTemplateReference> bizTemplateReferences = fileTemplateCol.getReferenceList();
        // 2 获取映射关系
        bizTemplateReferences.stream().forEach(bizTemplateReference -> {
            // 获取与当前动态属性sheet页名称或下标相等的动态数据,如果没有则代表动态数据为空
            DynamicReferenceData dynamicReflectData = getDynamicReflectData(bizTemplateReference, fileTemplateCol);
            if (Objects.isNull(dynamicReflectData)) {
                return;
            }
            // 组装表格数据
            Map<String, Integer> headReflectMap = dynamicReflectData.getHeadReferenceMap();
            TextRenderData[] headData = headReflectMap.keySet().stream()
                    .filter(titleName -> !Objects.equals(titleName, fileTemplateCol.getFieldName()))
                    .sorted(Comparator.comparingInt(headReflectMap::get))
                    .map(titleName -> new TextRenderData(titleName, PoiCommon.TABLE_STYLE)).toArray(TextRenderData[]::new);
            RowRenderData header = RowRenderData.build(headData);
            // 组装表格数据
            // 获取映射字段下标
            Integer reflectIndex = headReflectMap.get(fileTemplateCol.getFieldName());
            // 通过映射字段分组
            List<DynamicReferenceRow> dynamicReflectDataList = new ArrayList<>();
            for (DynamicReferenceRow dynamicReflectRow : dynamicReflectData.getDynamicReflectRow()) {
                List<DynamicReferenceCol> dynamicReflectCols = dynamicReflectRow.getDynamicReflectCols();
                for (DynamicReferenceCol dynamicReflectCol : dynamicReflectCols) {
                    // 如果下标是对应的映射字段,并且映射字段值相同,则将这行数据加入到动态数据中
                    if (Objects.equals(dynamicReflectCol.getColIndex(), reflectIndex)
                            && Objects.equals(dynamicReflectCol.getExcelValue(), fileTemplateCol.getExcelValue())) {
                        dynamicReflectDataList.add(dynamicReflectRow);
                        break;
                    }
                }
            }
            log.info("headData 组装完成 referenceName:{},sheetName:{}", fileTemplateCol.getReferenceName(), dynamicReflectData.getSheetName());
            List<RowRenderData> table = getRowRenderData(bizTemplateReference, reflectIndex, dynamicReflectDataList, headData.length);
            log.info("tableData 组装完成,wordPlaceholder:{},sheetName:{}", fileTemplateCol.getFieldName(), dynamicReflectData.getSheetName());
            // 组装成表格
            fileTemplateRenderData.getRenderDataMap().put(bizTemplateReference.getReferencePlaceHolder(), new MiniTableRenderData(header, table));
        });
    }


    private DynamicReferenceData getDynamicReflectData(BizTemplateReference bizTemplateReference, FileTemplateCol fileTemplateCol) {
        String referenceName = fileTemplateCol.getReferenceName();
        Integer sheetNo = bizTemplateReference.getSheetNo();
        Optional<DynamicReferenceData> first = fileTemplateCol.getDynamicReferenceDataList().stream()
                .filter(dynamicReferenceData ->
                        (Objects.nonNull(dynamicReferenceData.getSheetNo()) && Objects.equals(dynamicReferenceData.getSheetNo(), sheetNo))
                                || (Objects.nonNull(dynamicReferenceData.getSheetName()) && Objects.equals(dynamicReferenceData.getSheetName(), bizTemplateReference.getSheetName()))
                ).findFirst();
        if (!first.isPresent()) {
            log.error("当前映射关系不存在,当前列:{},映射titleName:{},sheetNo:{}", fileTemplateCol.getFieldName(), referenceName, sheetNo);
            return null;
        }
        DynamicReferenceData dynamicReferenceData = first.get();
        // 3 判断是否为表格配置
        if (!Objects.equals(FileConstant.INT_0, bizTemplateReference.getReferenceType())) {
            log.error("目前仅支持动态表格映射关系,当前列:{},映射titleName:{},sheetNo:{},reflectType:{}",
                    fileTemplateCol.getFieldName(), referenceName, sheetNo, bizTemplateReference.getReferenceType());
            return null;
        }
        return dynamicReferenceData;
    }


    private List<RowRenderData> getRowRenderData(BizTemplateReference bizTemplateReflect, Integer reflectIndex,
                                                 List<DynamicReferenceRow> dynamicReflectDataList, Integer colSize) {
        List<RowRenderData> table = new ArrayList<>();
        if (Objects.nonNull(bizTemplateReflect.getTableCellSize())
                && bizTemplateReflect.getTableCellSize() > 0
                && CollectionUtils.isEmpty(dynamicReflectDataList)) {
            // v3.23.0 如果当前动态数据不存在,判断是否有tableCellSize字段,如果大于0则按照此配置进行填充
            for (Integer i = 0; i < bizTemplateReflect.getTableCellSize(); i++) {
                table.add(RowRenderData
                        .build(buildDefaultTextRenderData(bizTemplateReflect, colSize)));
            }
        } else {
            // 组装表格数据
            table = dynamicReflectDataList.stream().map(dynamicReflectRow -> {
                TextRenderData[] textRenderData = dynamicReflectRow.getDynamicReflectCols().stream()
                        .filter(dynamicReflectCol -> !Objects.equals(reflectIndex, dynamicReflectCol.getColIndex()))
                        .sorted(Comparator.comparingInt(DynamicReferenceCol::getColIndex))
                        .map(dynamicReflectCol -> new TextRenderData(dynamicReflectCol.getExcelValue(), PoiCommon.TABLE_STYLE))
                        .toArray(TextRenderData[]::new);
                return RowRenderData.build(textRenderData);
            }).collect(Collectors.toList());
        }
        return table;
    }

    private TextRenderData[] buildDefaultTextRenderData(BizTemplateReference bizTemplateReflect, Integer colSize) {
        return Stream.generate(Math::random)
                .limit(colSize)
                .map(textRenderData -> new TextRenderData(Optional
                        .ofNullable(bizTemplateReflect.getDefaultTableValue())
                        .orElse(FileConstant.EMPTY_STR),
                        PoiCommon.TABLE_STYLE)).toArray(TextRenderData[]::new);
    }

    @Override
    public boolean isMatchRule(FileTemplateCol fileTemplateCol) {
        return CollectionUtil.isNotEmpty(fileTemplateCol.getReferenceList()) && CollectionUtil.isNotEmpty(fileTemplateCol.getDynamicReferenceDataList());
    }
}
