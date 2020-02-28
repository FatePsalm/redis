package com.wemew.rediscache.service.parameter;

import com.wemew.rediscache.model.RedisOpsParameter;
import com.wemew.rediscache.service.RedisOpsParameterService;

import java.util.Arrays;


public class AdminRedisParament extends RedisOpsParameter implements RedisOpsParameterService {

    public String set() {
        Object[] adminParament = this.getPar(AdminRedisParament.class);
        getMap().put(AdminRedisParament.class, Arrays.asList(adminParament));

        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }
}
