package com.spring.test;

import com.spring.entity.ResponseResult;
import com.spring.param.NormalUserQueryParam;
import com.spring.param.UserQueryParam;
import com.spring.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 前端控制导出数据分页大小,但是是根据后端配置最大limit生效
     * @param queryParam
     */
    @RequestMapping("/normal/export")
    public void excelExportNormal(@RequestBody NormalUserQueryParam queryParam){
        iUserService.queryList(queryParam);
    }


    /**
     * 后端控制导出数据大小
     * @param queryParam
     */
    @RequestMapping("/sub/export")
    public void excelExportSub(@RequestBody UserQueryParam queryParam){
        iUserService.querySubList(queryParam);
    }



    /**
     * 前端控制导出数据分页大小,但是是根据后端配置最大limit生效
     */
    @RequestMapping("/normal/exportByRequestParam")
    public void excelExportNormalByRequestParam(){
        iUserService.queryList(null,null);
    }


    /**
     * 后端控制导出数据大小
     */
    @RequestMapping("/sub/exportByRequestParam")
    public void excelExportSubByRequestParam(){
        iUserService.querySubList(null,null);
    }
}
