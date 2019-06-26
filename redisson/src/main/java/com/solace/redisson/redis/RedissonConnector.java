package com.solace.redisson.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 获取RedissonClient连接类
 */
@Component
public class RedissonConnector {
    RedissonClient redisson;
    @PostConstruct
    public void init(){
       /* RedissonClient redissonClient = Redisson.create();
        redisson = Redisson.create();*/
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.186.130:6379").setPassword("123456").setDatabase(0);
        redisson= Redisson.create(config);
    }

    public RedissonClient getClient(){
        return redisson;
    }

}