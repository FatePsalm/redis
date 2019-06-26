package com.solace.redisson.myRedissonCli;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLock {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.186.130:6379").setPassword("123456").setDatabase(1);
        // 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);
        for (int i=0;i<5;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 设置锁定资源名称
                    RLock disLock = redissonClient.getLock("DISLOCK");
                    boolean isLock;
                    String name = Thread.currentThread().getName();
                    try {
                        //尝试获取分布式锁
                        /*
                        等待时间
                        租约时间
                        时间单位
                         */
                        disLock.lock();
                       // isLock = disLock.tryLock(500, 15000, TimeUnit.MILLISECONDS);
                        System.out.println("执行:" + this.getClass().getSimpleName()+ ":" + new Exception().getStackTrace()[0].getMethodName());
                        System.out.println("线程名字:" + name);
                       // if (isLock) {
                            //TODO if get lock success, do something;
                            System.out.println(name + ":获取到锁,执行业务逻辑");
                            Thread.sleep(100);
                       /* }else {
                            System.out.println(name + ":没有获取到锁");
                        }*/
                    } catch (Exception e) {
                    } finally {
                        // 无论如何, 最后都要解锁
                        disLock.unlock();
                        System.out.println(name + "释放锁");
                    }
                }
            }).start();
        }
    }
}
