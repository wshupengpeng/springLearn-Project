package com.spring.excel.processor;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.spring.excel.support.AnnotationDefinition;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/12 16:02
 */
@Component
public class NormalExcelPostProcessor extends AbstractExcelPostProcessor {

    @Override
    public void postProcessBeforeWrite(AnnotationDefinition defintion) {
        defintion.addWriterHandler(getSimpleColumnWidthStyleStrategy())
                .add(getHorizontalCellStyleStrategy());
    }

    /**
     * 设置长度
     *
     * @return
     */
    public static SimpleColumnWidthStyleStrategy getSimpleColumnWidthStyleStrategy() {
        return new SimpleColumnWidthStyleStrategy(30);
    }

    /**
     * easy-excel 自定义样式
     *
     * @return HorizontalCellStyleStrategy
     */
    public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为灰色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        contentWriteCellStyle.setDataFormat((short) 49);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
}
