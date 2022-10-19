package com.spring.service;

import com.spring.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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

    List<User> queryList();

}
