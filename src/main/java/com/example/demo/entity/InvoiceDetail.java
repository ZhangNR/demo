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
 * 发票详情
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("invoice_detail")
public class InvoiceDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 开票申请审批id
     */
    @TableField("invoice_apply_instance_id")
    private String invoiceApplyInstanceId;

    /**
     * 发票编号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 发票金额
     */
    @TableField("invoice_amount")
    private BigDecimal invoiceAmount;

    @TableField("create_time")
    private LocalDateTime createTime;


}
