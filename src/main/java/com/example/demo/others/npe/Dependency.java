package com.example.demo.others.npe;

import lombok.extern.slf4j.Slf4j;

/**
 * Dependency
 * 该对象的真实类，实现了业务行为接口 DependencyBase 与空对象操作接口 Nullable
 *
 * @author ZhangJP
 * @date 2021/5/14
 */
@Slf4j
public class Dependency implements DependencyBase, Nullable {

    @Override
    public void operation() {
        log.info("Test!");
    }

    @Override
    public boolean isNull() {
        return false;
    }

}
