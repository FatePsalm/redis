package com.wemew.rediscache.model;

import com.wemew.rediscache.enums.RedisModel;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者 CG
 * 时间 2020/2/26 13:58
 * 注释 redis参数生成器
 */
public class RedisOpsParameter {
    String methodName;
    RedisModel redisModel;//keymodel
    Map<Class<?>, Object[]> par = new HashMap<>();//参数列表
    Map<Class<?>, List<Object>> map = new HashMap<>();//封装redis执行参数列表

    public Object[] getPar(Class<?> name) {
        return par.get(name);
    }

    public Map<Class<?>, Object[]> getPars() {
        return par;
    }

    public RedisOpsParameter setPar(Class<?> name, Object... par) {
        this.par.put(name, par);
        return this;
    }

    public Map<Class<?>, List<Object>> getMap() {
        return map;
    }

    public RedisOpsParameter setMap(Class<?> className, List<Object> list) {
        this.map.put(className, list);
        return this;
    }

    public RedisModel getModel() {
        return redisModel;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setModel(RedisModel redisModel) {

        this.redisModel = redisModel;
    }

    public RedisOpsParameter getRedisOpsParameter(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public void setRedisOpsParameter(RedisOpsParameter redisOpsParameter) {
        if (redisOpsParameter != null) {
            if (StringUtils.isNotBlank(redisOpsParameter.getMethodName()))
                this.methodName = redisOpsParameter.getMethodName();
            if (redisOpsParameter.getPars() != null)
                this.par.putAll(redisOpsParameter.getPars());
            if (redisOpsParameter.getMap() != null)
                this.map.putAll(redisOpsParameter.getMap());
        }
    }
}
