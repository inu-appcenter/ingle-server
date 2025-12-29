package com.example.ingle.global.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule()) // record 생성자 파라미터 인식
                .registerModule(new Jdk8Module()) // Optional 지원
                .registerModule(new JavaTimeModule()); // LocalDateTime 등 지원

        // Redis 캐싱 시 JSON을 원래 객체 타입으로 되살리기 위한 Type Metadata 삽입 기능
        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance, // 어떤 타입에 대해서 타입 정보를 허용할지를 검사하는 Validator -> 모든 타입
                ObjectMapper.DefaultTyping.NON_FINAL, // 어떤 클래스에 대해 타입 정보를 붙일 것인지 -> final이 아닌 모든 타입
                JsonTypeInfo.As.PROPERTY // 타입 정보를 JSON 어디에 기록할 것인가 -> @class 속성을 하나 추가
        );

        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(3))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
