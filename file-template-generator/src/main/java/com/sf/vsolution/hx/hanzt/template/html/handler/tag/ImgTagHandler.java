package com.sf.vsolution.hx.hanzt.template.html.handler.tag;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.RichText;
import com.sf.vsolution.hx.hanzt.template.html.utils.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Node;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * @creater hpp
 * @Date 2023/3/18-20:50
 * @description:
 */
@Slf4j
public class ImgTagHandler extends AbstractHtmlTagHandler {
    @Override
    public String getTagName() {
        return "img";
    }


    @Override
    public void doHandle(RichText richText) {
        Node currentNode = richText.getCurrentNode();
        String imgRealPath = getImageRealPath(currentNode);

        if(StringUtils.isBlank(imgRealPath)){
            log.error("image download failed, imgRealPath is empty");
            return;
        }

        try {
            // 获取图片高宽
            BufferedImage image = ImageIO.read(new FileInputStream(imgRealPath));
            // 解析宽和高
            int width = JsoupUtils.getAttribute(currentNode, PoiCommon.WIDTH, Integer::valueOf, image.getWidth());
            int height = JsoupUtils.getAttribute(currentNode, PoiCommon.HEIGHT, Integer::valueOf, image.getHeight());

            PictureRenderData pictureRenderData = new PictureRenderData(width, height, imgRealPath);

            PictureRenderPolicy.Helper.renderPicture(richText.createRun(), pictureRenderData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getImageRealPath(Node currentNode) {
        String src = currentNode.attr("src");
        String tempPath = FileConstant.EMPTY_STR;
        if (src.startsWith("data:image")) {
            byte[] decode = Base64.getDecoder().decode(src.split(",")[1]);
            tempPath = FileUtil.getTmpDirPath() + UUID.randomUUID() + ".png";
            FileUtil.writeBytes(decode, tempPath);
        } else if (src.matches("^(?i)(http|https)://.*$")) {
            tempPath = FileUtil.getTmpDirPath() + UUID.randomUUID() + ".png";
            HttpUtil.downloadFile(src, tempPath);
        }
        return tempPath;
    }
}
