package com.wemew.rediscache.enums;

import com.wemew.rediscache.model.RedisOpsParameter;
import com.wemew.rediscache.service.RedisOpsParameterService;
import com.wemew.rediscache.service.parameter.AdminRedisParament;
import com.wemew.rediscache.service.parameter.UserRedisParament;

public enum RedisModel {
    管理(2,"admin:id:{{userid}}", AdminRedisParament.class,  RedisOps.opsForValue.getOps(), 0,RedisSoure.REDISONE.getSoure(),"管理员信息缓存!"),

    用户(1,"user:id:{{userid}}", UserRedisParament.class, RedisOps.opsForValue.getOps(), 0,RedisSoure.REDISONE.getSoure(),"用户信息缓存!"),
    ;
    private int index;//下标
    private String key;//值
    private Class<?> className;//参数解析器关联的类
    private String type;//类型
    private long time;//持续时间
    private int redisSource ;//redis链接
    private String text;//备注

    RedisModel(int index, String key, Class<?> className, String type, long time, int redisSource, String text) {
        this.index = index;
        this.key = key;
        this.className = className;
        this.type = type;
        this.time = time;
        this.redisSource = redisSource;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Class<?> getClassName() {
        return className;
    }

    public void setClassName(Class<?> className) {
        this.className = className;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getRedisSource() {
        return redisSource;
    }

    public void setRedisSource(int redisSource) {
        this.redisSource = redisSource;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "RedisModel{" +
                "index=" + index +
                ", key='" + key + '\'' +
                ", className=" + className +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", redisSource=" + redisSource +
                ", text='" + text + '\'' +
                '}';
    }

    public static RedisModel getClassName(RedisOpsParameterService redisOpsParameterService){
        if (redisOpsParameterService!=null) {
            RedisModel[] values = RedisModel.values();
            for (RedisModel value : values) {
                Class<?> className = value.getClassName();
                if (redisOpsParameterService.getClass()==className) {
                    RedisOpsParameter service = (RedisOpsParameter) redisOpsParameterService;
                    service.setModel(value);
                    return value;
                }
            }
        }
        return null;
    }
    public static RedisModel findIndex(Integer indexType){
        if (indexType!=null) {
            RedisModel[] values = RedisModel.values();
            for (RedisModel value : values) {
                int index = value.getIndex();
                if (index==indexType)
                    return value;
            }
        }
        return null;
    }
}
