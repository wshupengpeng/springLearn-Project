package com.mybatis.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatis.test.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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


    User selectDiffById(@RequestParam("id") Integer id);

    int updateBatchById(List<User> list);

}
