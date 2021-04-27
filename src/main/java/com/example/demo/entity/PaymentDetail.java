package com.example.demo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableField("invoice_apply_instance_id")
    private String invoiceApplyInstanceId;

    @TableField("payment_time")
    private LocalDateTime paymentTime;

    @TableField("payment_amount")
    private BigDecimal paymentAmount;

    @TableField("create_time")
    private LocalDateTime createTime;


}
