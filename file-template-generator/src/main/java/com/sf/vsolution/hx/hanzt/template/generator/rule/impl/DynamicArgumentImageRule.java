package com.sf.vsolution.hx.hanzt.template.generator.rule.impl;

import com.deepoove.poi.data.PictureRenderData;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.generator.enums.TemplateImageTypeEnum;
import com.sf.vsolution.hx.hanzt.template.generator.param.DynamicTemplateImage;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateCol;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateConfiguration;
import com.sf.vsolution.hx.hanzt.template.generator.param.FileTemplateRenderData;
import com.sf.vsolution.hx.hanzt.template.generator.rule.DynamicArgumentRule;
import com.sf.vsolution.hx.hanzt.template.generator.utils.DrawImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description: 动态参数图片规则
 * @Author 01415355
 * @Date 2023/5/8 16:45
 */
@Slf4j
public class DynamicArgumentImageRule implements DynamicArgumentRule {

    @Override
    public void rule(FileTemplateConfiguration configuration, FileTemplateCol fileTemplateCol, FileTemplateRenderData fileTemplateRenderData) {
        fileTemplateCol.getImageList().stream().forEach(bizTemplateImage -> {
            addDynamicImage(configuration, bizTemplateImage, fileTemplateCol, fileTemplateRenderData);
        });
    }

    private void addDynamicImage(FileTemplateConfiguration configuration,
                                 DynamicTemplateImage bizTemplateImage,
                                 FileTemplateCol fileTemplateCol,
                                 FileTemplateRenderData fileTemplateRenderData) {
        String code = fileTemplateCol.getFieldValue();
        String imageTempPath = configuration.getConfig().getImageDir() + File.separator + code
                + FileConstant.SEPARATOR_UNDERLINE + UUID.randomUUID() + FileConstant.JPG_SUFFIX;
        //生成条形码
        if (Objects.equals(bizTemplateImage.getImageType(), TemplateImageTypeEnum.BAR_CODE.getCode())) {
            // todo 后期需要增加配置,看编码是否需要加在条形码四周,目前默认加到底部
            DrawImageUtil.generateCode(new File(imageTempPath), code, code, null, null, 125, 25);
        } else {
            try {
                DrawImageUtil.createImage(code, imageTempPath, Integer.valueOf(bizTemplateImage.getFitWidth()), Integer.valueOf(bizTemplateImage.getFitHeight()));
            } catch (Exception e) {
                log.error("create qrCode image failed, error msg:", e);
            }
        }
        PictureRenderData imageRender = new PictureRenderData(Integer.valueOf(bizTemplateImage.getFitWidth()), Integer.valueOf(bizTemplateImage.getFitHeight()), imageTempPath);

        fileTemplateRenderData.getRenderDataMap().put(bizTemplateImage.getImagePlaceHolder(), imageRender);
    }


    @Override
    public boolean isMatchRule(FileTemplateCol fileTemplateCol) {
        return CollectionUtils.isNotEmpty(fileTemplateCol.getImageList());
    }
}
