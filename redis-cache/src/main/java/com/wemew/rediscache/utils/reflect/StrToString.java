package com.wemew.rediscache.utils.reflect;

import org.springframework.stereotype.Component;

@Component
public class StrToString implements StrToObjectService<String> {


    @Override
    public String castObject(Class<String> t, String value) {
        return value;
    }

}
