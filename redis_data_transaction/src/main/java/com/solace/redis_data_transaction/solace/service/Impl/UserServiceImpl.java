package com.solace.redis_data_transaction.solace.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.solace.redis_data_transaction.solace.beans.User;
import com.solace.redis_data_transaction.solace.mapper.UserMapper;
import com.solace.redis_data_transaction.solace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CaoGang
 * @since 2019-04-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate2;
    @Override
    @Transactional
    public boolean saveOrUpdate(User entity) {
        String key = entity.getUserName();
        String value = entity.getPassword();
        stringRedisTemplate.opsForValue().set(entity.getId(),key+":"+value);
        stringRedisTemplate2.opsForValue().set(entity.getId(),key+":"+value);
        System.out.println("执行了");
        return super.saveOrUpdate(entity);
    }
}
