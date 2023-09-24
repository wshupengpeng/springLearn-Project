package com.sf.vsolution.hx.hanzt.template.generator.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 用于校验数量的监听器
 * @Author 01415355
 * @Date 2023/7/13 18:18
 */
public class MaxValidListener extends AnalysisEventListener<Map<Integer, String>> {

    private AtomicInteger totalNum = new AtomicInteger(0);

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        totalNum.incrementAndGet();
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public int getTotalNum() {
        return totalNum.intValue();
    }
}
