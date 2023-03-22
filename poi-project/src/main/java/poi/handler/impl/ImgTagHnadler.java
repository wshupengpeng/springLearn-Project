package poi.handler.impl;

import cn.hutool.core.io.FileUtil;
import org.jsoup.nodes.Node;
import poi.handler.AbstractHtmlTagHandler;
import poi.handler.param.DocumentParam;

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
    public void handler(DocumentParam documentParam) {
        Node currentNode = documentParam.getCurrentNode();
        String imgRealPath = getImageRealPath(currentNode);
    }

    private String getImageRealPath(Node currentNode) {
        String src = currentNode.attr("src");
        if(src.startsWith("data:image")){
            byte[] decode = Base64.getDecoder().decode(src.split(",")[1]);
            String tempPath = FileUtil.getTmpDirPath() + UUID.randomUUID() + ".png";
            FileUtil.writeBytes(decode,tempPath);
            return tempPath;
        }else if(src.matches("^(http|https)")){
            
        }

        return null;
    }
}
