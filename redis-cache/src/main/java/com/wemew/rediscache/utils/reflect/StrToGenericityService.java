package com.wemew.rediscache.utils.reflect;

import com.wemew.rediscache.utils.SpringUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 作者 CG
  * 时间 2019/12/31 22:19
  * 注释 策略模式
  */
@Component
public class StrToGenericityService {
   Map<Class<?>, StrToObjectService> map = new HashMap<>();

   public StrToGenericityService(List<StrToObjectService> list) {
       for (StrToObjectService index : list
       ) {
           Class<?> indexClass = SpringUtil.classGenericity(index);
           map.put(indexClass, index);
       }
   }

   public <T> T invoke(Class<T> type, String value) {
       StrToObjectService str = map.get(type);
       return (T) str.castObject(type.getClass(), value);
   }
}
