package com.example.demo.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("apply_detail")
public class ApplyDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    /**
     * 开票申请审批id
     */
    @TableField("invoice_apply_instance_id")
    private String invoiceApplyInstanceId;

    /**
     * 订单合同id
     */
    @TableField("order_contract_id")
    private Integer orderContractId;

    /**
     * 申请金额
     */
    @TableField("apply_amount")
    private BigDecimal applyAmount;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    @TableField("create_time")
    private LocalDateTime createTime;


}
