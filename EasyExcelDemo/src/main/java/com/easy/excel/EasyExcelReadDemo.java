package com.easy.excel;

import com.alibaba.excel.EasyExcel;
import com.easy.excel.customize.CustomStringStringConverter;
import com.easy.excel.listener.BaseListener;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void writeFile(){
        String filePath = "d://easyExcelDemo.xlsx";
        try {
            // 合并行
//            List<List<String>> head = new ArrayList<>();
//            head.add(Arrays.asList("表头1","表头1"));
//            head.add(Arrays.asList("表头2","表头3"));
            // 合并列
            List<List<String>> head = new ArrayList<>();
            head.add(Arrays.asList("表头1","表头2"));
            head.add(Arrays.asList("表头1","表头3"));

            EasyExcel.write(new FileOutputStream(filePath))
//                    .registerReadListener(new BaseListener())
//                    .registerConverter(new CustomStringStringConverter())
//                    .head(Arrays.asList("表头1","表头2").stream().map(Arrays::asList).collect(Collectors.toList()))
                    .head(head)
                    .setAutoTrim(false)
                    .sheet().doWrite(Arrays.asList(" 开头空格","中间 空格","结尾空格 ").stream().map(Arrays::asList).collect(Collectors.toList()));
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }


}
