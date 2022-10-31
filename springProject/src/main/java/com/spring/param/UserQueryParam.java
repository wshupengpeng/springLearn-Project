package com.spring.param;

import com.spring.excel.annotation.ExportSubSelection;
import com.spring.excel.enums.SubSelectionEnum;
import lombok.Data;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/19 17:21
 */
@Data
public class UserQueryParam {

    @ExportSubSelection(subselection = SubSelectionEnum.PAGE_NO, defaultValue = 1)
    private Long pageNo;

    @ExportSubSelection(subselection = SubSelectionEnum.PAGE_SIZE, defaultValue = 10)
    private Long pageSize;


}
