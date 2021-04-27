package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ZhangJP
 */
@Data
public class OrderContractProjectVO {

    private static final long serialVersionUID = 1L;

    private Integer orderContractProjectId;
    /**
     * 项目编号
     */
    private String projectNum;
    /**
     * 项目金额
     */
    private Double projectMoney;
    /**
     * 合计项目金额（累加增补合同金额）
     */
    private Double sumProjectMoney;
    /**
     * 已开票费用
     */
    private BigDecimal amountPaid;
    /**
     * 已开票费用
     */
    private BigDecimal balance;
    /**
     * 本次申请金额
     */
    private BigDecimal applyAmount;
    /**
     * 是否结算完毕(传递0/1 显示时处理成是/否)
     */
    private Integer settlementCompleted;

}
