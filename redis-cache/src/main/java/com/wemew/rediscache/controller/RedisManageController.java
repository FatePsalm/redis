package com.wemew.rediscache.controller;


import com.wemew.rediscache.model.JsonResult;
import com.wemew.rediscache.model.MethodNameAndArgs;
import com.wemew.rediscache.model.RedisManage;
import com.wemew.rediscache.service.RedisManageService;
import com.wemew.rediscache.service.RedisOpsService;
import com.wemew.rediscache.service.parameter.AdminRedisParament;
import com.wemew.rediscache.service.parameter.UserRedisParament;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/redisManage")
public class RedisManageController {
    @Autowired
    private RedisManageService redisManageService;
    @Autowired
    private RedisOpsService redisOpsService;

    @RequestMapping("testdel")
    public JsonResult testdel() {

        return new JsonResult();
    }

    @RequestMapping("testadd")
    public JsonResult testadd() {
        UserRedisParament parament = new UserRedisParament();
        parament.setPar(UserRedisParament.class, "11111", "我是大张伟");
        parament.setPar(AdminRedisParament.class, "3333", "我是管理员");
        redisOpsService.opsNoTransaction(parament, parament.set());
        return new JsonResult();
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
