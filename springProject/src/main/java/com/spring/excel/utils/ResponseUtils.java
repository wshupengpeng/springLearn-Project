package com.spring.excel.utils;

import com.spring.excel.enums.ContentTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/14 11:58
 */
public class ResponseUtils {
    private static final String DEFAULT_UTF_8 = "utf-8";
    /**
     * 设置excel 响应头
     * @param response
     * @param fileName
     */
    public static void setExcelResponseHead(HttpServletResponse response, String fileName){
        setResponseHead(response,DEFAULT_UTF_8,fileName, ContentTypeEnum.XLS);
    }


    /**
     *  设置响应头
     * @param response 响应结果
     * @param encoding  编码
     * @param fileName  文件名称
     * @param contenyTypeEnum contenType枚举
     */
    public static void setResponseHead(HttpServletResponse response,String encoding
            ,String fileName,ContentTypeEnum contenyTypeEnum){
        response.setContentType(contenyTypeEnum.getMimeType());
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition");
        response.setCharacterEncoding(encoding);
        try {
            String encoder = URLEncoder.encode(fileName, encoding);
            response.setHeader("Content-disposition", "attachment;filename=" + encoder + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
