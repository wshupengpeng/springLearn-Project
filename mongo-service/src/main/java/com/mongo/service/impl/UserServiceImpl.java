package com.mongo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mongo.entity.User;
import com.mongo.mapper.UserMapper;
import com.mongo.service.IUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hpp
 * @since 2022-09-15
 */
@Service
@Primary
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public List<User> queryList(Long pageNo, Long pageSize) {
        IPage page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, new QueryWrapper<User>()).getRecords();
    }

    @Override
    public List<User> querySubList(Long pageNo, Long pageSize) {
        IPage page = new Page(pageNo, pageSize);
        return baseMapper.selectPage(page, new QueryWrapper<User>()).getRecords();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(User user) {
        updateById(user);
    }
}
