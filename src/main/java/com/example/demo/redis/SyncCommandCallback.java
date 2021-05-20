package com.example.demo.redis;

import io.lettuce.core.api.sync.RedisCommands;

/**
 * SyncCommandCallback
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
public interface SyncCommandCallback<T> {
    /**
     * 在此操作Redis:
     */
    T doInConnection(RedisCommands<String, String> commands);


}
