package com.example.demo.untils;


import com.example.demo.entity.Result;

/**
 * 通用返回结果工具
 *
 * @author ZhangJP
 */
public class Response {

    private Response() {
    }

    public static Result success(String msg) {
        return new Result(0, msg, null);
    }

    public static Result success(String msg, Object data) {
        return new Result(0, msg, data);
    }

    public static Result fail(String msg) {
        return new Result(1, msg, null);
    }

    public static Result fail(String msg, Object data) {
        return new Result(1, msg, data);
    }


}
