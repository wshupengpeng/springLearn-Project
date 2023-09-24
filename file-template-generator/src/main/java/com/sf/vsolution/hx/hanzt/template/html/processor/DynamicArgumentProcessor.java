package com.sf.vsolution.hx.hanzt.template.html.processor;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.sf.vsolution.hx.hanzt.template.constant.FileConstant;
import com.sf.vsolution.hx.hanzt.template.generator.enums.FieldValueTypeEnum;
import com.sf.vsolution.hx.hanzt.template.html.common.PoiCommon;
import com.sf.vsolution.hx.hanzt.template.html.enums.DynamicTypeEnum;
import com.sf.vsolution.hx.hanzt.template.html.param.*;
import com.sf.vsolution.hx.hanzt.template.html.utils.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Node;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;

/**
 * @Description: 动态参数解析器, 用于解析动态参数
 * @Author 01415355
 * @Date 2023/5/5 13:42
 */
@Slf4j
public class DynamicArgumentProcessor implements HtmlTagProcessor {

    /**
     * 动态参数名称或sheet页名称
     */
    private static final String DATA_HZT_NAME = "data-hzt-name";

    /**
     * 动态参数类型
     * text: 动态文本
     * qrcode: 动态图片(二维码)
     * barcode: 动态图片(条形码)
     * table: 动态表格
     */
    private static final String DATA_HZT_TYPE = "data-hzt-type";

    /**
     * 动态参数样式,按照 k:v;k1:v2; 形式传入,每个参数按照;分隔,key和value按照:分隔。
     */
    private static final String DATA_HZT_STYLE = "data-hzt-style";


    /**
     * 动态表格唯一索引,设置选项为【已经设置的动态参数，是动态文本的参数名】
     */
    private static final String DATA_HZT_UNIQUEID = "data-hzt-uniqueid";


    private static final String DATA_HZT_TABLE_HEADER = "data-hzt-table-header";


    public boolean isSupport(Node node) {
        return node.hasAttr(DATA_HZT_TYPE);
    }


    @Override
    public void preChildHandle(RichText richText) {
        // 获取当前解析节点
        Node currentNode = richText.getCurrentNode();
        // 判断是否含有动态参数
        if (!isSupport(currentNode)) {
            return;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        log.info("dynamic argument resovle begin");

        // 获取动态参数类型
        String dynamicType = currentNode.attr(DATA_HZT_TYPE);
        switch (DynamicTypeEnum.getDynamicTypeEnumByType(dynamicType)) {
            case TEXT:
                parseDynamicText(richText);
                break;
//            case QRCODE:
//            case BARCODE:
//                parseDynamicImage(richText);
//                break;
            case TABLE:
                parseDynamicTable(richText);
                break;
            default:
                log.error("not support dynamic type, dynamicType:{}", dynamicType);
        }
        // 所有动态标签跳过子标签迭代
        richText.setContinueItr(Boolean.TRUE);
        stopWatch.stop();
        log.info("resolver argument end,cost time:{}ms", stopWatch.getTotalTimeMillis());
    }



    private void parseDynamicTable(RichText richText) {
        log.info("parseDynamicTable start");
        Node currentNode = richText.getCurrentNode();

        if (!currentNode.hasAttr(DATA_HZT_NAME)) {
            log.error("parseDynamicTable failed,missing data-hzt-name");
            return;
        }

        if (!currentNode.hasAttr(DATA_HZT_UNIQUEID)) {
            log.error("parseDynamicTable failed,missing data-hzt-uniqueid");
            return;
        }

        if (!currentNode.hasAttr(DATA_HZT_TABLE_HEADER)) {
            log.error("parseDynamicTable failed,missing data-hzt-table-header");
            return;
        }

        DynamicArgumentResult extendParams = richText.getExtendParams();

        // 动态表格映射动态文件唯一主键,所有动态表格都必须有这个参数,否则对表格对应的sheet页进行关联
        String unique = currentNode.attr(DATA_HZT_UNIQUEID);
        // 判断当前主键是否在动态文本参数中
        if (!extendParams.getPlaceholderMap().containsKey(unique)) {
            log.error("parseDynamicTable failed, dynamic text not contains data-hzt-uniqueid :{}", unique);
            return;
        }


        String sheetName = currentNode.attr(DATA_HZT_NAME);
        DynamicTextField dynamicTextField = extendParams.getPlaceholderMap().get(unique);

        String tableHeader = currentNode.attr(DATA_HZT_TABLE_HEADER);

        List<DynamicReferenceStructure> dynamicReferenceStructures = JSONObject.parseArray(tableHeader, DynamicReferenceStructure.class);

        richText.getExtendParams().incrementSequenceAndGet();

        for (int i = 0; i < dynamicReferenceStructures.size(); i++) {
            DynamicReferenceStructure referenceStructure = dynamicReferenceStructures.get(i);
            referenceStructure.setFontColor(PoiCommon.DEFAULT_STYLE.getColor());
            referenceStructure.setFontFamily(PoiCommon.DEFAULT_STYLE.getFontFamily());
            referenceStructure.setFontSize(PoiCommon.DEFAULT_STYLE.getFontSize());
            referenceStructure.setSheetName(sheetName);
            referenceStructure.setSheetNo(richText.getExtendParams().getSequence());
            referenceStructure.setColumnIndex(i);
        }

        DynamicReference dynamicReference = new DynamicReference();
        dynamicReference.setReferenceType(FileConstant.INT_0);
        dynamicReference.setSheetName(sheetName);
        dynamicReference.setSheetNo(richText.getExtendParams().getSequence());
//        dynamicReference.setReferencePlaceHolder(String.valueOf(extendParams.incrementSequenceAndGet()));
        dynamicReference.setReferencePlaceHolder(sheetName);
        // 目前表头字体大小以及表格内容字体大小均无前端传值,因此先给默认值
        dynamicReference.setTableTitleFontColor(PoiCommon.DEFAULT_STYLE.getColor());
        dynamicReference.setTableTitleFontFamily(PoiCommon.DEFAULT_STYLE.getFontFamily());
        dynamicReference.setTableTitleFontSize(PoiCommon.DEFAULT_STYLE.getFontSize());
        dynamicReference.setFontColor(PoiCommon.DEFAULT_STYLE.getColor());
        dynamicReference.setFontFamily(PoiCommon.DEFAULT_STYLE.getFontFamily());
        dynamicReference.setFontSize(PoiCommon.DEFAULT_STYLE.getFontSize());
        dynamicReference.setTemplateId(dynamicTextField.getTemplateId());
        dynamicReference.setReferenceStructureList(dynamicReferenceStructures);

        dynamicTextField.getReferenceList().add(dynamicReference);


        // 动态表格写入前后必换行
        richText.insertNewParagraph();

        String placeholderPrefix = "{{#";
        if (richText.isRepeatHeader()) {
            placeholderPrefix = "{{~";
        }

        TextRenderPolicy.Helper.renderTextRun(richText.createRun(), new TextRenderData(String.join(FileConstant.EMPTY_STR, placeholderPrefix, dynamicReference.getReferencePlaceHolder(), "}}"), PoiCommon.DEFAULT_STYLE));
        richText.createRun().addBreak();
        log.info("parseDynamicTable end");
    }

//    private void parseDynamicImage(RichText richText) {
//        //todo 目前富文本不包含动态图片
//        log.info("parseDynamicImage begin");
//
//        Node currentNode = richText.getCurrentNode();
//
//        if(!currentNode.hasAttr(DATA_HZT_NAME)){
//            log.error("parseDynamicImage failed,missing data-hzt-name argument");
//            return;
//        }
//        DynamicArgument extendParams = richText.getExtendParams();
//        String dynamicName = currentNode.attr(DATA_HZT_NAME);
//        // 判断当前主键是否在动态文本参数中
//        if(!extendParams.getPlaceholderMap().containsKey(dynamicName)){
//            log.error("parseDynamicImage failed, dynamic text not contains data-hzt-name :{}", dynamicName);
//            return;
//        }
//        BizTemplateField bizTemplateField = extendParams.getPlaceholderMap().get(dynamicName);
//        // 之前v1版本生成文件时,当前表对象既有动态图片,也有静态文件,目前模板绘制只包含动态图片
//        BizTemplateImage bizTemplateImage = new BizTemplateImage();
//
//        bizTemplateImage.setImageType(currentNode.attr(DATA_HZT_TYPE));
////        bizTemplateImage.setTemplateFieldId(bizTemplateField.getId());
//        bizTemplateImage.setTemplateId(extendParams.getTemplateId());
//        bizTemplateImage.setSource(FileConstant.INT_1);
//        bizTemplateImage.setImagePlaceHolder(String.valueOf(extendParams.incrementSequenceAndGet()));
//        // 设置图片高度和宽度
//        setImageStyle(currentNode.attr(DATA_HZT_STYLE), bizTemplateImage);
//
//        bizTemplateField.getTemplateImageList().add(bizTemplateImage);
////        extendParams.getBizTemplateImages().add(bizTemplateImage);
//
//        TextRenderPolicy.Helper.renderTextRun(richText.createRun(), new TextRenderData(String.join(FileConstant.EMPTY_STR, "{{@",bizTemplateImage.getImagePlaceHolder() , "}}")));
//    }

//    private void setImageStyle(String imageStyle, BizTemplateImage bizTemplateImage) {
//        // 解析高度和宽度
//        String[] styleAttr = imageStyle.split(PoiCommon.SEMICOLON);
//        for (String attr : styleAttr) {
//            if(attr.contains("width")){
//                bizTemplateImage.setFitWidth(attr.split(PoiCommon.COLON)[1].trim());
//            }
//            if(attr.contains("height")){
//                bizTemplateImage.setFitHeight(attr.split(PoiCommon.COLON)[1].trim());
//            }
//        }
//    }

    private void parseDynamicText(RichText richText) {
        log.info("parseDynamicText begin");
        Node currentNode = richText.getCurrentNode();

        if (!currentNode.hasAttr(DATA_HZT_NAME)) {
            log.error("parseDynamicText failed,missing data-hzt-name argument");
            return;
        }

        DynamicArgumentResult extendParams = richText.getExtendParams();


        // 判断是否已经有对应的动态参数被解析了
        Map<String, DynamicTextField> placeholderMap = extendParams.getPlaceholderMap();

        String dynamicName = currentNode.attr(DATA_HZT_NAME);

        if (!placeholderMap.containsKey(dynamicName)) {
            log.error("parseDynamicText failed, [{}] missing", dynamicName);
            return;
        }
        DynamicTextField dynamicTextField = placeholderMap.get(dynamicName);
        // 如果不存在,则构造动态参数数据库表对象
        dynamicTextField.setFieldName(dynamicName);
        // 占位符字段,本身无硬性要求，只要保证一个模板内的占位符不会重复即可
//        dynamicTextField.setFieldValue(String.valueOf(extendParams.incrementSequenceAndGet()));
        dynamicTextField.setFieldValue(dynamicName);
        dynamicTextField.setFieldType(FileConstant.INT_0);
        // 当前前端未传入此参数,直接默认给string类型
        dynamicTextField.setFieldValueType(FieldValueTypeEnum.STRING.getDesc());
        // 模板id,在解析模板时就生成了
        dynamicTextField.setTemplateId(extendParams.getTemplateId());
        // 解析动态参数样式字段
        TextFormatStyle textFormatStyle = new TextFormatStyle();
        if (currentNode.hasAttr(DATA_HZT_STYLE)) {
            JsoupUtils.parseStyle(currentNode.attr(DATA_HZT_STYLE), textFormatStyle);
        }
        // 目前仅支持字体颜色大小类型等参数定义
        dynamicTextField.setFontColor(textFormatStyle.getStyle().getColor());
        dynamicTextField.setFontFamily(textFormatStyle.getStyle().getFontFamily());
        dynamicTextField.setFontSize(textFormatStyle.getStyle().getFontSize());
//        extendParams.getBizTemplateFields().add(bizTemplateField);
        richText.setPreIsDynamicText(Boolean.TRUE);
        // 写出占位符到文本
        TextRenderPolicy.Helper.renderTextRun(richText.createRun(), new TextRenderData(String.join(FileConstant.EMPTY_STR, "{{", dynamicTextField.getFieldValue(), "}}"), textFormatStyle.getStyle()));
    }


}
