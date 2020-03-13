package com.wemew.rediscache.utils;

import com.alibaba.druid.sql.visitor.functions.Char;

public class ReflectionUtil {

    public static Class<?> basisToObject(Class<?> basisClass) {
        if (basisClass == byte.class)
            return Byte.class;
        if (basisClass == short.class)
            return Short.class;
        if (basisClass == int.class)
            return Integer.class;
        if (basisClass == long.class)
            return Long.class;
        if (basisClass == float.class)
            return Float.class;
        if (basisClass == double.class)
            return Double.class;
        if (basisClass == boolean.class)
            return Boolean.class;
        if (basisClass == char.class)
            return Char.class;
        return null;
    }
}
