package com.example.demo.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RedisConfiguration
 * 加载Redis配置
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Configuration
@ConfigurationProperties("spring.redis")
@Data
public class RedisConfiguration {
    private String host;
    private int port;
    private String password;
    private int database;

    // getters and setters...

    @Bean
    RedisClient redisClient() {
        RedisURI uri = RedisURI.Builder.redis(this.host, this.port)
                .withPassword(this.password)
                .withDatabase(this.database)
                .build();
        return RedisClient.create(uri);
    }
}