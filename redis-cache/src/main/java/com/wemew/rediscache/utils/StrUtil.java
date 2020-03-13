package com.wemew.rediscache.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {
     /**
       * 作者 CG
       * 时间 2020/3/3 15:16
       * 注释 截取$以前的字符串
       */
     public static String subStringDoule(String str) {
         if (StringUtils.isNotBlank(str)){
             int index = str.indexOf("$");
             if (index!=-1){
                 str = str.substring(0, index);
             }
         }
         return str;
     }
     /**
       * 作者 CG
       * 时间 2020/2/25 11:33
       * 注释 将{{userid}} 替换为 userid
       */
     public static String replace(String key,String parameter,String value) {
         return key.replaceAll("\\{\\{" + parameter + "\\}\\}", value);
     }
    /**
     * 作者 CG
     * 时间 2020/1/14 16:38
     * 注释 截图双花括号类的内容
     */
    public static String getInParentheses(String value) {
        String regex = "(?<=\\{\\{)[^}]*(?=\\}\\})";
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (value);
        while (matcher.find ())
        {
            return matcher.group();
        }
        return null;
    }
}
