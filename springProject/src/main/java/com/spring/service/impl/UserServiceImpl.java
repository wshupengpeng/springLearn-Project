package com.spring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.entity.User;
import com.spring.excel.annotation.ExportExcel;
import com.spring.excel.annotation.ExportSubSelection;
import com.spring.excel.enums.ExportModeEnum;
import com.spring.mapper.UserMapper;
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

    @ExportExcel(beanClass = User.class,fileName = "文件导出")
    public List<User> queryList(UserQueryParam queryParam){
        IPage page = new Page(queryParam.getPageNo(),queryParam.getPageSize());
        return baseMapper.selectPage(page,new QueryWrapper<User>()).getRecords();
    }

    @ExportExcel(beanClass = User.class,fileName = "文件导出",mode = ExportModeEnum.SUBSELECTION)
    @Override
    public List<User> querySubList(UserQueryParam queryParam){
        IPage page = new Page(queryParam.getPageNo(),queryParam.getPageSize());
        return baseMapper.selectPage(page,new QueryWrapper<User>()).getRecords();
    }
}
