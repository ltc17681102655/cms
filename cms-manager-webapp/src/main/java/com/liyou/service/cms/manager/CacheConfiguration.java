package com.liyou.service.cms.manager;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyou.framework.base.store.LocalStore;
import com.liyou.framework.base.store.Store;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.framework.redis.RedisStore;
import com.liyou.framework.redis.util.RedisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableAspectJAutoProxy
public class CacheConfiguration {

    private static final ObjectMapper OBJECT_MAPPER = JSONUtils.objectMapperCopy();

    static {
        OBJECT_MAPPER.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        OBJECT_MAPPER.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    public static final String LOCAL_CACHE = "LOCAL_CACHE";
    public static final String REDIS_CACHE = "REDIS_CACHE";

    @Bean(name = LOCAL_CACHE)
    public Store localCache(){
        return new LocalStore();
    }

    @Primary
    @Bean(name = REDIS_CACHE)
    public Store redisCache(){
        return new RedisStore();
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(JedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(OBJECT_MAPPER);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        RedisUtils.setRedisTemplate(redisTemplate);
        return redisTemplate;
    }
}