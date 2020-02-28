package com.wemew.rediscache.config.handler;

import com.alibaba.fastjson.JSONException;
import com.wemew.rediscache.config.exception.BusinessException;
import com.wemew.rediscache.model.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
   * 作者 CG
   * 时间 2019/8/5 14:52
   * 注释 全局异常接收
   */
@RestControllerAdvice
public class GlobalException {
    /**
      * 作者 CG
      * 时间 2019/8/5 14:54
      * 注释 业务逻辑异常
      */
    @ExceptionHandler(BusinessException.class)
    public JsonResult businessException(BusinessException e) {
        return new JsonResult(new NullPointerException(e.getMessage()),1001);
    }
     /**
      * 作者 CG
      * 时间 2019/8/5 14:54
      * 注释 运行时异常
      */
     @ExceptionHandler(RuntimeException.class)
     public JsonResult runtimeException(RuntimeException e) {
         e.printStackTrace();
         return new JsonResult(new NullPointerException("系统异常,请联系管理员!"),1001);
     }

    /**
     * 作者 CG
     * 时间 2019/10/15 9:31
     * 注释 未知错误
     */
    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception e) {
        e.printStackTrace();
        return new JsonResult(new NullPointerException("未知错误,请联系管理员!"),1001);
    }
    /**
     * 作者 CG
     * 时间 2019/10/15 9:31
     * 注释 JSON转换异常
     */
    @ExceptionHandler(JSONException.class)
    public JsonResult jsonException(JSONException e) {
        e.printStackTrace();
        return new JsonResult(new NullPointerException("JSON转换异常,请核实后在提交!"),1001);
    }
    /**
     * 作者 CG
     * 时间 2019/10/15 9:31
     * 注释 没有获取到对应方法
     */
    @ExceptionHandler(NoSuchMethodException.class)
    public JsonResult jsonException(NoSuchMethodException e) {
        e.printStackTrace();
        return new JsonResult(new BusinessException("没有获取到对应方法!"),1001);
    }
}
