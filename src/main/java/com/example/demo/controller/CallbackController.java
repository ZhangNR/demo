package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.CallbackService;
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
public class CallbackController {

    private final CallbackService callbackService;

    @Autowired
    public CallbackController(CallbackService callbackService) {
        this.callbackService = callbackService;
    }

    @PostMapping("invoice")
    public void invoice(@RequestBody JSONObject json) {
        callbackService.invoice(json);

    }
}
