package com.example.demo.entity;

import java.text.MessageFormat;

/**
 * ErrorCode
 *
 * @author ZhangJP
 * @date 2021/5/10
 */
public enum ErrorCode {

    /**
     * 成功
     */
    OK(0, "成功"),

    /**
     * 错误A
     */
    ERROR_A(100, "错误A"),

    /**
     * 错误B
     */
    ERROR_B(200, "错误B");

    ErrorCode(int number, String description) {
        this.code = number;
        this.description = description;
    }
    private int code;
    private String description;
    public int getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
    public static void main(String[] args) { // 静态方法
        for (ErrorCode s : ErrorCode.values()) {
            MessageFormat.format("code {}, description {}", s.getCode(), s.getDescription());
        }
    }

}
