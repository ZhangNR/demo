package com.example.demo.service;

import com.example.demo.entity.PaymentDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PaymentSplitParams;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 到款详情 服务类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
public interface IPaymentDetailService extends IService<PaymentDetail> {

    /**
     * 导出开票记录
     *
     * @param response /
     * @param params   /
     */
    void export(HttpServletResponse response, PaymentSplitParams params);
}
