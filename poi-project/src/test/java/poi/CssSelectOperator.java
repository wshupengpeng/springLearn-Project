package poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * @creater hpp
 * @Date 2023/3/16-21:59
 * @description: css选择器测试
 */
@Slf4j
public class CssSelectOperator {
    public static final XWPFDocument doc = new XWPFDocument();

    public static final String descPath = "d://hpp//test.doc";

    public static final String srcPath = "d://hpp//2.html";

    public static final String srcPath_home = "d://测试数据/poi.html";

    public static final String descPath_home = "d://测试数据/poi.doc";


    @Before
    public void before(){
//        File file = new File(descPath);
        File file = new File(descPath_home);
        file.deleteOnExit();
    }


    @After
    public void after() throws IOException {
//        doc.write(new FileOutputStream(descPath));
        doc.write(new FileOutputStream(descPath_home));
    }


    @Test
    public void cssSelect() throws IOException {
        Document parse = Jsoup.parse(new File(srcPath_home));
        Element body = parse.body();
        Element table = body.getElementsByTag("tbody").first();

        Elements tr = table.getElementsByTag("tr");

        Iterator<Element> iterator = tr.iterator();

        Element next;
        while(iterator.hasNext()){
            next = iterator.next();
            Elements th = next.select("th");
            if(!th.isEmpty()){
                log.info("th has not exists attr:{}",th.attr("123","456").text());
            }
            log.info("css query qd result:{}",th);
//            log.info("css select th|td result:{},html:{}",next.hasAttr("th"),next.outerHtml());

        }
        log.info(body.html());
    }




}
