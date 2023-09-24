package com.sf.vsolution.hx.hanzt.template.generator.param;

import com.deepoove.poi.data.RenderData;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 一个文件生成配置
 * @Author 01415355
 * @Date 2023/1/17 18:51
 */
@Data
public class FileTemplateRenderData {
    /**
     *  excel读取后配置
     */
    private Map<String, RenderData> renderDataMap = new HashMap<>();
    /**
     * 文件名称
     */
    private String fileName;

    private String confirmationNo;
    /**
     *  图片配置
     */
    private List<ImageConfig> imageConfigs = new ArrayList<>();
}
