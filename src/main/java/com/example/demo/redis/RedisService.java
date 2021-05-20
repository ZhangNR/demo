package com.example.demo.redis;

import com.example.demo.redis.SyncCommandCallback;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * RedisService
 * 封装所有的Redis操作
 * 引入了Commons Pool的一个对象池，用于缓存Redis连接。因为Lettuce本身是基于Netty的异步驱动，在异步访问时并不需要创建连接池，
 * 但基于Servlet模型的同步访问时，连接池是有必要的。
 * 连接池在@PostConstruct方法中初始化，
 * 在@PreDestroy方法中关闭
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Component
@Slf4j
public class RedisService {
    @Autowired
    RedisClient redisClient;

    private GenericObjectPool<StatefulRedisConnection<String, String>> redisConnectionPool;

    @PostConstruct
    public void init() {
        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxIdle(5);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        this.redisConnectionPool = ConnectionPoolSupport.createGenericObjectPool(() -> redisClient.connect(), poolConfig);
    }

    @PreDestroy
    public void shutdown() {
        this.redisConnectionPool.close();
        this.redisClient.shutdown();
    }

    public String set(String key, String value) {
        return executeSync(commands -> commands.set(key, value));
    }

    public String get(String key) {
        return executeSync(commands -> commands.get(key));
    }

    public boolean hset(String key, String field, String value) {
        return executeSync(commands -> commands.hset(key, field, value));
    }

    public String hget(String key, String field) {
        return executeSync(commands -> commands.hget(key, field));
    }

    public Map<String, String> hgetall(String key) {
        return executeSync(commands -> commands.hgetall(key));
    }

    private <T> T executeSync(SyncCommandCallback<T> callback) {
        try (StatefulRedisConnection<String, String> connection = redisConnectionPool.borrowObject()) {
            connection.setAutoFlushCommands(true);
            RedisCommands<String, String> commands = connection.sync();
            return callback.doInConnection(commands);
        } catch (Exception e) {
            log.warn("executeSync redis failed.", e);
            throw new RuntimeException(e);
        }
    }
}
