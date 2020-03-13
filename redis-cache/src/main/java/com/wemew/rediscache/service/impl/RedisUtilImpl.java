package com.wemew.rediscache.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wemew.rediscache.config.exception.BusinessException;
import com.wemew.rediscache.enums.RedisModel;
import com.wemew.rediscache.enums.RedisSoure;
import com.wemew.rediscache.model.RedisManage;
import com.wemew.rediscache.service.RedisManageService;
import com.wemew.rediscache.service.RedisUtils;
import com.wemew.rediscache.utils.ReflectionUtil;
import com.wemew.rediscache.utils.SpringUtil;
import com.wemew.rediscache.utils.StrUtil;
import com.wemew.rediscache.utils.reflect.StrToGenericityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 作者 CG
 * 时间 2020/2/24 11:45
 * 注释 redis工具类
 */
@Service
public class RedisUtilImpl implements RedisUtils {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private StrToGenericityService strToGenericityService;
    @Autowired
    private RedisManageService redisManageService;
    @Autowired
    private SpringUtil springUtil;



    @Override
    public JSONObject createJson(RedisModel redisModel, List<RedisManage> list) {
        JSONObject js = new JSONObject(true);
        int index = redisModel.getIndex();
        js.put("redisModel", redisModel.toString());
        JSONObject reveal = new JSONObject();
        reveal.put("Key", redisModel.getKey());
        reveal.put("Time", redisModel.getTime());
        reveal.put("Type", redisModel.getType());
        reveal.put("redisSource", redisModel.getRedisSource());
        reveal.put("text", redisModel.getText());
        reveal.put("index", redisModel.getIndex());
        js.put("reveal", reveal);
        if (list != null) {
            //筛选出对应下标
            Map<Integer, List<RedisManage>> collect = list.stream().filter(x -> x.getPid() == null).collect(Collectors.groupingBy(RedisManage::getRedisIndex));
            List<RedisManage> redisManages = collect.get(index);
            //如果key对应下标操作方法不等于null
            if (redisManages != null) {
                //在对应的操作方法找到关联key
                for (RedisManage redisManage : redisManages) {
                    String id = redisManage.getId();
                    List<RedisManage> subList = list.stream().filter(x -> x.getPid() != null && x.getPid().equals(id)).collect(Collectors.toList());
                    redisManage.setRedisManage(subList);
                }
                js.put("ops", redisManages);
            }
        }
        return js;
    }

    @Override
    public List<JSONObject> redisList() {
        List<JSONObject> list = new ArrayList<>();
        //先获取枚举基础类型
        RedisModel[] values = RedisModel.values();
        //查询数据库所有的配置信息
        List<RedisManage> all = redisManageService.all();
        for (RedisModel value : values) {
            JSONObject json = createJson(value, all);
            list.add(json);
        }
        return list;
    }

    @Override
    public List<String> getRedisKeyList() {
        List<String> list = new ArrayList<>();
        RedisModel[] values = RedisModel.values();
        for (RedisModel value : values) {
            list.add(value.toString());
        }
        return list;
    }

    @Override
    public boolean eqParameterTypes(List<Class<?>> classes, List<Class<?>> parameter) {
        for (int i = 0; i < classes.size(); i++) {
            Class<?> aClass = classes.get(i);
            Class<?> parame = parameter.get(i);
            if (parame.isPrimitive()){
                parame = ReflectionUtil.basisToObject(parame);
                if (parame==null)
                    throw new BusinessException("基础类型转化异常!");
            }
            String aClassName = StrUtil.subStringDoule(aClass.getName()) ;
            String parameName = StrUtil.subStringDoule(parame.getName());
            if (!aClassName.equals(parameName)) {
                if (parame != Object.class) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Object[] objectToMetho(Method method, Object... objects) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (!parameterType.equals(Object.class)) {
                objects[i] = strToGenericityService.invoke(parameterType, String.valueOf(objects[i]));
            }
        }
        return parameterTypes;
    }

    @Override
    public Method getRedisTypeOps(Object object, String methodName, Class<?>... classes) {
        if (object != null && StringUtils.isNotBlank(methodName)) {
            Class<?> objectClass = object.getClass();
            Method[] methods = objectClass.getMethods();
            int classSise = classes == null ? 0 : classes.length;
            for (Method method : methods) {
                String name = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (name.equals(methodName) && classSise == parameterTypes.length) {//判断方法名字
                    if (classes == null)
                        return method;
                    boolean eqParameterTypes = eqParameterTypes(Arrays.asList(classes), Arrays.asList(parameterTypes));
                    if (eqParameterTypes) {
                        return method;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object opsForType(RedisTemplate redisTemplate,RedisModel redisModel, String methodName, Object... objects) {
        Class<?>[] classes = springUtil.parameterType(objects);
        String type = redisModel.getType();
        //先根据操作类型获取,如果没有获取到则获取key操作类型
        Object redis = redisTemplate;
        Method redisType = getRedisTypeOps(redis, type, null);
        Object redisTypeInvoke = springUtil.invoke(redis, redisType, null);//获取操作类型
        Method redisTypeOps = getRedisTypeOps(redisTypeInvoke, methodName, classes);//获取操作方法
        if (redisTypeOps == null) {
            redisTypeInvoke = redis;
            redisTypeOps = getRedisTypeOps(redis, methodName, classes);
        }
        if (redisTypeOps == null)
            throw new BusinessException("RedisOps操作类型错误!");
        Object invoke = springUtil.invoke(redisTypeInvoke, redisTypeOps, objects);
        return invoke;
    }

    @Override
    public RedisTemplate getRedis(Integer redisIndex) {
        if (redisIndex != null) {
            if (redisIndex == RedisSoure.REDISONE.getSoure()) {
                return stringRedisTemplate;
            }
        }
        return stringRedisTemplate;
    }
}
