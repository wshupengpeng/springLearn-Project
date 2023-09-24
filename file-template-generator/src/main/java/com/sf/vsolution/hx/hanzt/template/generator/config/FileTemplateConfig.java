package com.sf.vsolution.hx.hanzt.template.generator.config;

import cn.hutool.core.io.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

/**
 * @Author 01415355
 * @Description: 文件模板常量
 * @Date 2023/1/17 19:03
 */
@Data
@AllArgsConstructor
public class FileTemplateConfig {
    private Integer importSize;

    private String destWordPath;

    private String destPdfPath;

    private String imageDir;

    private static final String FILE_TEMPLATE_PREFIX_DIR = "/template";

    public static final FileTemplateConfig DEFAULT_CONFIG = new FileTemplateConfig(1000,
            FILE_TEMPLATE_PREFIX_DIR + File.separator + "wordPath",
            FILE_TEMPLATE_PREFIX_DIR + File.separator + "pdfPath",
            FILE_TEMPLATE_PREFIX_DIR + File.separator + "imgPath");

    static {
        DEFAULT_CONFIG.init();
    }


    public void init() {
        FileUtil.mkdir(destWordPath);
        FileUtil.mkdir(destPdfPath);
        FileUtil.mkdir(imageDir);
    }


}
