package java.com;

import com.alibaba.fastjson.JSONObject;
import com.sf.vsolution.hx.hanzt.template.generator.utils.PoiTlUtils;
import com.sf.vsolution.hx.hanzt.template.html.param.FrontDynamicData;
import com.sf.vsolution.hx.hanzt.template.html.param.PoiDocumentConfig;
import com.sf.vsolution.hx.hanzt.template.html.param.RichContent;
import com.sf.vsolution.hx.hanzt.template.html.parse.RichTextParser;
import org.junit.Test;

import java.io.*;

/**
 * @author 01415355
 * @description: html标签解析
 * @date 2023年09月06日
 * @version: 1.0
 */
public class HtmlParseTests {

    private static final String BASE_DIR = "D:\\hpp\\poi\\";

    @Test
    public void parseHtml() {
        String fileName = "1.html";
        String dynamicParam = "[{\"htmlId\": \"7xci8ik077w0000\", \"isActive\": false, \"className\": \"dynamic-table\", \"paramName\": \"银行存款\", \"paramType\": \"table\", \"paramTableHeader\": [{\"columnName\": \"账户名称\", \"columnType\": \"string\"}, {\"columnName\": \"银行账号\", \"columnType\": \"string\"}, {\"columnName\": \"币种\", \"columnType\": \"string\"}, {\"columnName\": \"利率\", \"columnType\": \"string\"}, {\"columnName\": \"账户类型\", \"columnType\": \"string\"}, {\"columnName\": \"账户余额\", \"columnType\": \"string\"}, {\"columnName\": \"是否属于资金归集 （资金池或其他资金 管理）账户\", \"columnType\": \"string\"}, {\"columnName\": \"起始日期\", \"columnType\": \"string\"}, {\"columnName\": \"终止日期\", \"columnType\": \"string\"}, {\"columnName\": \"是否存在冻结、担保或其他使用限制（如是 ，请注明）\", \"columnType\": \"string\"}, {\"columnName\": \"备注\", \"columnType\": \"string\"}]}, {\"htmlId\": \"89uhyud4w9c0000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"被审计单位联系人\", \"paramType\": \"text\"}, {\"htmlId\": \"27n4vt462avw000\", \"isActive\": false, \"className\": \"dynamic-table\", \"paramName\": \"银行借款\", \"paramType\": \"table\", \"paramTableHeader\": [{\"columnName\": \"借款人名称\", \"columnType\": \"string\"}, {\"columnName\": \"借款账户\", \"columnType\": \"string\"}, {\"columnName\": \"币种\", \"columnType\": \"string\"}, {\"columnName\": \"余额\", \"columnType\": \"string\"}, {\"columnName\": \"借款日期\", \"columnType\": \"string\"}, {\"columnName\": \"到期日期\", \"columnType\": \"string\"}, {\"columnName\": \"利率\", \"columnType\": \"string\"}, {\"columnName\": \"抵（质）押品/担保人\", \"columnType\": \"string\"}, {\"columnName\": \"备注\", \"columnType\": \"string\"}]}, {\"htmlId\": \"cxgdtyf8mug0000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"被函证单位\", \"paramType\": \"text\"}, {\"htmlId\": \"exb18huut9c0000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"被审计单位联系人电话\", \"paramType\": \"text\"}, {\"htmlId\": \"2j1bn5o1ji00000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"函证中心收件地址\", \"paramType\": \"text\"}, {\"htmlId\": \"eavgrcj0ji00000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"函证中心联系人\", \"paramType\": \"text\"}, {\"htmlId\": \"2wjx9k0yxjw0000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"函证中心联系人电话\", \"paramType\": \"text\"}, {\"htmlId\": \"7iz4cfez5gs0000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"审计年度\", \"paramType\": \"text\"}, {\"htmlId\": \"6mtczhtn7xs0000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"函证中心传真\", \"paramType\": \"text\"}, {\"htmlId\": \"au2q4lnunz40000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"函证中心邮编\", \"paramType\": \"text\"}, {\"htmlId\": \"3xr11hyoqsi0000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"函证基准日\", \"paramType\": \"text\"}, {\"htmlId\": \"1x65qqhr089s000\", \"isActive\": false, \"className\": \"dynamic-text\", \"paramName\": \"被审计单位\", \"paramType\": \"text\"}]";
        String templateId = "23";
        String sourcePath = String.format("%s%s", BASE_DIR, fileName);
        String targetPath = String.format("%s%s", BASE_DIR, "1.docx");



        String content = readContent(sourcePath);

        RichContent richContent = new RichContent();

        richContent.setContent(content);
        richContent.setDynamicDataList(JSONObject.parseArray(dynamicParam, FrontDynamicData.class));
        richContent.setTemplateId(templateId);

        PoiDocumentConfig poiDocumentConfig = new PoiDocumentConfig();

        PoiDocumentConfig.PageMarginsConfig pageMarginsConfig = new PoiDocumentConfig.PageMarginsConfig();
        pageMarginsConfig.setBottom(2.54d);
        pageMarginsConfig.setTop(2.54d);
        pageMarginsConfig.setLeft(3.17d);
        pageMarginsConfig.setRight(2.7d);

        poiDocumentConfig.setPageMarginsConfig(pageMarginsConfig);

        PoiDocumentConfig.FooterAndHeaderConfig footerAndHeaderConfig = new PoiDocumentConfig.FooterAndHeaderConfig();
        footerAndHeaderConfig.setFooter(1.5d);
        footerAndHeaderConfig.setHeader(1.75d);
        footerAndHeaderConfig.setHasPageNum(Boolean.TRUE);
        poiDocumentConfig.setFooterAndHeader(footerAndHeaderConfig);

        richContent.setPoiDocumentConfig(poiDocumentConfig);

        RichTextParser.parse(richContent, targetPath);

    }

    private String readContent(String targetPath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(targetPath)));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @Test
    public void wordConvertToPdf() throws IOException {
        String filePath = String.format("%s%s", BASE_DIR, "hzt_company_fileManagerPath___fc93e92bcb8e4feda80739dc9db29d3f (1).docx");
        String outputPdfPath = String.format("%s%s", BASE_DIR, "1.pdf");
        // 缺失字体导致无法渲染正确的pdf
        PoiTlUtils.wordToPdf(filePath, outputPdfPath);

    }

}
