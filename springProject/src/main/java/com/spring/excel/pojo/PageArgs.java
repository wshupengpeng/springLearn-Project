package com.spring.excel.pojo;

import com.spring.excel.enums.SubSelectionEnum;
import com.spring.excel.exceptions.ExcelCommonException;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/14 14:22
 */
@Data
public class PageArgs {

    private Object[] args;

    private List<PageDefinition> pageDefinitions = new ArrayList<>();


    public void setPage(Integer page, SubSelectionEnum subSelectionEnum) {
        PageDefinition pageDefinition = getDefinition(subSelectionEnum);
        pageDefinition.setValue(page);
    }

    public pageDefinition getPage(SubSelectionEnum subSelectionEnum) {
        return getDefinition(subSelectionEnum);
    }


    private PageDefinition getDefinition(SubSelectionEnum subSelectionEnum){
        return pageDefinitions.stream()
                .filter(pd -> pd.subSelectionEnum == subSelectionEnum)
                .findFirst()
                .orElseThrow(() -> new ExcelCommonException("缺少分页参数"));
    }

    private void setObjPage(PageDefinition pageDefinition){
        pageDefinition.getField().setAccessible(true);
        try {
            pageDefinition.getField().set(args[pageDefinition.mark], pageDefinition.value);
        } catch (IllegalAccessException e) {
            throw new ExcelCommonException("设置分页参数失败",e);
        }
    }
    public void addPageDefinition(PageDefinition pd){
        pageDefinitions.add(pd);
    }

    public Object[] buildPage(){
        pageDefinitions.forEach((bd)->{
            if(bd.isObj){
                setObjPage(bd);
            }else{
                args[bd.mark] = bd.getValue();
            }
        });
        return args;
    }

    @Data
    public static class PageDefinition{
        private Field field;
        private boolean isObj;
        private int mark;
        private SubSelectionEnum subSelectionEnum;
        private Integer value;
    }
}
