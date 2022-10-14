package com.spring.excel.pojo;

import javax.management.relation.RoleUnresolved;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author 01415355
 * @Date 2022/10/14 14:22
 */
public class PageArgs {

    private Object[] args;

    private int index;

    private Method method;

    private boolean isObj;


    public void setPage(Integer page){
        if(isObj){
            setObjPage(page);
        }else{
            args[index] = page;
        }
    }

    private void setObjPage(Integer page){
        try {
            method.invoke(args[index], page);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("设置分页参数失败");
        }
    }


    public Object[] getArgs(){
        return args;
    }

}
