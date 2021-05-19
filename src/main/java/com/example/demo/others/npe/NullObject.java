package com.example.demo.others.npe;

import lombok.extern.slf4j.Slf4j;

/**
 * NullObject 模式
 * In object-oriented computer programming,
 * a null object is an object with no referenced value or with defined neutral ("null") behavior.
 * The null object design pattern describes the uses of such objects and their behavior (or lack thereof)
 * 一般的，在面向对象语言中，对对象的调用前需要使用判空检查，来判断这些对象是否为空，因为在空引用上无法调用所需方法。
 * client--> dependencyBase(operation) <-- Dependency(operation)
 *           dependencyBase(operation) <-- NullObject(operation)
 * 示例： Nullable 是空对象的相关操作接口，用于确定对象是否为空，因为在空对象模式中，对象为空会被包装成一个 Object，成为 Null Object，该对象会对原有对象的所有方法进行空实现。
 *
 * @author ZhangJP
 * @date 2021/5/14
 */
@Slf4j
public class NullObject implements DependencyBase {
    /**
     * 定义了业务对象的行为
     * 例如 吃喝睡 对应eatOperation、drinkOperation、sleepOperation，
     * eatOperation中展示实现细节
     */
    @Override
    public void operation() {
        // do something
    }

    /**
     * 空对象的相关操作接口，用于确定对象是否为空
     *
     * @return boolean
     */
    @Override
    public boolean isNull() {
        return false;
    }

}
