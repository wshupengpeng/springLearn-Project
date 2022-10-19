package com.spring.test;

import com.spring.entity.ResponseResult;
import com.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 *  excel导出测试
 * @Author 01415355
 * @Date 2022/10/19 16:46
 */
@RestController
@RequestMapping("/excel")
public class ExcelTestController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/normal/export")
    public ResponseResult excelExportNormal(){
        iUserService.queryList();
        return ResponseResult.ok();
    }
}
