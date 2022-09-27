package com.spring.mapper;

import com.spring.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hpp
 * @since 2022-09-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
