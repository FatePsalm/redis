package com.wemew.rediscache.service.parameter;

import com.wemew.rediscache.model.RedisOpsParameter;
import com.wemew.rediscache.service.RedisOpsParameterService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserRedisParament extends RedisOpsParameter implements RedisOpsParameterService  {

    public String set() {
        Object[] userParament = this.getPar(this.getClass());
        /*RedisUtils redisUtils = SpringUtil.getObject(RedisUtils.class);
        userParament[1] = redisUtils.redisList().toString();*/
        getMap().put(UserRedisParament.class, Arrays.asList(userParament));


        AdminRedisParament adminRedisParament = new AdminRedisParament();
        adminRedisParament.setPars(getPars());
        adminRedisParament.set();
        Map<Class<?>, List<Object>> map = adminRedisParament.getMap();
        getMap().putAll(map);

        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }

}
