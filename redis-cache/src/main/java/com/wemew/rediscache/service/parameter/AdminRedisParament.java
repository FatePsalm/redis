package com.wemew.rediscache.service.parameter;

import com.wemew.rediscache.model.RedisOpsParameter;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class AdminRedisParament extends RedisOpsParameter {
    public RedisOpsParameter delete() {
        Object[] adminParament = this.getPar(AdminRedisParament.class);
        this.setMap(AdminRedisParament.class, adminParament == null ? null : Arrays.asList(adminParament));

        return this.getRedisOpsParameter(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    public RedisOpsParameter set() {
        Object[] adminParament = this.getPar(AdminRedisParament.class);
        this.setMap(AdminRedisParament.class, Arrays.asList(adminParament[0], adminParament[1], 1L, TimeUnit.HOURS));

        return this.getRedisOpsParameter(Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
