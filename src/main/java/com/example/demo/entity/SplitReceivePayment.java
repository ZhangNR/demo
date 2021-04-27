package com.example.demo.entity;

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
 * 
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("split_receive_payment")
public class SplitReceivePayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("invoice_id")
    private Integer invoiceId;

    @TableField("belong_dept_id")
    private String belongDeptId;

    @TableField("belong_dept_name")
    private String belongDeptName;

    @TableField("belong_sub_dept_id")
    private String belongSubDeptId;

    @TableField("belong_sub_dept_name")
    private String belongSubDeptName;

    @TableField("payment_time")
    private String paymentTime;

    @TableField("payment_amount")
    private Double paymentAmount;

    @TableField("create_time")
    private LocalDateTime createTime;


}
