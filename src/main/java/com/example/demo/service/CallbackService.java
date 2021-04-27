package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;

/**
 * CallbackService
 *
 * @author ZhangJP
 * @date 2020/8/26
 */
public interface CallbackService {

    void invoice(JSONObject json);
}
