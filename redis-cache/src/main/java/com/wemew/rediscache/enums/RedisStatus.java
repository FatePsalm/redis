package com.wemew.rediscache.enums;

public enum RedisStatus {
    STOP(0),//禁用
    UP(1),//启用
    ;
    private int status;

    RedisStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
