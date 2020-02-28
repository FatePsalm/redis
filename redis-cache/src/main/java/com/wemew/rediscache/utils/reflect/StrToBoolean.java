package com.wemew.rediscache.utils.reflect;

import org.springframework.stereotype.Component;

@Component
public class StrToBoolean implements StrToObjectService<Boolean> {


    @Override
    public Boolean castObject(Class<Boolean> t, String value) {
        return Boolean.valueOf(value);
    }

}
