package com.wemew.rediscache.utils.reflect;
 /**
   * 作者 CG
   * 时间 2019/12/31 22:11
   * 注释 根据相应的类型转换为对应的类型
   */
public interface StrToObjectService<T> {
    T castObject(Class<T> t, String value);
}
