package com.example.demo.untils;

import com.example.demo.entity.Result;
import com.example.demo.entity.ResultCode;

/**
 * 响应结果生成工具
 *
 * @author ZhangJP
 * @date 2020/10/9
 */
public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    public static Result genFailResult(String message) {
        Result result = new Result();
        result.setCode(ResultCode.FAIL);
        result.setMessage(message);
        return result;
    }
}
