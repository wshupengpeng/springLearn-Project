package com.sf.vsolution.hx.hanzt.template.html.param;

import lombok.Data;

/**
 * @Description: 单元格合并记录
 * @Author 01415355
 * @Date 2023/3/17 11:10
 */
@Data
public class CellMergeRecord {

    private Integer rowStartIndex;

    private Integer rowEndIndex;

    private Integer colStartIndex;

    private Integer colEndIndex;

}
