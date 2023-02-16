package com.easy.excel;

import com.alibaba.excel.EasyExcel;
import com.easy.excel.customize.CustomStringStringConverter;
import com.easy.excel.listener.BaseListener;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * @Description: easyExceldemo启动类
 * @Author 01415355
 * @Date 2023/2/8 14:47
 */
public class EasyExcelReadDemo {

    @Test
    public void readFile(){
        String filePath = "d://easyExcelDemo.xlsx";
        try {
            EasyExcel.read(new File(filePath))
                    .registerReadListener(new BaseListener())
                    .registerConverter(new CustomStringStringConverter())
                    .sheet(0).doRead();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

}
