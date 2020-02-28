package com.wemew.rediscache.model;

import com.wemew.rediscache.enums.RedisModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
   * 作者 CG
   * 时间 2020/2/26 13:58
   * 注释 redis参数生成器
   */
public class RedisOpsParameter {
    RedisModel redisModel;//keymodel
    Map<Class<?>, Object[]> par = new HashMap<>();//参数列表
    Map<Class<?>, List<Object>> map = new HashMap<>();//封装redis执行参数列表

    public  Object[] getPar(Class<?> name) {
        return par.get(name);
    }
    public  Map<Class<?>, Object[]>  getPars() {
        return par;
    }
    public void setPar(Class<?> name, Object... par) {
        this.par.put(name, par);
    }
    public void setPars(Map<Class<?>, Object[]> pars) {
        this.par=pars;
    }
    public Map<Class<?>, List<Object>> getMap() {
        return map;
    }
    public void setMap(Map<Class<?>, List<Object>> map) {
        this.map = map;
    }

    public RedisModel getModel() {
        return redisModel;
    }

    public void setModel(RedisModel redisModel) {
        this.redisModel = redisModel;
    }
}
