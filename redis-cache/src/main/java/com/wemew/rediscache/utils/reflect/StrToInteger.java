package com.wemew.rediscache.utils.reflect;

import org.springframework.stereotype.Component;

@Component
public class StrToInteger implements StrToObjectService<Integer> {

    @Override
    public Integer castObject(Class<Integer> t, String value) {
        return Integer.valueOf(value);
    }
}
