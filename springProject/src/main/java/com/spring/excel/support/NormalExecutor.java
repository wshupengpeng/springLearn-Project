package com.spring.excel.support;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.spring.excel.pojo.FieldEntity;
import com.spring.excel.support.interfaces.ExcelExecutor;
import com.spring.excel.utils.ExcelUtils;
import com.spring.excel.utils.HttpServletHolderUtil;
import com.spring.excel.utils.ResponseUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @creater hpp
 * @Date 2022/10/13-21:29
 * @description:
 */
public class NormalExecutor implements ExcelExecutor {
    @Override
    public void execute(AnnotationDefinition defintion) {
        Class<?> returnType = defintion.getExportAnnotation().beanClass();
        ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
        try {
            Object proceed = jp.proceed();
            if(proceed instanceof List){
                List<FieldEntity> parse = ExcelUtils.parseHead(returnType);
                List<List<String>> headList = parse.stream()
                        .map(field->Arrays.asList(field.getName()))
                        .collect(Collectors.toList());
                List list = (List) proceed;
                List<List<String>> dataList = ExcelUtils.parseData(list, parse);
                writeToExcel(defintion, headList, dataList);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private void writeToExcel(AnnotationDefinition defintion, List<List<String>> headList,
                              List<List<String>> dataList) throws IOException {
        ExcelWriter writer = null;
        try {
            HttpServletResponse response = HttpServletHolderUtil.getHttpServletResponse();
            ResponseUtils.setExcelResponseHead(response, defintion.getExportAnnotation().fileName());
            ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream())
                    .head(headList);
            defintion.getWriteHandlerList().forEach(writerBuilder::registerWriteHandler);
            writer = writerBuilder.build();
            WriteSheet writeSheet = new WriteSheet();
            writeSheet.setSheetNo(0);
            writeSheet.setSheetName(defintion.getExportAnnotation().sheetName());
            writer.write(dataList,writeSheet);
        }finally {
            if(writer != null) writer.finish();
        }
    }

}
