package com.solace.redis_data_transaction.solace.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 本地数据源 redis template
     *
     * @param database
     * @param timeout
     * @param maxActive
     * @param maxWait
     * @param maxIdle
     * @param minIdle
     * @param hostName
     * @param port
     * @param password
     * @return
     */
    @Bean
    public RedisTemplate redisTemplateLocal(
            @Value("${spring.redis.local.database}") int database,
            @Value("${spring.redis.timeout}") long timeout,
            @Value("${spring.redis.lettuce.pool.max-active}") int maxActive,
            @Value("${spring.redis.lettuce.pool.max-wait}") int maxWait,
            @Value("${spring.redis.lettuce.pool.max-idle}") int maxIdle,
            @Value("${spring.redis.lettuce.pool.min-idle}") int minIdle,
            @Value("${spring.redis.local.host}") String hostName,
            @Value("${spring.redis.local.port}") int port,
            @Value("${spring.redis.local.password}") String password) {

        /* ========= 基本配置 ========= */
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setPort(port);
        configuration.setDatabase(database);
        if (!ObjectUtils.isEmpty(password)) {
            RedisPassword redisPassword = RedisPassword.of(password);
            configuration.setPassword(redisPassword);
        }
        /* ========= 连接池通用配置 ========= */
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        /* ========= jedis pool ========= */
        /*
        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder builder = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        builder.connectTimeout(Duration.ofSeconds(timeout));
        builder.usePooling();
        builder.poolConfig(genericObjectPoolConfig);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration, builder.build());
        // 连接池初始化
        connectionFactory.afterPropertiesSet();
        */
        /* ========= lettuce pool ========= */
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        builder.commandTimeout(Duration.ofSeconds(timeout));
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
        connectionFactory.afterPropertiesSet();
        /* ========= 创建 template ========= */
        RedisTemplate redisTemplate = createRedisTemplate(connectionFactory);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
    /**
     * 本地数据源 redis template
     *
     * @param database
     * @param timeout
     * @param maxActive
     * @param maxWait
     * @param maxIdle
     * @param minIdle
     * @param hostName
     * @param port
     * @param password
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate2(
            @Value("${spring.redis.local.database}") int database,
            @Value("${spring.redis.timeout}") long timeout,
            @Value("${spring.redis.lettuce.pool.max-active}") int maxActive,
            @Value("${spring.redis.lettuce.pool.max-wait}") int maxWait,
            @Value("${spring.redis.lettuce.pool.max-idle}") int maxIdle,
            @Value("${spring.redis.lettuce.pool.min-idle}") int minIdle,
            @Value("${spring.redis.local.host}") String hostName,
            @Value("${spring.redis.local.port}") int port,
            @Value("${spring.redis.local.password}") String password) {

        /* ========= 基本配置 ========= */
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setPort(port);
        configuration.setDatabase(database);
        if (!ObjectUtils.isEmpty(password)) {
            RedisPassword redisPassword = RedisPassword.of(password);
            configuration.setPassword(redisPassword);
        }
        /* ========= 连接池通用配置 ========= */
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
        /* ========= jedis pool ========= */
        /*
        JedisClientConfiguration.DefaultJedisClientConfigurationBuilder builder = (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration
                .builder();
        builder.connectTimeout(Duration.ofSeconds(timeout));
        builder.usePooling();
        builder.poolConfig(genericObjectPoolConfig);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration, builder.build());
        // 连接池初始化
        connectionFactory.afterPropertiesSet();
        */
        /* ========= lettuce pool ========= */
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        builder.poolConfig(genericObjectPoolConfig);
        builder.commandTimeout(Duration.ofSeconds(timeout));
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration, builder.build());
        connectionFactory.afterPropertiesSet();
        /* ========= 创建 template ========= */
        StringRedisTemplate stringRedisTemplate = createStringRedisTemplate(connectionFactory);
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }
    /**
     * 默认配置(6379) redis template
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = createRedisTemplate(redisConnectionFactory);
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = createStringRedisTemplate(redisConnectionFactory);
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }

    /**
     * json 实现 redisTemplate
     * <p>
     * 该方法不能加 @Bean 否则不管如何调用，connectionFactory都会是默认配置
     *
     * @param redisConnectionFactory
     * @return
     */
    /*public RedisTemplate createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }*/
    public RedisTemplate createRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer StringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(StringRedisSerializer);//设置默认的Serialize，包含 keySerializer & valueSerializer
        //redisTemplate.setKeySerializer(fastJsonRedisSerializer);//单独设置keySerializer
        //redisTemplate.setValueSerializer(fastJsonRedisSerializer);//单独设置valueSerializer
        redisTemplate.afterPropertiesSet();
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
    public StringRedisTemplate createStringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer StringRedisSerializer = new StringRedisSerializer();
        stringRedisTemplate.setDefaultSerializer(StringRedisSerializer);//设置默认的Serialize，包含 keySerializer & valueSerializer
        //redisTemplate.setKeySerializer(fastJsonRedisSerializer);//单独设置keySerializer
        //redisTemplate.setValueSerializer(fastJsonRedisSerializer);//单独设置valueSerializer
        stringRedisTemplate.afterPropertiesSet();
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }
    /**
     * 作者 CG
     * 时间 2019/8/5 10:57
     * 注释 redis 事务管理器
     */
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) throws SQLException {
        return new DataSourceTransactionManager(dataSource);
    }
}