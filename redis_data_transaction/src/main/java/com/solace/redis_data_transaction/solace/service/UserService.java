package com.solace.redis_data_transaction.solace.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.solace.redis_data_transaction.solace.beans.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CaoGang
 * @since 2019-04-20
 */
public interface UserService extends IService<User> {
    @Override
    boolean saveOrUpdate(User user);
}
