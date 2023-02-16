package com.easy.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/2/8 14:49
 */
@Slf4j
public class BaseListener extends AnalysisEventListener<Map<Integer,String>> {


    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        log.info("解析开始,解析内容为：{}", integerStringMap);
        Map<String, ExcelContentProperty> fieldNameContentPropertyMap = analysisContext.readSheetHolder().getExcelReadHeadProperty().getFieldNameContentPropertyMap();
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Integer currentRowNum = context.getCurrentRowNum();
        if(currentRowNum == 0){
            log.info("当前文件行数为0");
        }
    }
}
