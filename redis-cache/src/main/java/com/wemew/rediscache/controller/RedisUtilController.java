package com.wemew.rediscache.controller;

import com.alibaba.fastjson.JSONObject;
import com.wemew.rediscache.model.JsonResult;
import com.wemew.rediscache.service.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RedisUtilController {
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 作者 CG
     * 时间 2020/2/25 18:42
     * 注释 获取key的配置列表
     */
    @RequestMapping("redisList")
    public JsonResult redisList()  {
        List<JSONObject> list = redisUtils.redisList();
        return new JsonResult(list);
    }
}
