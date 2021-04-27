package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.example.demo.entity.PaymentApplication;
import com.example.demo.service.CallbackService;
import com.example.demo.untils.DingServiceApi;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CallbackServiceImpl
 *
 * @author ZhangJP
 * @date 2020/8/26
 */
@Service
public class CallbackServiceImpl implements CallbackService {

    @Override
    public void invoice(JSONObject json) {
        OapiProcessinstanceGetResponse.ProcessInstanceTopVo processinstance = DingServiceApi.getProcessinstance(json.getString("processInstanceId"), DingServiceApi.getToken());

        // 解析vo 获取所需数据

        if (processinstance == null) {
            return;
        }

        List<OapiProcessinstanceGetResponse.FormComponentValueVo> vos = processinstance.getFormComponentValues();

        PaymentApplication payment = new PaymentApplication();

        for (OapiProcessinstanceGetResponse.FormComponentValueVo vo : vos) {
            if ("订单合同编号".equals(vo.getName())) {
                payment.setOrderId(Integer.valueOf(vo.getValue()));
            }
            if ("收入合同编号".equals(vo.getName())) {
                payment.setIncomeId(Integer.valueOf(vo.getValue()));
            }
            if ("支付申请金额".equals(vo.getName())) {
                payment.setAmount(Double.valueOf(vo.getValue()));
            }
        }

        // 对payment 处理

    }


}
