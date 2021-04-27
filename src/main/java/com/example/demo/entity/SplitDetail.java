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
 * 
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("split_detail")
public class SplitDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 开票申请审批id
     */
    @TableField("invoice_apply_instance_id")
    private String invoiceApplyInstanceId;

    /**
     * 合作单位
     */
    @TableField("partner")
    private String partner;

    /**
     * 拆分金额
     */
    @TableField("split_amount")
    private BigDecimal splitAmount;

    @TableField("create_time")
    private LocalDateTime createTime;


}
