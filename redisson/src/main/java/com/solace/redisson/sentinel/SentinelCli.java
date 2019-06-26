package com.solace.redisson.sentinel;

import org.redisson.config.Config;
/*
即sentinel模式，实现代码和单机模式几乎一样，唯一的不同就是Config的构造：
 */
public class SentinelCli {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSentinelServers().addSentinelAddress(
                "redis://172.29.3.245:26378","redis://172.29.3.245:26379", "redis://172.29.3.245:26380")
                .setMasterName("mymaster")
                .setPassword("a123456").setDatabase(0);
    }
}
