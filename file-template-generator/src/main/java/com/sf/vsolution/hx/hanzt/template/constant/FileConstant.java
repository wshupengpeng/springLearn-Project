package com.sf.vsolution.hx.hanzt.template.constant;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/7/13 11:34
 */
public class FileConstant {

    public static final String EMPTY_STR = "";

    public static final int INT_0 = 0;
    public static final int INT_1 = 1;
    public static final String SEPARATOR_UNDERLINE = "_";

    public static final String DOC_SUFFIX = ".doc";

    public static final String DOCX_SUFFIX = ".docx";

    public static final String PDF_SUFFIX = ".pdf";

    public static final String PNG_SUFFIX = ".png";

    public static final String JPG_SUFFIX = ".jpg";

    public static final String SEVEN_ZIP_SUFFIX = ".7z";

    /** 传参不规范,code：400*/
    public static final Integer PARAM_INCORRECT_CODE = 400;

    /** 成功,code：200*/
    public static final Integer SUCCESS_CODE = 200;

    /** 服务内部调用失败,code：500*/
    public static final Integer SERVER_ERROR_CODE = 500;

    /** 登录失效,code：401*/
    public static final Integer AUTH_FAIL_CODE = 401;

    /** 无对应接口权限,code：402*/
    public static final Integer HAVE_NOT_PERMISSION_CODE = 402;

    /** 操作无记录,code：403*/
    public static final Integer NO_RECORD_OPERATION = 403;

    /**
     * 服务调用超时
     */
    public static final String ERR_SERVER_CONNECT_OVERTIME = "服务调用超时！";

    public static final String ERR_SERVER_CONNECT_REQUESTDATA = "远程服务数据超时!";

    public static final String ERR_SERVER  = "服务调用异常";

    public static final String HTTP_REGEX = "^(?i)(http|https)://.*$";

    public static final Integer HTTP_TIME_OUT = 5000;

    /** 一联单临时文件前缀名 **/
    public static final String IMAGE_TEMP_PREFIX = "temp";
}
