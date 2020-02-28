package com.wemew.rediscache.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wemew.rediscache.config.exception.BusinessException;
import com.wemew.rediscache.enums.RedisModel;
import com.wemew.rediscache.enums.RedisSoure;
import com.wemew.rediscache.enums.RedisStatus;
import com.wemew.rediscache.enums.RedisType;
import com.wemew.rediscache.mapper.RedisManageMapper;
import com.wemew.rediscache.model.MethodNameAndArgs;
import com.wemew.rediscache.model.RedisManage;
import com.wemew.rediscache.service.RedisManageService;
import com.wemew.rediscache.service.RedisUtils;
import com.wemew.rediscache.utils.SpringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * redis缓存策略 服务实现类
 * </p>
 *
 * @author CG
 * @since 2020-02-25
 */
@Service
public class RedisManageServiceImpl extends ServiceImpl<RedisManageMapper, RedisManage> implements RedisManageService {
    @Autowired
    private SpringUtil springUtil;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<MethodNameAndArgs> findKeyOpsName() {
        List<MethodNameAndArgs> list = new ArrayList<>();
        RedisTemplate redis = redisUtils.getRedis(RedisSoure.REDISONE.getSoure());
        Method[] declaredMethods = redis.getClass().getMethods();
        for (Method declaredMethod : declaredMethods) {
            MethodNameAndArgs method = springUtil.getMethod(declaredMethod);
            list.add(method);
        }
        return list;
    }

    @Override
    public List<MethodNameAndArgs> findKeyTypeOpsName(Integer redisIndex) {
        List<MethodNameAndArgs> list = new ArrayList<>();
        RedisModel model = RedisModel.findIndex(redisIndex);
        if (model==null)
            throw new BusinessException("未获取model类型!");
        Class<?> className = model.getClassName();
        Method[] declaredMethods = className.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            MethodNameAndArgs method = springUtil.getMethod(declaredMethod);
            list.add(method);
        }
        return list;
    }

    @Override
    public List<RedisManage> findPid(String pid) {
        QueryWrapper<RedisManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RedisManage::getPid, pid).
                eq(RedisManage::getStatus, RedisStatus.UP.getStatus());
        return list(queryWrapper);
    }

    @Override
    public List<RedisManage> all() {
        QueryWrapper<RedisManage> queryWrapper = new QueryWrapper<>();
        return list(queryWrapper);
    }

    @Override
    @Transactional
    public boolean addKey(RedisManage redisManage) {
        RedisType redisType = RedisType.ROOT;
        Integer redisIndex = redisManage.getRedisIndex();
        String opsName = redisManage.getOpsName();
        RedisModel model = RedisModel.findIndex(redisIndex);
        if (model==null)
            throw new BusinessException("RedisModel参数错误!");
        if (StringUtils.isBlank(opsName))
            throw new BusinessException("opsName参数错误!");
        //查询是否已经添加过
        RedisManage index = findIndexOps(redisIndex, redisType,opsName);
        if (index!=null)
            throw new BusinessException("redisKey已添加过该操作类型!");
        redisManage.setId(UUID.randomUUID().toString());
        redisManage.setCreateTime(LocalDateTime.now());
        redisManage.setStatus(RedisStatus.UP.getStatus());
        redisManage.setClassify(redisType.getType());
        redisManage.setPid(null);
        return save(redisManage);
    }

    @Override
    public boolean addSubKey(RedisManage redisManage) {
        RedisType redisType = RedisType.SUBCLASS;
        Integer redisIndex = redisManage.getRedisIndex();
        String pid = redisManage.getPid();
        String opsName = redisManage.getOpsName();
        if (StringUtils.isBlank(opsName))
            throw new BusinessException("redisKey操作命令参数错误!");
        if (StringUtils.isBlank(pid))
            throw new BusinessException("根节点ID参数错误!");
        RedisManage byId = getById(pid);//根节点
        if (byId==null)
            throw new BusinessException("未获取到根节点信息!");
        RedisModel model = RedisModel.findIndex(redisIndex);//关联的RedisModel
        if (model==null)
            throw new BusinessException("RedisModel参数错误!");
        RedisManage keyTypeOps = findIndexOps(redisIndex, RedisType.ROOT,opsName);//关联的key是否配置了操作方法
        if (keyTypeOps==null)
         throw new BusinessException("redisKey还未配置该操作类型!");
        //查询是否已经添加过
        RedisManage index = findIndexOpsPid(pid,redisIndex, redisType,opsName);
        if (index!=null)
            throw new BusinessException("redisKey子节点已添加过该类型!");
        if(byId.getRedisIndex()==redisIndex)
            throw new BusinessException("redisIndex不能添加自己!");
        redisManage.setId(UUID.randomUUID().toString());
        redisManage.setCreateTime(LocalDateTime.now());
        redisManage.setStatus(RedisStatus.UP.getStatus());
        redisManage.setClassify(redisType.getType());
        return save(redisManage);
    }


    @Override
    public RedisManage findIndexOps(Integer index,RedisType redisType, String opsName) {
        QueryWrapper<RedisManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RedisManage::getRedisIndex, index).
                eq(RedisManage::getClassify,redisType.getType()).
                eq(RedisManage::getStatus, RedisStatus.UP.getStatus()).
                eq(RedisManage::getOpsName,opsName);
        return getOne(queryWrapper);
    }

    @Override
    public RedisManage findIndexOpsPid(String pid,Integer index,RedisType redisType,String opsName) {
        QueryWrapper<RedisManage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RedisManage::getRedisIndex, index).
                eq(RedisManage::getClassify, redisType.getType()).
                eq(RedisManage::getOpsName, opsName).
                eq(RedisManage::getStatus, RedisStatus.UP.getStatus()).
                eq(RedisManage::getPid,pid);
        return getOne(queryWrapper);
    }
}
