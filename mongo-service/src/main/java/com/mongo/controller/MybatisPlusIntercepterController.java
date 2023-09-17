package com.mongo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mongo.dto.R;
import com.mongo.entity.User;
import com.mongo.mapper.UserMapper;
import com.mongo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @creater hpp
 * @Date 2023/8/21-20:24
 * @description:
 */
@RestController
public class MybatisPlusIntercepterController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/selectTestMybatisPlusIntercepter")
    public R<User> testMybatisPlusIntercepter(@RequestParam("id") Integer id, @RequestParam("userName") String userName) {
//        User user = userMapper.selectDiffById(id);
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, id).eq(User::getUserName, userName));
        return R.success(user);
    }


    @RequestMapping("/insertTestMybatisPlusIntercepter")
    @Transactional(transactionManager = "customizeTransactionManager")
    public R<User> insertTestMybatisPlusIntercepter(@RequestParam("id") Long id,
                                                    @RequestParam("needException") boolean needException) {
        User user = new User();
        user.setId(id);
        user.setUserName(String.valueOf(id));
        user.setRemark("remark");
        userMapper.insert(user);
        if (needException) {
            int i = 1 / 0;
        }
        return R.success();
    }

    @RequestMapping("/deleteTestMybatisPlusIntercepter")
    @Transactional
    public R<User> deleteTestMybatisPlusIntercepter(@RequestParam("id") Long id) {
        userMapper.deleteById(id);
        return R.success();
    }

    @RequestMapping("/updateTestMybatisPlusIntercepter")
    @Transactional
    public R<User> updateTestMybatisPlusIntercepter(@RequestBody User user) {
        userMapper.updateById(user);
        return R.success();
    }


    @RequestMapping("/test/timeout")
    @Transactional
    public R timeOutTest(@RequestParam("timeOut") Long timeOut) {

        try {
            Thread.sleep(timeOut);
        }catch (Exception e){
            e.printStackTrace();
        }

        return R.success();
    }




    @RequestMapping("/updateBatchTestMybatisPlusIntercepter")
    @Transactional
    public R<User> updateBatchTestMybatisPlusIntercepter(@RequestBody List<User> userList) {
        iUserService.saveOrUpdateBatch(userList);
//        userMapper.updateBatchById(userList);
        return R.success();
    }
}
