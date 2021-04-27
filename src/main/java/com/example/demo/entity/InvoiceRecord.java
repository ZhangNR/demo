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
@TableName("invoice_record")
public class InvoiceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 项目组id
     */
    @TableField("sub_dept_id")
    private String subDeptId;

    /**
     * 项目组名称
     */
    @TableField("sub_dept_name")
    private String subDeptName;

    /**
     * 市场部合作支付
     */
    @TableField("cooperative_payment")
    private String cooperativePayment;

    /**
     * 合作合同id
     */
    @TableField("contract_id")
    private Integer contractId;

    /**
     * 合作合同已支付金额
     */
    @TableField("amount_paid")
    private Double amountPaid;

    /**
     * 合作合同可付金额
     */
    @TableField("payable_amount")
    private Double payableAmount;

    /**
     * 收入合同ID
     */
    @TableField("income_id")
    private Integer incomeId;

    /**
     * 收入合同到款金额
     */
    @TableField("income_paid")
    private Double incomePaid;

    /**
     * 收入合同到款应付合作费用
     */
    @TableField("income_payable_expenses")
    private Double incomePayableExpenses;

    /**
     * 支付方式
     */
    @TableField("type")
    private String type;

    /**
     * 支付类型
     */
    @TableField("category")
    private String category;

    /**
     * 支付类别
     */
    @TableField("sub_category")
    private String subCategory;

    /**
     * 申请支付金额
     */
    @TableField("amount")
    private Double amount;

    /**
     * 审批状态
     */
    @TableField("state")
    private String state;

    /**
     * 银行账户名
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 银行账户
     */
    @TableField("bank_account")
    private String bankAccount;

    /**
     * 开户银行
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 开户支行
     */
    @TableField("bank_branch_name")
    private String bankBranchName;

    /**
     * 申请理由
     */
    @TableField("apply_reason")
    private String applyReason;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 附加凭证
     */
    @TableField("file_value")
    private String fileValue;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


}
