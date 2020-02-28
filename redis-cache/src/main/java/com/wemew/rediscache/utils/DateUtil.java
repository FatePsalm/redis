package com.wemew.rediscache.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtil {

    /**
     * 作者 CG
     * 时间 2019/11/25 15:31
     * 注释 时间转换
     */
    public static LocalDateTime strToLocalDate(String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(time, df);
    }
}
