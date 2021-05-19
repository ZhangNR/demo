package com.example.demo.others.npe;

/**
 * Client
 * 使用范例，通过这种模式，我们不再需要进行对象的判空操作，而是可以直接使用对象，也不必担心 NPE（NullPointerException）的问题
 *
 * @author ZhangJP
 * @date 2021/5/14
 */
public class Client {

    public void test(DependencyBase dependencyBase) {
        Factory.get(dependencyBase).operation();
    }

}
