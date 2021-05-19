package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * Voucher details
 * </p>
 *
 * @author zjp
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode()
@TableName("expense_detail_2020")
public class ExpenseDetail2020 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Expense Entry ID
     */
    @TableField("expense_id")
    private Integer expenseId;

    /**
     * 填报时间
     */
    @TableField("input_date")
    private LocalDate inputDate;

    /**
     * 申请时间
     */
    @TableField("apply_date")
    private LocalDate applyDate;

    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;

    /**
     * 属性类别
     */
    @TableField("type")
    private String type;

    /**
     * 报销分类
     */
    @TableField("category")
    private String category;

    /**
     * 报销类别
     */
    @TableField("sub_category")
    private String subCategory;

    /**
     * 发票情况
     */
    @TableField("has_voucher")
    private String hasVoucher;

    /**
     * 报销金额
     */
    @TableField("amount")
    private BigDecimal amount;

    /**
     * 连接批复
     */
    @TableField("related_approve")
    private String relatedApprove;

    /**
     * 上传凭证
     */
    @TableField("audit_voucher")
    private String auditVoucher;

    /**
     * 费用备注
     */
    @TableField("note")
    private String note;

    /**
     * 结果
     */
    @TableField("result")
    private String result;

    /**
     * 拒绝理由
     */
    @TableField("refuse_msg")
    private String refuseMsg;

    /**
     * 新增日期
     */
    @TableField("date")
    private LocalDateTime date;

    /**
     * 更新日期
     */
    @TableField("change_date")
    private LocalDateTime changeDate;


}
