package com.solace.redis_data_transaction.solace.controller;


import com.solace.redis_data_transaction.solace.beans.User;
import com.solace.redis_data_transaction.solace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CaoGang
 * @since 2019-04-20
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("test")
    public String test(String key,String value){
        User user = new User();
        user.setId("1");
        user.setUserName(key);
        user.setPassword(value);
        boolean b = userService.saveOrUpdate(user);
        return String.valueOf(b);
    }
}

