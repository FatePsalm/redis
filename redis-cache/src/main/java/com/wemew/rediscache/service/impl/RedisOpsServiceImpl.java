package com.wemew.rediscache.service.impl;

import com.alibaba.fastjson.JSON;
import com.wemew.rediscache.config.exception.BusinessException;
import com.wemew.rediscache.enums.RedisModel;
import com.wemew.rediscache.enums.RedisType;
import com.wemew.rediscache.model.RedisManage;
import com.wemew.rediscache.model.RedisOpsParameter;
import com.wemew.rediscache.service.*;
import com.wemew.rediscache.utils.LogUtil;
import com.wemew.rediscache.utils.StrinUtil;
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
    public void relevancy(RedisTemplate redisTemplate, RedisOpsParameterService redisOpsParameterService, String opsName) {
        RedisModel redisModel = ((RedisOpsParameter) redisOpsParameterService).getModel();
        //先查找对应的index和操作类型,获取到id
        RedisManage index = redisManageService.findIndexOps(redisModel.getIndex(), RedisType.ROOT, opsName);
        if (index != null) {
            List<RedisManage> pid = redisManageService.findPid(index.getId());
            if (pid != null) {
                //关联的操作
                for (RedisManage redisManage : pid) {
                    Integer redisIndex = redisManage.getRedisIndex();
                    RedisModel model = RedisModel.findIndex(redisIndex);
                    ((RedisOpsParameter) redisOpsParameterService).setModel(model);
                    ops(redisTemplate, redisOpsParameterService, redisManage.getOpsName());
                }
            }
        }
    }

    @Override
    public Object[] parameters(RedisModel redisModel, Map<Class<?>, List<Object>> map) {
        List<Object> objects = map.get(redisModel.getClassName());
        String key = redisModel.getKey();//获取key 比如:"admin:id:{{userid}}"
        String inParentheses = StrinUtil.getInParentheses(key);//筛选参数userid
        if (StringUtils.isNotBlank(inParentheses)) {
            if (objects == null) {
                LogUtil.getLog(this.getClass()).error("参数错误:RedisKey:" + key + ",inParentheses:" + inParentheses + ",redisModel:" + redisModel.toString() + ",objects:" + JSON.toJSONString(objects));
                throw new BusinessException("参数错误:RedisKey:" + key + ",inParentheses:" + inParentheses + ",redisModel:" + redisModel.toString() + ",objects:" + JSON.toJSONString(objects));
            }
            String keyPar = String.valueOf(objects.get(0));//userid->87699de5-9769-4fa5-8616-a6d67e60cee8
            objects.set(0, StrinUtil.replace(key, inParentheses, keyPar));//比如:"admin:id:87699de5-9769-4fa5-8616-a6d67e60cee8"
        }
        return objects.toArray();
    }


    @Override
    public Object ops(RedisTemplate redisTemplate, RedisOpsParameterService redisOpsParameterService, String opsName) {
        RedisOpsParameter opsParameter = (RedisOpsParameter) redisOpsParameterService;
        Map<Class<?>, List<Object>> map = opsParameter.getMap();
        //springUtil.invoke(redisOpsParameterService, opsName, null);//返回封装参数
        RedisModel redisModel = findModel(redisOpsParameterService);
        Object[] parameters = parameters(redisModel, map);
        //通过数据库查找对应的操作类型和参数列表
        RedisManage del = redisManageService.findIndexOps(redisModel.getIndex(), RedisType.ROOT, opsName);
        if (del == null)
            throw new BusinessException("需要在后台配置当前删除方法的操作命令!");
        Object o = redisUtils.opsForType(redisTemplate, redisModel, del.getOpsName(), parameters);
        relevancy(redisTemplate, redisOpsParameterService, opsName);
        return o;
    }

    @Override
    public Object opsOpenTransaction(RedisOpsParameterService redisOpsParameterService, String opsName) {
        Object ops = null;
        RedisModel redisModel = findModel(redisOpsParameterService);
        int redisSource = redisModel.getRedisSource();
        RedisTemplate redis = redisUtils.getRedis(redisSource);
        redis.setEnableTransactionSupport(true);
        try {
            redis.multi();//开启事务
            ops = ops(redis, redisOpsParameterService, opsName);
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
    public Object opsNoTransaction(RedisOpsParameterService redisOpsParameterService, String opsName) {
        RedisModel redisModel = findModel(redisOpsParameterService);
        int redisSource = redisModel.getRedisSource();
        RedisTemplate redis = redisUtils.getRedis(redisSource);
        Object ops = ops(redis, redisOpsParameterService, opsName);
        return ops;
    }
     /**
       * 作者 CG
       * 时间 2020/2/28 18:59
       * 注释 获取model类型
       */
    RedisModel findModel(RedisOpsParameterService redisOpsParameterService) {
        RedisOpsParameter redisOpsParameter = (RedisOpsParameter) redisOpsParameterService;
        RedisModel model = redisOpsParameter.getModel();
        if (model == null) {
            model = RedisModel.getClassName(redisOpsParameterService);
        }
        return model;
    }
}
