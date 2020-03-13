package com.wemew.rediscache.controller;


import com.alibaba.fastjson.JSONObject;
import com.wemew.rediscache.config.exception.BusinessException;
import com.wemew.rediscache.enums.RedisModel;
import com.wemew.rediscache.model.JsonResult;
import com.wemew.rediscache.model.MethodNameAndArgs;
import com.wemew.rediscache.model.RedisManage;
import com.wemew.rediscache.service.RedisManageService;
import com.wemew.rediscache.service.RedisOpsService;
import com.wemew.rediscache.service.parameter.AdminRedisParament;
import com.wemew.rediscache.service.parameter.UserRedisParament;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * redis缓存策略 前端控制器
 * </p>
 *
 * @author CG
 * @since 2020-02-25
 */
@CrossOrigin
@RestController
@RequestMapping("/redisManage")
public class RedisManageController {
    @Autowired
    private RedisManageService redisManageService;
    @Autowired
    private RedisOpsService redisOpsService;

    @RequestMapping("testdel")
    public JsonResult testdel() {
        UserRedisParament parament = new UserRedisParament();
        parament.setPar(UserRedisParament.class, "11111");
        parament.setPar(AdminRedisParament.class, "3333");
        redisOpsService.opsNoTransaction(parament.delete());
        return new JsonResult();
    }

    @RequestMapping("testadd")
    public JsonResult testadd() {
        UserRedisParament parament = new UserRedisParament();
        parament.setPar(UserRedisParament.class, "11111", "我是大张伟");
        parament.setPar(AdminRedisParament.class, "3333", "我是管理员");
        redisOpsService.opsNoTransaction(parament.set());
        return new JsonResult();
    }

    /**
     * 作者 CG
     * 时间 2020/3/4 11:17
     * 注释 删除全部的subList
     */
    @RequestMapping("deleteList")
    public JsonResult deleteList(String id) {
        if (StringUtils.isBlank(id))
            throw new BusinessException("参数错误!");
        Integer integer = redisManageService.deleteList(id);
        return new JsonResult(integer);
    }

    /**
     * 作者 CG
     * 时间 2020/3/4 11:17
     * 注释 获取全部的subList
     */
    @RequestMapping("subList")
    public JsonResult subList(String pid) {
        if (StringUtils.isBlank(pid))
            throw new BusinessException("参数错误!");
        List<JSONObject> jsonObjects = redisManageService.subList(pid);
        return new JsonResult(jsonObjects);
    }

    /**
     * 作者 CG
     * 时间 2020/3/4 11:17
     * 注释 获取全部的modelAll
     */
    @RequestMapping("modelAll")
    public JsonResult modelAll() {
        List<String> redisModels = redisManageService.modelAll();
        if (redisModels == null)
            throw new BusinessException("未获取到model类型!");
        return new JsonResult(redisModels);
    }

    /**
     * 作者 CG
     * 时间 2020/2/25 19:46
     * 注释 根据下标查询model信息
     */
    @RequestMapping("findModel")
    public JsonResult findModel(Integer redisIndex) {
        JSONObject model = redisManageService.findModel(redisIndex);
        return new JsonResult(model);
    }

    /**
     * 作者 CG
     * 时间 2020/2/25 19:46
     * 注释 获取key操作类型
     */
    @RequestMapping("findKeyOpsName")
    public JsonResult findKeyOpsName() {
        List<MethodNameAndArgs> keyOpsName = redisManageService.findKeyOpsName();
        return new JsonResult(keyOpsName);
    }

    /**
     * 作者 CG
     * 时间 2020/2/25 19:46
     * 注释 获取key操作类型
     */
    @RequestMapping("findKeyTypeOpsName")
    public JsonResult findKeyTypeOpsName(Integer redisIndex) {
        List<MethodNameAndArgs> keyOpsName = redisManageService.findKeyTypeOpsName(redisIndex);
        return new JsonResult(keyOpsName);
    }

    /**
     * 作者 CG
     * 时间 2020/2/25 19:46
     * 注释 添加子节点
     */
    @RequestMapping("addSubKey")
    public JsonResult addSubKey(RedisManage redisManage) {
        boolean b = redisManageService.addSubKey(redisManage);
        return new JsonResult(b);
    }

    /**
     * 作者 CG
     * 时间 2020/2/25 10:47
     * 注释 添加key
     */
    @RequestMapping("addKey")
    public JsonResult addKey(RedisManage redisManage) {
        boolean b = redisManageService.addKey(redisManage);
        return new JsonResult(b);
    }
}
