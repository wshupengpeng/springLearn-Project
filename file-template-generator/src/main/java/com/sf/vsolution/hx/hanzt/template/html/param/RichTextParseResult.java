package com.sf.vsolution.hx.hanzt.template.html.param;

//import com.sf.vsolution.hx.dto.obs.ObsResponseDto;
import lombok.Data;

/**
 * @Description
 * @Author 01415355
 * @Date 2023/6/7 16:44
 */
@Data
public class RichTextParseResult<T> {

    /**
     * 解析的动态参数名称
     */
    private DynamicArgumentResult extendParams;

    /**
     *  解析结果
     */
    private Boolean isSuccess = Boolean.TRUE;

    /**
     *  错误原因
     */
    private String errorMsg;

    /**
     *  obs 上传响应结果
     */
//    private ObsResponseDto obsResponseDto;

    /**
     *  响应结果封装
     */
    private T result;


}
