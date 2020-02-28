package com.wemew.rediscache.enums;

public enum RedisType {
    ROOT(0),
    SUBCLASS(1),;
    private int type;

    RedisType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
