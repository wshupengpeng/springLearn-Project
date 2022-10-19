package com.spring.service.impl;

import com.spring.entity.User;
import com.spring.mapper.UserMapper;
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

    public List<User> queryList(){
        return Collections.EMPTY_LIST;
    }
}
