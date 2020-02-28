package com.wemew.rediscache.enums;

public enum  RedisOps {
    opsForZSet("opsForZSet"),
    opsForValue("opsForValue"),
    opsForGeo("opsForGeo"),
    opsForCluster("opsForCluster"),
    opsForHyperLogLog("opsForHyperLogLog"),
    opsForSet("opsForSet"),
    opsForHash("opsForHash"),
    opsForStream("opsForStream"),
    opsForList("opsForList"),
    ;
    private String ops;

    RedisOps(String ops) {
        this.ops = ops;
    }

    public String getOps() {
        return ops;
    }

    public void setOps(String ops) {
        this.ops = ops;
    }
}
