package com.example.demo.entity;

/**
 * EnumMethodDemo
 *
 * @author ZhangJP
 * @date 2021/5/10
 */
public class EnumMethodDemo {
    /**
     * 枚举可以添加普通方法、静态方法、抽象方法、构造方法
     * Java 虽然不能直接为实例赋值，但是它有更优秀的解决方案：为 enum 添加方法来间接实现显示赋值。
     * 要为enum定义方法，那么必须在enum的最后一个实例尾部添加一个分号。此外，在enum中，必须先定义实例，不能将字段或方法定义在实例前面
     * 枚举的应用场景
     * 组织常量\switch 状态机\
     * public static String getTrafficInstruct(Signal signal) {
     * String instruct = "信号灯故障";
     * switch (signal) {
     * case RED:
     * instruct = "红灯停";
     * break;
     * case YELLOW:
     * instruct = "黄灯请注意";
     * break;
     * case GREEN:
     * instruct = "绿灯行";
     * break;
     * default:
     * break;
     * }
     * return instruct;
     * }
     */
    public enum Color {
        /**
         * 颜色
         */
        RED, GREEN, BLUE
    }

    public enum Size {
        /**
         * 大小
         */
        BIG, MIDDLE, SMALL
    }

}