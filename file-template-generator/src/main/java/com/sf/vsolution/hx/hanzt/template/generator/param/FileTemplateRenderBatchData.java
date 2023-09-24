package com.sf.vsolution.hx.hanzt.template.generator.param;

import lombok.Data;

import java.util.List;

/**
 * @Description：一批文件生成配置
 * @Author 01415355
 * @Date 2023/5/9 14:58
 */
@Data
public class FileTemplateRenderBatchData {

    /**
     *  一批文件制作动态参数
     */
    private List<FileTemplateRenderData> fileTemplateRenderData;


}
