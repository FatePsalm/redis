package com.solace.redis_data_transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.solace.redis_data_transaction.solace.mapper")
public class RedisDataTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisDataTransactionApplication.class, args);
    }

}
