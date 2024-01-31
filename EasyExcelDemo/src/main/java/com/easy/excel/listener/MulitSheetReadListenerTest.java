package com.easy.excel.listener;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

/**
 * @author 01415355
 * @ClassName 多个sheet页读取测试
 * @description: TODO
 * @date 2023年09月27日
 * @version: 1.0
 */
@Slf4j
public class MulitSheetReadListenerTest {

    private static final String BASE_DIR = "D:\\hpp\\test\\easyExcel\\";

    @Test
    public void readMultiSheet(){

        String filePath = String.format("%s%s", BASE_DIR, "读取指定多sheet页.xlsx");

        ExcelReader excelReader = EasyExcel.read(filePath).build();

        // 测试读取指定名称的sheet页

        for(int i = 1; i <= 3; i++){

            String sheetName = "Sheet" + i;

            ReadSheet readSheet = getReadSheet(sheetName,i);
            excelReader.read(readSheet);
        }

    }

    private ReadSheet getReadSheet(String sheetName,Integer sheetIndex) {
        return EasyExcel.readSheet(sheetName)
                .registerReadListener(new ReadListener<Map<Integer, String>>() {
                    @Override
                    public void onException(Exception exception, AnalysisContext context) throws Exception {

                    }

                    @Override
                    public void invokeHead(Map headMap, AnalysisContext context) {

                    }

                    @Override
                    public void invoke(Map<Integer, String> record, AnalysisContext context) {
                        String sheetName = context.getCurrentSheet().getSheetName();
                        log.info("读取sheet页:{}", sheetName);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {

                    }

                    @Override
                    public boolean hasNext(AnalysisContext context) {
                        return false;
                    }
                }).build();
    }
}
