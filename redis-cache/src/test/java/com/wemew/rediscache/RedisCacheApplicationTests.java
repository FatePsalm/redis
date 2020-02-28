package com.wemew.rediscache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RedisCacheApplicationTests {
    @Test
    void strTest()  {


    }
    public String add(){
        return Thread.currentThread().getStackTrace()[1].getMethodName();
    }
    @Test
    void contextLoads() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        Type[] types = objectObjectHashMap.getClass().getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) type;
                Class clazz = (Class<?>) pType.getRawType();
                if ( Map.class.isAssignableFrom(clazz)){
                   Class keyClazz = (Class) pType.getActualTypeArguments()[0];
                   Class valueClazz = (Class) pType.getActualTypeArguments()[1];


                }
            }
        }

    }

}
