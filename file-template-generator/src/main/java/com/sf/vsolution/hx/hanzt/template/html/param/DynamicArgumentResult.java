package com.sf.vsolution.hx.hanzt.template.html.param;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 解析的动态参数结果
 * @Author 01415355
 * @Date 2023/5/5 13:48
 */
@Data
public class DynamicArgumentResult {

    /**
     * 动态文本参数
     */
    private List<DynamicTextField> bizTemplateFields = new ArrayList<>();

    /**
     * 动态图片参数
     */
//    private List<BizTemplateImage> bizTemplateImages = new ArrayList<>();

    /**
     * 动态表格参数引用
     */
//    private List<BizTemplateReference> bizTemplateReflects = new ArrayList<>();


    /**
     * 占位符映射,k->动态文本参数名 v-> 动态文本对象
     * 用于判断当前是否含有重复的参数名称,相同的参数名称,需保持占位符一致
     */
    private Map<String, DynamicTextField> placeholderMap = new HashMap<>();

    /**
     * 当前模板id
     */
    private String templateId;

    /**
     * 参数sequenceId
     */
    private int sequence;


    public int incrementSequenceAndGet() {
        return sequence++;
    }

}
