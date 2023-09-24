package com.sf.vsolution.hx.hanzt.template.generator.provider;

import com.lowagie.text.pdf.BaseFont;
import com.sf.vsolution.hx.hanzt.template.generator.enums.FontsEnum;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 01415355
 * @description: 字体提供类, 用于适配word转pdf的中文字体,防止因为字体导致word转pdf部分中文字符不显示
 * @date 2023年09月13日
 * @version: 1.0
 */
@Slf4j
public class CustomizeFontProvider implements IFontProvider {

    private static final CustomizeFontProvider customizeFontProvider = new CustomizeFontProvider();

    public static CustomizeFontProvider getInstance() {
        return customizeFontProvider;
    }

    private Map<String, String> fontsResourceMap = new HashMap() {
        {
            this.put(FontsEnum.SIMSUN.getFontFamily(), "/usr/share/fonts/simsun.ttc,0");
            this.put(FontsEnum.SIMHEI.getFontFamily(), "/usr/share/fonts/simhei.ttf");
            this.put(FontsEnum.TIMES_NEW_ROMAN.getFontFamily(), "/usr/share/fonts/times.ttf");
        }
    };


    @Override
    public Font getFont(String fontFamily, String encoding, float size, int style, Color color) {

        if (StringUtils.isNotBlank(fontFamily)) {
            try {
                BaseFont baseFont = null;
                if (fontsResourceMap.containsKey(fontFamily)) {
                    baseFont = BaseFont.createFont(fontsResourceMap.get(fontFamily), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                } else {
                    baseFont = BaseFont.createFont(fontsResourceMap.get(FontsEnum.SIMSUN.getFontFamily()), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                }
                return new Font(baseFont, size, style, color);
            } catch (Exception e) {
                throw new IllegalArgumentException("Font was not found" + e);
            }
        }
        return null;
    }


}
