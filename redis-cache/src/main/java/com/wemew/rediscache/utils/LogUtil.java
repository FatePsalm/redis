package com.wemew.rediscache.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者 CG
 * 时间 2019/12/16 19:05
 * 注释 统一日志
 */
public class LogUtil {
    public static Logger getLog(Class<?> logClass) {
        return LoggerFactory.getLogger(logClass);
    }
}
