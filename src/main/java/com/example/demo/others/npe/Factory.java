package com.example.demo.others.npe;

/**
 * Factory
 * 在使用时，可以通过工厂调用方式来进行空对象的调用，也可以通过其他如反射的方式对对象进行调用（一般多耗时几毫秒）
 *
 * @author ZhangJP
 * @date 2021/5/14
 */
public class Factory {

    private Factory() {
    }

    public static DependencyBase get(Nullable dependencyBase) {
        if (dependencyBase == null) {
            return new NullObject();
        }
        return new Dependency();
    }
}
