package com.example.demo.entity;

import lombok.Data;

/**
 * 统一API响应结果封装
 *
 * @author ZhangJP
 * @date 2020/10/9
 */
@Data
public class Result {

    private int code;
    private String message;
    private Object data;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }
}
