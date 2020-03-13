package com.wemew.rediscache.service.impl;

import com.alibaba.fastjson.JSON;
import com.wemew.rediscache.config.exception.BusinessException;
import com.wemew.rediscache.enums.RedisModel;
import com.wemew.rediscache.enums.RedisType;
import com.wemew.rediscache.model.RedisManage;
import com.wemew.rediscache.model.RedisOpsParameter;
import com.wemew.rediscache.service.*;
import com.wemew.rediscache.utils.LogUtil;
import com.wemew.rediscache.utils.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RedisOpsServiceImpl implements RedisOpsService, RedisOpsRelevancyService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RedisManageService redisManageService;


    @Override
    public void relevancy(RedisTemplate redisTemplate,RedisOpsParameter redisOpsParameter) {
        RedisModel redisModel = redisOpsParameter.getModel();
        //先查找对应的index和操作类型,获取到id
        RedisManage index = redisManageService.findIndexOps(redisModel.getIndex(), RedisType.ROOT, redisOpsParameter.getMethodName());
        if (index != null) {
            List<RedisManage> pid = redisManageService.findPid(index.getId());
            if (pid != null) {
                //关联的操作
                for (RedisManage redisManage : pid) {
                    Integer redisIndex = redisManage.getRedisIndex();
                    RedisModel model = RedisModel.findIndex(redisIndex);
                    redisOpsParameter.setModel(model);
                    redisOpsParameter.setMethodName(redisManage.getOpsName());
                    ops(redisTemplate, redisOpsParameter);
                }
            }
        }
    }

    @Override
    public Object[] parameters(RedisModel redisModel, Map<Class<?>, List<Object>> map) {
        List<Object> objects = map.get(redisModel.getClassName());
        String key = redisModel.getKey();//获取key 比如:"admin:id:{{userid}}"
        String inParentheses = StrUtil.getInParentheses(key);//筛选参数userid
        if (StringUtils.isNotBlank(inParentheses)) {
            if (objects == null) {
                LogUtil.getLog(this.getClass()).error("参数错误:RedisKey:" + key + ",inParentheses:" + inParentheses + ",redisModel:" + redisModel.toString() + ",objects:" + JSON.toJSONString(objects));
                throw new BusinessException("参数错误:RedisKey:" + key + ",inParentheses:" + inParentheses + ",redisModel:" + redisModel.toString() + ",objects:" + JSON.toJSONString(objects));
            }
            String keyPar = String.valueOf(objects.get(0));//userid->87699de5-9769-4fa5-8616-a6d67e60cee8
            objects.set(0, StrUtil.replace(key, inParentheses, keyPar));//比如:"admin:id:87699de5-9769-4fa5-8616-a6d67e60cee8"
        }
        return objects.toArray();
    }


    @Override
    public Object ops(RedisTemplate redisTemplate, RedisOpsParameter redisOpsParameter) {
        Map<Class<?>, List<Object>> map = redisOpsParameter.getMap();
        //springUtil.invoke(redisOpsParameterService, opsName, null);//返回封装参数
        RedisModel redisModel = findModel(redisOpsParameter);
        Object[] parameters = parameters(redisModel, map);
        //通过数据库查找对应的操作类型和参数列表
        RedisManage del = redisManageService.findIndexOps(redisModel.getIndex(), RedisType.ROOT, redisOpsParameter.getMethodName());
        if (del == null)
            throw new BusinessException("需要在后台配置当前删除方法的操作命令!");
        Object o = redisUtils.opsForType(redisTemplate, redisModel, del.getOpsName(), parameters);
        relevancy(redisTemplate, redisOpsParameter);
        return o;
    }

    @Override
    public Object opsOpenTransaction(RedisOpsParameter redisOpsParameter) {
        Object ops = null;
        RedisModel redisModel = findModel(redisOpsParameter);
        int redisSource = redisModel.getRedisSource();
        RedisTemplate redis = redisUtils.getRedis(redisSource);
        redis.setEnableTransactionSupport(true);
        try {
            redis.multi();//开启事务
            ops = ops(redis, redisOpsParameter);
            //提交
            redis.exec();
        } catch (Exception e) {
            LogUtil.getLog(this.getClass()).error(e.getMessage(), e);
            //开启回滚
            redis.discard();
        }
        return ops;
    }

    @Override
    public Object opsNoTransaction(RedisOpsParameter redisOpsParameter) {
        RedisModel redisModel = findModel(redisOpsParameter);
        int redisSource = redisModel.getRedisSource();
        RedisTemplate redis = redisUtils.getRedis(redisSource);
        Object ops = ops(redis, redisOpsParameter);
        return ops;
    }
     /**
       * 作者 CG
       * 时间 2020/2/28 18:59
       * 注释 获取model类型
       */
    RedisModel findModel(RedisOpsParameter redisOpsParameter) {
        RedisModel model = redisOpsParameter.getModel();
        if (model == null) {
            model = RedisModel.getClassName(redisOpsParameter);
        }
        return model;
    }
}
