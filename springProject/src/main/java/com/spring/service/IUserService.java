package com.spring.service;

import com.spring.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.param.NormalUserQueryParam;
import com.spring.param.UserQueryParam;

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

    List<User> queryList(NormalUserQueryParam queryParam);

    List<User> querySubList(UserQueryParam queryParam);

    List<User> queryList(Long pageNo, Long pageSize);

    List<User> querySubList(Long pageNo, Long pageSize);
}
