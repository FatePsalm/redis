package com.wemew.rediscache.service;

import com.wemew.rediscache.enums.RedisModel;
import com.wemew.rediscache.model.RedisOpsParameter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

public interface RedisOpsRelevancyService {
     /**
       * 作者 CG
       * 时间 2020/2/26 10:21
       * 注释 操作关联关系
       */
     void relevancy(RedisTemplate redisTemplate, RedisOpsParameter redisOpsParameter);

    /**
     * 作者 CG
     * 时间 2020/2/25 18:01
     * 注释 如果key里面有参数 则默认把第一个参数替换
     */
    Object[] parameters(RedisModel redisModel, Map<Class<?>, List<Object>> map);

}
