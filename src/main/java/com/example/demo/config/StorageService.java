package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * a
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Slf4j
public class StorageService {

    @Autowired
    StorageConfiguration storageConfig;

    @PostConstruct
    public void init() {
        log.info("Load configuration: root-dir = {}", storageConfig.getRootDir());
        log.info("Load configuration: max-size = {}", storageConfig.getMaxSize());
        log.info("Load configuration: allowed-types = {}", storageConfig.getAllowTypes());
    }
}