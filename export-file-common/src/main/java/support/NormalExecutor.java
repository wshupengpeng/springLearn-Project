package com.spring.excel.support;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.spring.excel.annotation.ExportExcel;
import com.spring.excel.enums.ExportModeEnum;
import com.spring.excel.enums.SubSelectionEnum;
import com.spring.excel.exceptions.ExcelCommonException;
import com.spring.excel.pojo.FieldEntity;
import com.spring.excel.pojo.PageArgs;
import com.spring.excel.support.interfaces.ExcelExecutor;
import com.spring.excel.utils.ExcelUtils;
import com.spring.excel.utils.HttpServletHolderUtil;
import com.spring.excel.utils.ResponseUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @creater hpp
 * @Date 2022/10/13-21:29
 * @description:
 */
@Component
public class NormalExecutor implements ExcelExecutor {
    @Override
    public void execute(AnnotationDefinition annotationDefinition) {
        if(annotationDefinition instanceof ExcelAnnotationDefinition){
            ExcelAnnotationDefinition defintion = (ExcelAnnotationDefinition) annotationDefinition;
            Class<?> beanClass = defintion.getExportAnnotation().beanClass();
            ProceedingJoinPoint jp = (ProceedingJoinPoint) defintion.getJp();
            Class returnType = defintion.getMethodSignature().getReturnType();
            ExportExcel exportAnnotation = defintion.getExportAnnotation();
            try {
                if (Collection.class.isAssignableFrom(returnType)) {
                    // 定义规则对象
                    PageArgs pageArgs = ExcelUtils.parsePage(defintion);
                    // 添加限制
                    if(pageArgs.getPage(SubSelectionEnum.PAGE_SIZE).getValue() > exportAnnotation.limit()) throw new ExcelCommonException("超出最大导出数量");
                    Object proceed = jp.proceed(pageArgs.buildPage());
                    List<FieldEntity> parse = ExcelUtils.parseHead(beanClass);
                    List<List<String>> headList = parse.stream()
                            .map(field->Arrays.asList(field.getName()))
                            .collect(Collectors.toList());
                    List<List<String>> dataList = ExcelUtils.parseData((Collection) proceed, parse);
                    writeToExcel(defintion, headList, dataList);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean support(AnnotationDefinition definition) {
        return definition.getExportAnnotation().mode() == ExportModeEnum.NORMAL
                && (definition instanceof ExcelAnnotationDefinition);
    }

    private void writeToExcel(ExcelAnnotationDefinition defintion, List<List<String>> headList,
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
