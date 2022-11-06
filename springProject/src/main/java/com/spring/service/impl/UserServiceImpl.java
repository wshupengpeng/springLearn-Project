package com.spring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.entity.User;
import com.spring.excel.annotation.ExportExcel;
import com.spring.excel.annotation.ExportSubSelection;
import com.spring.excel.enums.ExportModeEnum;
import com.spring.excel.enums.SubSelectionEnum;
import com.spring.mapper.UserMapper;
import com.spring.param.NormalUserQueryParam;
import com.spring.param.UserQueryParam;
import com.spring.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hpp
 * @since 2022-09-15
 */
@Service
@Primary
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @ExportExcel(beanClass = User.class, fileName = "文件导出", limit = 10)
    public List<User> queryList(NormalUserQueryParam queryParam){
        IPage page = new Page(queryParam.getPageNo(),queryParam.getPageSize());
        return baseMapper.selectPage(page,new QueryWrapper<User>()).getRecords();
    }

    @ExportExcel(beanClass = User.class, fileName = "文件导出", mode = ExportModeEnum.SUBSELECTION, limit = 40)
    @Override
    public List<User> querySubList(UserQueryParam queryParam){
        IPage page = new Page(queryParam.getPageNo(),queryParam.getPageSize());
        return baseMapper.selectPage(page,new QueryWrapper<User>()).getRecords();
    }

    @Override
    @ExportExcel(beanClass = User.class, fileName = "文件导出", limit = 30)
    public List<User> queryList(@ExportSubSelection(subselection = SubSelectionEnum.PAGE_NO,defaultValue = 1) Long pageNo,
                                @ExportSubSelection(subselection = SubSelectionEnum.PAGE_SIZE,defaultValue = 10)Long pageSize) {
        IPage page = new Page(pageNo,pageSize);
        return baseMapper.selectPage(page,new QueryWrapper<User>()).getRecords();
    }

    @Override
    @ExportExcel(beanClass = User.class, fileName = "文件导出", mode = ExportModeEnum.SUBSELECTION, limit = 40)
    public List<User> querySubList(@ExportSubSelection(subselection = SubSelectionEnum.PAGE_NO,defaultValue = 1) Long pageNo,
                                   @ExportSubSelection(subselection = SubSelectionEnum.PAGE_SIZE,defaultValue = 20)Long pageSize) {
        IPage page = new Page(pageNo,pageSize);
        return baseMapper.selectPage(page,new QueryWrapper<User>()).getRecords();
    }
}
