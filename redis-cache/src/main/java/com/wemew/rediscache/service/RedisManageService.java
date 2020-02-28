package com.wemew.rediscache.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wemew.rediscache.enums.RedisType;
import com.wemew.rediscache.model.MethodNameAndArgs;
import com.wemew.rediscache.model.RedisManage;

import java.util.List;

/**
 * <p>
 * redis缓存策略 服务类
 * </p>
 *
 * @author CG
 * @since 2020-02-25
 */
public interface RedisManageService extends IService<RedisManage> {
    /**
     * 作者 CG
     * 时间 2020/2/28 18:41
     * 注释 获取key的操作方法
     */
    List<MethodNameAndArgs> findKeyOpsName();
    /**
     * 作者 CG
     * 时间 2020/2/28 18:41
     * 注释 获取keyOps的操作方法
     */
    List<MethodNameAndArgs> findKeyTypeOpsName(Integer redisIndex);
    /**
     * 作者 CG
     * 时间 2020/2/26 10:24
     * 注释 查询pid列表
     */
    List<RedisManage> findPid(String pid);
    /**
     * 作者 CG
     * 时间 2020/2/25 18:59
     * 注释 查询所有的内容
     */
    List<RedisManage> all();
    /**
     * 作者 CG
     * 时间 2020/2/25 18:15
     * 注释 添加key操作方法
     */
    boolean addKey(RedisManage redisManage);
    /**
     * 作者 CG
     * 时间 2020/2/25 18:15
     * 注释 添加子节点
     */
    boolean addSubKey(RedisManage redisManage);

     /**
       * 作者 CG
       * 时间 2020/2/26 11:45
       * 注释 操作类型查询
       */
     RedisManage findIndexOps(Integer index,RedisType redisType, String opsName);
    /**
     * 作者 CG
     * 时间 2020/2/26 11:45
     * 注释 查询是否在key操作类型下添加关联key
     * pid 操作类型id
     * index 需要关联的keyIndex
     * subClass 操作类型
     */
    RedisManage findIndexOpsPid(String pid,Integer index,RedisType redisType,String opsName);


}
