package poi.v2.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import poi.handler.AbstractHtmlTagHandler;
import poi.v2.AbstractHtmlTagHandlerV2;
import poi.v2.handler.param.RichText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 富文本解析器
 * @Author 01415355
 * @Date 2023/4/18 10:49
 */
@Data
@Slf4j
public class RichTextParser {

    public static final Map<String, AbstractHtmlTagHandlerV2> tagHandlerMap = new HashMap<>();
    static {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(ClassUtil.getPackage(AbstractHtmlTagHandlerV2.class), AbstractHtmlTagHandlerV2.class);
        if (CollUtil.isNotEmpty(classes)) {
            for (Class clazz:classes) {
                AbstractHtmlTagHandlerV2 handler = (AbstractHtmlTagHandlerV2) ReflectUtil.newInstance(clazz);
                tagHandlerMap.put(handler.getTagName(), handler);
            }
        }
    }

    public static void parse(String content, String filePath) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 对内容预处理,处理半角或全角字符等问题,待确认
        content = prepareContent(content);
        log.info("prepareContent complete, cost time :{}s", stopWatch.getTime(TimeUnit.SECONDS));
        // 解析html对象
        Document parse = Jsoup.parse(content);

        // 创建Word文档对象
        XWPFDocument doc = new XWPFDocument();

        // 组装richText待解析对象
        RichText richText = new RichText();
        richText.setDoc(doc);
        richText.setCurrentParagraph(doc.createParagraph());
        richText.setCurrentNode(parse.body());
        richText.createRun();

        // 解析标签
        AbstractHtmlTagHandlerV2 handler = getHandler(parse.body().tagName());

        if(Objects.isNull(handler)){
            log.info("未定义标签:{}处理类,请定义后进行处理", parse.body().tagName());
            return;
        }

        handler.handler(richText);

        log.info("prepareContent complete, cost time:{}s", stopWatch.getTime(TimeUnit.SECONDS));
        // 写出到指定位置
        try {
            doc.write(new FileOutputStream(filePath));
        } catch (Exception e) {
            log.error("richText parse failed,error:",e);
        }

        log.info("write to file complete, total cost time:{}s", stopWatch.getTime(TimeUnit.SECONDS));

    }

    public static AbstractHtmlTagHandlerV2 getHandler(String tagName) {

        for (String tagRegexName : tagHandlerMap.keySet()) {
            if(tagRegexName.equalsIgnoreCase(tagName) || tagName.matches(tagRegexName)){
                return tagHandlerMap.get(tagRegexName);
            }
        }
        // 未定义的标签选择不处理
        return null;
    }



    public static String prepareContent(String content){
        //todo 预处理操作

        return content;
    }

}
