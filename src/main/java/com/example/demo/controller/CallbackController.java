package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Result;
import com.example.demo.service.CallbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * CallbackController
 *
 * @author ZhangJP
 * @date 2020/8/26
 */
@Controller
@RequestMapping("callback")
@Api(tags = "回调接口")
public class CallbackController {

    private final CallbackService callbackService;

    @Autowired
    public CallbackController(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @ApiOperation(value = "开票申请回调")
    @PostMapping("invoice")
    public Result invoice(@RequestBody JSONObject json) {
        return callbackService.invoice(json);
    }
}
