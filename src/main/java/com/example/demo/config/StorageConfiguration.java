package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * StorageConfiguration
 *  1@ConfigurationProperties("storage.local")表示将从配置项storage.local读取该项的所有子项配置，
 *  2@Configuration表示StorageConfiguration也是一个Spring管理的Bean，可直接注入到其他Bean中：
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Data
@Component
@ConfigurationProperties("storage.local")
public class StorageConfiguration {

    private String rootDir;
    private int maxSize;
    private boolean allowEmpty;
    private List<String> allowTypes;

}