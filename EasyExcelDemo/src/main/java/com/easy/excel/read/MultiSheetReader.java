package com.easy.excel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSONObject;
import com.easy.excel.customize.CustomStringStringConverter;
import com.easy.excel.listener.BaseListener;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @creater hpp
 * @Date 2024/1/31-10:48
 * @description: 测试多sheet页读入效率
 */
@Slf4j
public class MultiSheetReader {


    public static void main(String[] args) {
        String excelPath = "D:\\测试数据\\批量sheet页导入.xlsx";
        List<ImportDemo> importDemoList = Collections.synchronizedList(new ArrayList<>());
        long start = System.currentTimeMillis();
        EasyExcel.read(excelPath)
                .head(ImportDemo.class)
                .registerReadListener(new AnalysisEventListener<ImportDemo>() {
                    @Override
                    public void invoke(ImportDemo o, AnalysisContext analysisContext) {
//                        log.info("读取内容:{}", JSONObject.toJSONString(o));
                        importDemoList.add(o);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                    }
                }).ignoreEmptyRow(Boolean.TRUE).doReadAll();
//        ExcelReader excelReader = EasyExcel.read(excelPath)
//                .build();
//        try {
//            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
////            List<Sheet> sheets = excelReader.getSheets();
////            List<CompletableFuture<Void>> completableFutures =
//            List<Integer> list = Arrays.asList(0,1, 2, 3,4,5,6,7);
//            List<CompletableFuture<Void>> completableFutures = list.stream().map(sheet -> {
////                ReadSheet readSheet =
////                        EasyExcel.readSheet(sheet.getSheetNo(),sheet.getSheetName()).head(ImportDemo.class).registerReadListener(new AnalysisEventListener<ImportDemo>() {
////                            @Override
////                            public void invoke(ImportDemo o, AnalysisContext analysisContext) {
////                                log.info("读取内容:{}", JSONObject.toJSONString(o));
////                                importDemoList.add(o);
////                            }
////
////                            @Override
////                            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
////
////                            }
////                        }).build();
//                        ;
//                        return CompletableFuture.runAsync(() -> EasyExcel.read(excelPath)
//                                .head(ImportDemo.class)
//                                .ignoreEmptyRow(Boolean.TRUE)
//                                .registerReadListener(new AnalysisEventListener<ImportDemo>() {
//                                    private Integer count = 0;
//
//                                    @Override
//                                    public void invoke(ImportDemo o, AnalysisContext analysisContext) {
////                                        log.info("读取内容:{}", JSONObject.toJSONString(o));
//                                        count++;
//                                        importDemoList.add(o);
//                                    }
//
//                                    @Override
//                                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                                        log.info("当前sheet页读取行数:{}", count);
//                                    }
//                                }).sheet(sheet).doRead());
//                    })
//                    .collect(Collectors.toList());
//            List<Void> collect = completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
////            ReadSheet readSheet2 =
////                    EasyExcel.readSheet(1).head(ImportDemo.class).registerReadListener(new DemoDataListener()).build();
////            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
////            excelReader.read(readSheet1, readSheet2);
////            voidCompletableFuture.join();
//        } finally {
//            excelReader.finish();
//        }
        log.info("总数：{},总耗时：{}ms", importDemoList.size(), System.currentTimeMillis() - start);
    }

    @Data
    public static final class ImportDemo {
        @ExcelProperty(converter = CustomStringStringConverter.class, value = "测试表头1")
        private String string;
        /**
         * 这里用string 去接日期才能格式化。我想接收年月日格式
         */
        @ExcelProperty(value = "测试表头2")
        private String date;
        /**
         * 我想接收百分比的数字
         */
        @ExcelProperty(value = "测试表头3")
        private String doubleData;
    }
}
