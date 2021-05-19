package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Result;

/**
 * CallbackService
 *
 * @author ZhangJP
 * @date 2020/8/26
 */
public interface CallbackService {

    /**
     * 开票回调
     *
     * @param json 审批信息
     * @return result
     */
    Result invoice(JSONObject json);
}
