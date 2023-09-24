package com.sf.vsolution.hx.hanzt.template.html.parse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import com.sf.vsolution.hx.hanzt.template.html.enums.DynamicTypeEnum;
import com.sf.vsolution.hx.hanzt.template.html.handler.AbstractHtmlTagHandler;
import com.sf.vsolution.hx.hanzt.template.html.param.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/4/28 9:20
 */
@Slf4j
public class RichTextParser {


    public static final Map<String, AbstractHtmlTagHandler> tagHandlerMap = new HashMap<>();

    static {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper(ClassUtil.getPackage(AbstractHtmlTagHandler.class), AbstractHtmlTagHandler.class);
        if (CollUtil.isNotEmpty(classes)) {
            for (Class clazz : classes) {
                AbstractHtmlTagHandler handler = (AbstractHtmlTagHandler) ReflectUtil.newInstance(clazz);
                tagHandlerMap.put(handler.getTagName(), handler);
            }
        }
    }

    public static RichTextParseResult parse(RichContent richContent, String filePath) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        RichTextParseResult result = new RichTextParseResult();
        try {
            Assert.notNull(filePath, "文件路径不能为空");
            Assert.notNull(richContent.getContent(), "富文本内容不能为空");
            Assert.notNull(richContent.getTemplateId(), "模板id不能为空");
            // 对内容预处理,处理半角或全角字符等问题,待确认
            richContent.setContent(prepareContent(richContent.getContent()));
            log.info("prepareContent complete, cost time :{}s", stopWatch.getTime(TimeUnit.SECONDS));

            // 预处理动态参数
            DynamicArgumentResult extendParams = prepareDynamicData(richContent);
            log.info("prepareDynamicData complete,cost time:{}s", stopWatch.getTime(TimeUnit.SECONDS));

            // 解析html对象
            Document parse = Jsoup.parse(richContent.getContent());

            // 创建Word文档对象
            XWPFDocument doc = new XWPFDocument();
            // 设置document转pdf必须设置的参数
            // 不设置文件生成word转pdf会报错
            setDocumentRequireAttribute(doc, richContent);

            // 组装richText待解析对象
            RichText richText = new RichText();
            richText.setDoc(doc);
            richText.setCurrentParagraph(doc.createParagraph());
            richText.setCurrentNode(parse.body());
            richText.setTextFormatStyle(new TextFormatStyle());
            richText.setExtendParams(extendParams);
            richText.setRepeatHeader(Optional.ofNullable(richContent.getPoiDocumentConfig()).map(PoiDocumentConfig::isRepeatHeader).orElse(Boolean.FALSE));
            richText.createRun();


            // 解析标签
            parseTag(parse.body().tagName(), richText);

            // 设置解析后的动态参数
            result.setExtendParams(richText.getExtendParams());

            log.info("prepareContent complete, cost time:{}s", stopWatch.getTime(TimeUnit.SECONDS));
            // 写出到指定位置
            doc.write(new FileOutputStream(filePath));

            // 回调函数
            if (Objects.nonNull(richContent.getRichTextParseResultCallBackFn())) {
                richContent.getRichTextParseResultCallBackFn().parseRichTextAfter(result);
            }
        } catch (Exception e) {
            log.error("richText parse failed,error:", e);
            result.setIsSuccess(Boolean.FALSE);
            result.setErrorMsg("富文本解析发生异常");
        } finally {
//            FileUtil.del(filePath);
        }

        log.info("write to file complete, total cost time:{}s", stopWatch.getTime(TimeUnit.SECONDS));
        return result;
    }

    private static void setDocumentRequireAttribute(XWPFDocument doc, RichContent richContent) {
        doc.createStyles();

        PoiDocumentConfig poiDocumentConfig = Optional.ofNullable(richContent.getPoiDocumentConfig()).orElseGet(PoiDocumentConfig::new);

        PoiDocumentConfig.FooterAndHeaderConfig footerAndHeader = poiDocumentConfig.getFooterAndHeader();

        PoiDocumentConfig.PageMarginsConfig pageMarginsConfig = Optional.ofNullable(poiDocumentConfig.getPageMarginsConfig())
                .orElseGet(PoiDocumentConfig.PageMarginsConfig::new);

        if (Objects.nonNull(footerAndHeader)) {
            if (!doc.getDocument().getBody().isSetSectPr()) {
                doc.getDocument().getBody().addNewSectPr();
            }
            CTSectPr addNewSectPr = doc.getDocument().getBody().getSectPr();
            if (!addNewSectPr.isSetPgMar()) {
                addNewSectPr.addNewPgMar();
            }

            CTPageMar pgMar = addNewSectPr.getPgMar();

            if (Objects.nonNull(pageMarginsConfig.getBottom())) {
                pgMar.setBottom(BigInteger.valueOf((int) (PoiCommon.PER_CM * pageMarginsConfig.getBottom())));
            }

            if (Objects.nonNull(pageMarginsConfig.getTop())) {
                pgMar.setTop(BigInteger.valueOf((int) (PoiCommon.PER_CM * pageMarginsConfig.getTop())));
            }

            if (Objects.nonNull(pageMarginsConfig.getLeft())) {
                pgMar.setLeft(BigInteger.valueOf((int) (PoiCommon.PER_CM * pageMarginsConfig.getLeft())));
            }

            if (Objects.nonNull(pageMarginsConfig.getRight())) {
                pgMar.setRight(BigInteger.valueOf((int) (PoiCommon.PER_CM * pageMarginsConfig.getRight())));
            }

            if (Objects.nonNull(footerAndHeader.getFooter())) {
                pgMar.setHeader(BigInteger.valueOf((int) (PoiCommon.PER_CM * footerAndHeader.getFooter())));
            }

            if (Objects.nonNull(footerAndHeader.getHeader())) {
                pgMar.setFooter(BigInteger.valueOf((int) (PoiCommon.PER_CM * footerAndHeader.getHeader())));
            }

            if (Objects.nonNull(footerAndHeader.getHasPageNum()) && footerAndHeader.getHasPageNum()) {
                setFooterPageNum(doc, footerAndHeader);
            }

        }


        if (!doc.getDocument().getBody().isSetSectPr()) {
            doc.getDocument().getBody().addNewSectPr();
        }
        CTSectPr ctSectPr = doc.getDocument().getBody().getSectPr();

        if (!ctSectPr.isSetPgSz()) {
            ctSectPr.addNewPgSz();
        }

        CTPageSz pgSz = ctSectPr.getPgSz();

        long width = Optional.ofNullable(poiDocumentConfig.getPageMarginsConfig())
                .map(PoiDocumentConfig.PageMarginsConfig::getPageWidth)
                .map(w -> w * PoiCommon.PER_CM)
                .orElse((PoiCommon.A4_WIDTH * PoiCommon.PER_CM))
                .longValue();

        long height = Optional.ofNullable(poiDocumentConfig.getPageMarginsConfig())
                .map(PoiDocumentConfig.PageMarginsConfig::getPageHeight)
                .map(h -> h * PoiCommon.PER_CM)
                .orElse(PoiCommon.A4_HEIGHT * PoiCommon.PER_CM)
                .longValue();

        pgSz.setW(BigInteger.valueOf(width));
        pgSz.setH(BigInteger.valueOf(height));
        pgSz.setOrient(STPageOrientation.LANDSCAPE);

    }

    private static void setFooterPageNum(XWPFDocument document, PoiDocumentConfig.FooterAndHeaderConfig footerAndHeader) {
        XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();
        if (headerFooterPolicy == null){
            headerFooterPolicy = document.createHeaderFooterPolicy();
        }
        XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);
        XWPFParagraph footerParagraph = footer.createParagraph();
        // 宋体, 小五号
        setInstrText(footerParagraph, "PAGE\\*MERGEFORMAT", "宋体", 9);
        //设置段落对象
        XWPFRun runSuf = footerParagraph.createRun();//新的段落对象
        runSuf.setText(" / ");
        setXWPFRunStyle(runSuf,"宋体",9);
        setInstrText(footerParagraph, "NUMPAGES\\* MERGEFORMAT", "宋体", 9);
        footerParagraph.setAlignment(ParagraphAlignment.CENTER);
    }

    private static void setInstrText(XWPFParagraph paragraph, String instrText, String fontFamily, int fontSize) {
        XWPFRun run = paragraph.createRun();//新的段落对象
        CTFldChar fldChar = run.getCTR().addNewFldChar();//新的CTFldChar对象
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));
        CTText ctText = run.getCTR().addNewInstrText();
        ctText.setStringValue(instrText);
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        setXWPFRunStyle(run, fontFamily, fontSize);
        fldChar = run.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));
    }


    /**
     * 设置页脚的字体样式
     * @param r1 段落元素
     * @param font 段落元素
     * @param fontSize 的大小
     */
    public static void setXWPFRunStyle(XWPFRun r1, String font, int fontSize) {
        r1.setFontSize(fontSize);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setEastAsia(font);
        fonts.setHAnsi(font);
    }

    private static DynamicArgumentResult prepareDynamicData(RichContent richContent) {
        DynamicArgumentResult dynamicArgument = new DynamicArgumentResult();

        List<FrontDynamicData> dynamicDataList = richContent.getDynamicDataList();

        if (!CollectionUtils.isEmpty(dynamicDataList)) {
            for (FrontDynamicData frontDynamicData : dynamicDataList) {
                // 只预处理动态文本参数
                if (DynamicTypeEnum.TEXT.getType().equalsIgnoreCase(frontDynamicData.getParamType())
                        && !dynamicArgument.getPlaceholderMap().containsKey(frontDynamicData.getParamName())) {
                    DynamicTextField dynamicTextField = new DynamicTextField();
//                    bizTemplateField.setId(IdWorker.getIdStr());
                    dynamicTextField.setFieldName(frontDynamicData.getParamName());
                    dynamicArgument.getBizTemplateFields().add(dynamicTextField);
                    dynamicArgument.getPlaceholderMap().put(dynamicTextField.getFieldName(), dynamicTextField);
                }

            }
        }

        dynamicArgument.setTemplateId(richContent.getTemplateId());
        return dynamicArgument;
    }


    public static void parseTag(String tagName, RichText richText) {
        AbstractHtmlTagHandler handler = RichTextParser.getHandler(tagName);

        if (Objects.isNull(handler)) {
            log.info("未定义标签:{}处理类,请定义后进行处理", tagName);
            return;
        }

        handler.handler(richText);
    }


    public static AbstractHtmlTagHandler getHandler(String tagName) {

        for (String tagRegexName : tagHandlerMap.keySet()) {
            if (tagRegexName.equalsIgnoreCase(tagName) || tagName.matches(tagRegexName)) {
                return tagHandlerMap.get(tagRegexName);
            }
        }
        // 未定义的标签选择不处理
        return null;
    }


    public static String prepareContent(String content) {
        //todo 预处理操作

        return content.replaceAll("&nbsp;"," ");
    }
}
