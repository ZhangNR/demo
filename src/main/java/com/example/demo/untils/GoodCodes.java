package com.example.demo.untils;

/**
 * GoodCodes
 *
 * @author ZhangJP
 * @date 2020/10/15
 */
public class GoodCodes {

    public void useParamsInside() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (int i = 0; i < 1000000000; i++) {
            a++;
        }
        long useTime = System.currentTimeMillis() - start;
        System.out.println("useTime:" + useTime);
    }

}
