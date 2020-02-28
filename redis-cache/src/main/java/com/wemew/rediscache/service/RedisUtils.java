package com.wemew.rediscache.service;

import com.alibaba.fastjson.JSONObject;
import com.wemew.rediscache.enums.RedisModel;
import com.wemew.rediscache.model.MethodNameAndArgs;
import com.wemew.rediscache.model.RedisManage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.Method;
import java.util.List;

/**
   * 作者 CG
   * 时间 2020/2/24 11:46
   * 注释 redis工具类
   */
public interface RedisUtils {

    /**
     * 作者 CG
     * 时间 2020/2/25 19:23
     * 注释 封装参数
     */
    JSONObject createJson(RedisModel redisModel, List<RedisManage> list);
    /**
     * 作者 CG
     * 时间 2020/2/25 18:53
     * 注释 获取redis配置列表
     */
    List<JSONObject> redisList();
    /**
     * 作者 CG
     * 时间 2020/2/25 18:42
     * 注释 获取key集合
     */
    List<String> getRedisKeyList();
       /**
       * 作者 CG
       * 时间 2020/2/24 19:09
       * 注释 比较2个参数
       */
    boolean eqParameterTypes(List<Class<?>> classes, List<Class<?>> parameter);

    /**
     * 作者 CG
     * 时间 2020/2/24 16:41
     * 注释 转换成对应方法的参数列表
     * 此方法有待验证
     */
    Object[] objectToMetho(Method method,Object... objects);

    /**
     * 作者 CG
     * 时间 2020/2/24 14:22
     * 注释 通过对应的操作类型获取操作类
     *  redisTemplate.opsForValue().set();返回一个方法
     */
    Method getRedisTypeOps(Object object, String methodName, Class<?>... classes) ;

    /**
     * 作者 CG
     * 时间 2020/2/24 13:40
     * 注释 根据传入类型和参数执行对应的方法
     * typeName 主要是操作哪种类型比如set,list,string
     * methodName 是对应的操作方法
     */
    Object opsForType(RedisTemplate redisTemplate,RedisModel redisModel, String methodName, Object... objects) ;
    /**
     * 作者 CG
     * 时间 2020/2/24 13:35
     * 注释 获取redis连接属性
     */
    RedisTemplate getRedis(Integer redisIndex);

}
