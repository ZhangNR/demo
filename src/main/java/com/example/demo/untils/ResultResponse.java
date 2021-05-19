package com.example.demo.untils;

import com.example.demo.entity.Result;
import com.example.demo.entity.ResultCode;

/**
 * 响应结果封装
 *
 * @author ZhangJP
 * @date 2020/10/9
 */
public class ResultResponse {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 只返回状态
     */
    public static Result genSuccessResult() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    /**
     * 成功返回数据
     */
    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     */
    public static Result genFailResult(String message) {
        Result result = new Result();
        result.setCode(ResultCode.FAIL);
        result.setMessage(message);
        return result;
    }
}
