package com.credgenics.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;

@Configuration
public class RedisConfig {

//    @Value("${redis.host}")
//    private String redisHost;
//
//    @Value("${redis.port}")
//    private String redisPort;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
//
//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, parseInt(redisPort)));
//    }

//    @Bean
//    public void createRedisConnection(){
//        RedisClient client = RedisClient.create(RedisURI.create("localhost", 6379));
//        client.setDefaultTimeout(20, TimeUnit.SECONDS);
//        StatefulRedisConnection<String, String> connection = client.connect();
//        RedisStringCommands sync = connection.sync();
//        String value = sync.get("bht_1");
//        System.out.println("REDIS:: bht_1 >> " + value);
//
//        connection.close();
//        client.shutdown();
//    }

//    RedisClient client = RedisClient.create(RedisURI.create("localhost", 6379));
}
