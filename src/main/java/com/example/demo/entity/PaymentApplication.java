package com.example.demo.entity;

import lombok.Data;

/**
 * PaymentApplication
 *
 * @author ZhangJP
 * @date 2020/8/26
 */
@Data
public class PaymentApplication {

    private Integer id;

    /**
     * 订单合同id
     */
    private Integer orderId;

    /**
     * 收入合同id
     */
    private Integer incomeId;

    /**
     * 支付方式
     */
    private String type;

    /**
     * 支付类别
     */
    private String classify;

    /**
     * 支付申请金额
     */
    private Double amount;
}
