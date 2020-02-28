package com.wemew.rediscache.utils.reflect;

import com.wemew.rediscache.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StrToLocalDateTime implements StrToObjectService<LocalDateTime> {

    @Override
    public LocalDateTime castObject(Class<LocalDateTime> t, String value) {
        return DateUtil.strToLocalDate(value);
    }
}
