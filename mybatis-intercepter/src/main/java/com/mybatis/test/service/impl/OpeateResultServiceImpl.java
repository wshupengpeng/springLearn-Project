package com.mybatis.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.test.entity.OpeateResult;
import com.mybatis.test.mapper.OpeateResultMapper;
import com.mybatis.test.service.IOpeateResultService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hpp
 * @since 2023-08-22
 */
@Service
public class OpeateResultServiceImpl extends ServiceImpl<OpeateResultMapper, OpeateResult> implements IOpeateResultService {

}
