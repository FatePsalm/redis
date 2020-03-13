package com.wemew.rediscache.service;

import com.wemew.rediscache.model.RedisOpsParameter;
import org.springframework.data.redis.core.RedisTemplate;

public interface RedisOpsService {
    /**
     * 作者 CG
     * 时间 2020/2/25 14:28
     * 注释 执行方法(事务操作方法)
     */
    Object ops(RedisTemplate redisTemplate, RedisOpsParameter redisOpsParameter) ;
     /**
       * 作者 CG
       * 时间 2020/2/28 17:47
       * 注释 开启事务
       */
    Object opsOpenTransaction(RedisOpsParameter redisOpsParameter) ;
     /**
       * 作者 CG
       * 时间 2020/2/28 17:47
       * 注释 不开启事务
       */
     Object opsNoTransaction(RedisOpsParameter redisOpsParameter) ;
}
