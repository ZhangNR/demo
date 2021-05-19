package com.example.demo.untils;

/**
 * StringUtils
 *
 * @author ZhangJP
 * @date 2021/5/19
 */
public class StringUtils {

    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }
}
