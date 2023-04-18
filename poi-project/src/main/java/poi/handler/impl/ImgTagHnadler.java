package poi.handler.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.jsoup.nodes.Node;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.UUID;

/**
 * @creater hpp
 * @Date 2023/3/18-20:50
 * @description:
 */
public class ImgTagHnadler extends AbstractHtmlTagHandler {
    @Override
    public String getTagName() {
        return "img";
    }

    @Override
    public void doHandler(DocumentParam documentParam) {
        onPreHandler(documentParam);
        Node currentNode = documentParam.getCurrentNode();
        String imgRealPath = getImageRealPath(currentNode);
        try {
            documentParam.getDoc().addPictureData(new FileInputStream(imgRealPath), Document.PICTURE_TYPE_PNG);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }finally {
            FileUtil.del(imgRealPath);
        }
    }

    private String getImageRealPath(Node currentNode) {
        String src = currentNode.attr("src");
        String tempPath = FileUtil.getTmpDirPath() + UUID.randomUUID() + ".png";
        if (src.startsWith("data:image")) {
            byte[] decode = Base64.getDecoder().decode(src.split(",")[1]);
            tempPath = FileUtil.getTmpDirPath() + UUID.randomUUID() + ".png";
            FileUtil.writeBytes(decode, tempPath);
        } else if (src.matches("^(http|https)")) {
            HttpUtil.downloadFile(src, tempPath);
        }
        return tempPath;
    }
}
