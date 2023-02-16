package com.easy.excel.customize;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/2/8 14:58
 */
@Slf4j
public class CustomStringStringConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        return "自定义：" + cellData.getStringValue();
    }

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        log.info("value:{}",value);
        return new CellData(value);
    }
}
