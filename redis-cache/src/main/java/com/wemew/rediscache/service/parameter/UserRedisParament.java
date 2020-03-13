package com.wemew.rediscache.service.parameter;

import com.wemew.rediscache.model.RedisOpsParameter;

import java.util.Arrays;

public class UserRedisParament extends RedisOpsParameter  {
    public RedisOpsParameter delete() {
        Object[] userParament = this.getPar(this.getClass());
        /*RedisUtils redisUtils = SpringUtil.getObject(RedisUtils.class);
        userParament[1] = redisUtils.redisList().toString();*/
        RedisOpsParameter redisOpsParameter = this.setMap(UserRedisParament.class, Arrays.asList(userParament));


        AdminRedisParament adminRedisParament = new AdminRedisParament();
        adminRedisParament.setRedisOpsParameter(redisOpsParameter);
        redisOpsParameter.setRedisOpsParameter(adminRedisParament.delete());

        return redisOpsParameter.getRedisOpsParameter(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public RedisOpsParameter set() {
        Object[] userParament = this.getPar(this.getClass());
        /*RedisUtils redisUtils = SpringUtil.getObject(RedisUtils.class);
        userParament[1] = redisUtils.redisList().toString();*/
        RedisOpsParameter redisOpsParameter = this.setMap(UserRedisParament.class, Arrays.asList(userParament));


        AdminRedisParament adminRedisParament = new AdminRedisParament();
        adminRedisParament.setRedisOpsParameter(redisOpsParameter);
        redisOpsParameter.setRedisOpsParameter(adminRedisParament.set());

        return this.getRedisOpsParameter(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

}
