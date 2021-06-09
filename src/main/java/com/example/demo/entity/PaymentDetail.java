package com.example.demo.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 到款详情
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("payment_detail")
public class PaymentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 开票申请id
     */
    @TableField("invoice_apply_id")
    private Integer invoiceApplyId;

    /**
     * 到款时间
     */
    @TableField("payment_time")
    private LocalDate paymentTime;

    /**
     * 本次到款金额
     */
    @TableField("payment_amount")
    private BigDecimal paymentAmount;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 自有金额
     */
    @TableField("own_amount")
    private BigDecimal ownAmount;

    /**
     * 外部合作金额
     */
    @TableField("external_amount")
    private BigDecimal externalAmount;

    /**
     * 附件
     */
    @TableField("annex")
    private String annex;

    /**
     * 备注
     */
    @TableField("split_note")
    private String splitNote;

    /**
     * 到款审批编号
     */
    @TableField("voucher_num")
    private String voucherNum;

    /**
     * 到款审批url
     */
    @TableField("voucher_url")
    private String voucherUrl;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("change_time")
    private LocalDateTime changeTime;


}
