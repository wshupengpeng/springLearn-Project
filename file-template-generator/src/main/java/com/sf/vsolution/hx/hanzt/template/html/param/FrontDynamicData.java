package com.sf.vsolution.hx.hanzt.template.html.param;

import lombok.*;

/**
 * @Description: 前端入参动态参数
 * @Author 01415355
 * @Date 2023/5/5 17:43
 */
@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FrontDynamicData {

    /**
     *  动态参数类型:文本、图片、表格
     */
    private String paramType;

    /**
     *  参数名称或图片名称或表格名称
     */
    private String paramName;
}
