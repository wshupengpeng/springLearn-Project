package poi.handler.enums;

/**
 * @Description: 定义当前可以解析的标签类型
 * @Author 01415355
 * @Date 2023/3/2 18:13
 */
public enum TagEnum {
    P_TAG("p","正文标签"),
    SPAN_TAG("span","正文标签"),
    SUB_TAG("sub","上标文本标签"),
    EM_TAG("em","斜体标签"),
    IMAGE_TAG("image","图片标签"),
    TABLE_TAG("table","表格标签")
    ;

    TagEnum(String tagName, String desc) {
        this.tagName = tagName;
        this.desc = desc;
    }

    private String tagName;
    private String desc;
}
