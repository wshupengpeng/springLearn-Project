package com.sf.vsolution.hx.hanzt.template.html.param;

import com.sf.vsolution.hx.hanzt.template.html.function.RichTextParseResultCallBackFn;
import lombok.Data;

import java.util.List;

/**
 * @Description: 前端富文本解析入参
 * @Author 01415355
 * @Date 2023/5/5 17:33
 */
@Data
public class RichContent {

    /**
     * 富文本
     */
    private String content;

    /**
     *  前端动态参数入参
     */
    private List<FrontDynamicData> dynamicDataList;

    /**
     *  模板id
     */
    private String templateId;

    /**
     * 解析后的回调
     */
    private RichTextParseResultCallBackFn richTextParseResultCallBackFn;

    /**
     *  poi参数配置
     */
    private PoiDocumentConfig poiDocumentConfig;
}
