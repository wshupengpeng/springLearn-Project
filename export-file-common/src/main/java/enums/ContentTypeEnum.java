package enums;

/**
 * @description: ContentType 枚举
 * @author: 01415355
 * @createDate: 2021-10-09 16:42
 * @version: 1.0
 * @todo:
 */
public enum ContentTypeEnum {
    DOC("doc","微软Word文件","application/msword"),

    DOCX("docx","Microsoft Word（OpenXML）","application/vnd.openxmlformats-officedocument.wordprocessingml.document"),

    GZ("gz","GZip压缩档案","application/gzip"),

    GIF("gif","图形交换格式（GIF）","image/gif"),

    HTM("htm","超文本标记语言（HTML）","text/html"),

    HTML("html","超文本标记语言（HTML）","text/html"),

    JPEG("jpeg","JPEG图像","image/jpeg"),

    JPG("jpg","JPEG图像","image/jpeg"),

    JSON("json","JSON格式","application/json"),

    MP3("mp3","MP3音频","audio/mpeg"),

    PNG("png","便携式网络图形","image/png"),

    PDF("pdf","Adobe 可移植文档格式（PDF）","application/pdf"),

    PPT("ppt","Microsoft PowerPoint","application/vnd.ms-powerpoint"),

    PPTX("pptx","Microsoft PowerPoint（OpenXML）","application/vnd.openxmlformats-officedocument.presentationml.presentation"),

    RAR("rar","RAR档案","application/vnd.rar"),

    TXT("txt","文本（通常为ASCII或ISO 8859- n","text/plain"),

    WEBP("webp","WEBP图像","image/webp"),

    XLS("xls","微软Excel","application/vnd.ms-excel"),

    XML("xml","XML","application/xml"),

    ZIP("zip","ZIP","application/zip"),

    MIME_7Z("7z","7-zip存档","application/x-7z-compressed")
    ;
    private String extension;

    private String explain;

    private String mimeType;

    ContentTypeEnum(String extension, String explain, String mimeType) {
        this.extension = extension;
        this.explain = explain;
        this.mimeType = mimeType;
    }

    public String getExtension() {
        return extension;
    }

    public String getExplain() {
        return explain;
    }

    public String getMimeType() {
        return mimeType;
    }
}
