package com.wemew.rediscache.utils.reflect;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StrToBigDecimal implements StrToObjectService<BigDecimal> {


    @Override
    public BigDecimal castObject(Class<BigDecimal> t, String value) {
        return new BigDecimal(value);
    }

}
