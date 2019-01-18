package com.duia.english.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Arrays;

/**
 * redis @Cache的key生成规则 redis连接池配置
 * redis模板 默认15号库获取微信token/ticket使用,切换库需再次构建
 * redisConnectionFactory 实例化
 * JedisPoolConfig 实例化
 * Created by xiaochao on 2018/8/2.
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.pool.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.redis.pool.maxWaitMillis}")
    private int maxWaitMillis;
    @Value("${spring.redis.pool.maxTotal}")
    private int maxTotal;


    /**
     *  注解@Cache key生成规则
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (target,method,params)->{
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName())
                .append("_").append(method.getName());
            Arrays.stream(params).forEach(obj->
                sb.append("_").append(obj.toString())
            );
            return sb;
        };
    }



    /**
     * redis模板  15号库获取微信分享的 tiket;token StringRedisSerializer
     * @Description:
     * @param factory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;

    }


    /**
     * redis模板 Jackson2JsonRedisSerializer
     * @Description:
     * @param factory
     * @return
     */
    @Bean(name = "englishRedisTemplate")
    public RedisTemplate<?,?> EnglishRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;

    }



    /**
     * 避免使用过时api JedisConnectionFactory setPassword等
     * redis连接的基础设置
     * @Description:
     * @return
     */
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));//  connection timeout
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());
        return factory;
    }

    /**
     * 连接池配置
     * @Description:
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setMaxTotal(maxTotal);
        return jedisPoolConfig;
    }

    /**
     * redis数据操作异常处理
     * @return
     */
//    @Bean
//    @Override
//    public CacheErrorHandler errorHandler() {
//        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
//            @Override
//            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
//                logger.error("redis异常：key=[{}]",key,e);
//            }
//
//            @Override
//            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
//                logger.error("redis异常：key=[{}]",key,e);
//            }
//
//            @Override
//            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key)    {
//                logger.error("redis异常：key=[{}]",key,e);
//            }
//
//            @Override
//            public void handleCacheClearError(RuntimeException e, Cache cache) {
//                logger.error("redis异常：",e);
//            }
//        };
//        return cacheErrorHandler;
//    }

}
