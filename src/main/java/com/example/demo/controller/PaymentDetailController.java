package com.example.demo.controller;


import com.example.demo.entity.PaymentSplitParams;
import com.example.demo.service.IPaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 到款详情 前端控制器
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/payment")
public class PaymentDetailController {

    private final IPaymentDetailService service;

    @Autowired
    public PaymentDetailController(IPaymentDetailService service) {
        this.service = service;
    }

    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody PaymentSplitParams params) {
        service.export(response, params);
    }

}
