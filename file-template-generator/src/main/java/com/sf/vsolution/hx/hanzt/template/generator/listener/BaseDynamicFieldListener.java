package com.sf.vsolution.hx.hanzt.template.generator.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.generator.enums.FieldValueTypeEnum;
import com.sf.vsolution.hx.hanzt.template.generator.param.BizTemplateField;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateArgumentConfig;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateCol;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateRow;
import com.sf.vsolution.hx.hanzt.template.generator.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: 基础动态参数属性监听器
 * @Author 01415355
 * @Date 2023/5/8 14:13
 */
@Slf4j
public class BaseDynamicFieldListener extends AnalysisEventListener<Map<Integer, String>> {

    /**
     * 动态参数映射map, k->表头名, v->动态参数配置信息
     */
    private Map<String, BizTemplateField> headFieldMap;
    /**
     * 解析head头和坐标之间的关系
     */
    private Map<Integer, BizTemplateField> indexFileTemplateFiledMap = new HashMap<>();
    /**
     * 解析每行数据值
     */
    private List<FileTemplateRow> recordList = new ArrayList<>();


    public BaseDynamicFieldListener(FileTemplateArgumentConfig fileTemplateArgumentConfig) {
        // 筛选动态字段解析excel表数据
        Map<String, BizTemplateField> headFieldMap = fileTemplateArgumentConfig.getBizTemplateField().stream()
                .collect(Collectors.toMap(BizTemplateField::getFieldName, Function.identity()));
        this.headFieldMap = headFieldMap;
    }

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        // 文件模板行数据解析
        FileTemplateRow row = new FileTemplateRow();
        // 文件模板一行数据对应的列数据列表
        List<FileTemplateCol> fileTemplateCols = new ArrayList<>();
        integerStringMap.forEach((key, value) -> {
            BizTemplateField bizTemplateField = indexFileTemplateFiledMap.get(key);
            if (bizTemplateField != null) {
                FileTemplateCol col = BeanUtil.copyBeanProperties(FileTemplateCol::new, bizTemplateField);
                col.setExcelValue(Objects.isNull(value) ? FileConstant.EMPTY_STR : convertValue(value.trim(), bizTemplateField));
                fileTemplateCols.add(col);
            }
        });
        row.setFileTemplateCols(fileTemplateCols);
        recordList.add(row);
    }

    private String convertValue(String value, BizTemplateField bizTemplateField) {
        switch (FieldValueTypeEnum.getFieldValueTypeByDesc(bizTemplateField.getFieldValueType())) {
            case DOUBLE:
                return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).toString();
            case STRING:
                return value;
            case INTEGER:
                return Integer.valueOf(value).toString();
        }
        return value;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        // 转换坐标和配置信息
        headMap.forEach((key, value) -> {
            if (headFieldMap.get(value) != null) {
                log.info("parse file head，index:{},headName:{}", key, value);
                indexFileTemplateFiledMap.put(key, headFieldMap.get(value));
            } else {
                log.info("parse file head not match, index:{} headName:{} ", key, value);
            }
        });
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("parse file template end , totalSize:{}", recordList.size());
    }

    public List<FileTemplateRow> getRecordList() {
        return recordList;
    }


}
