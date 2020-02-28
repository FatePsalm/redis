package com.wemew.rediscache.enums;

public enum RedisSoure {
    REDISONE(1);//第一个redis
    private int soure;

    RedisSoure(int soure) {
        this.soure = soure;
    }

    public int getSoure() {
        return soure;
    }

    public void setSoure(int soure) {
        this.soure = soure;
    }
}
