package com.mybatis.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatis.test.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hpp
 * @since 2022-09-15
 */
public interface IUserService extends IService<User> {
    List<User> queryList(Long pageNo, Long pageSize);

    List<User> querySubList(Long pageNo, Long pageSize);

    void update(User user);
}
