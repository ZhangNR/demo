package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 记录审批表单内容
 *
 * @author zjp
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("invoice_apply")
public class InvoiceApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 归属部门id
     */
    @TableField("pt_dept_id")
    private String ptDeptId;

    /**
     * 归属部门名称
     */
    @TableField("pt_dept_name")
    private String ptDeptName;

    /**
     * 归属项目组id
     */
    @TableField("pt_sub_dept_id")
    private String ptSubDeptId;

    /**
     * 归属项目组名称
     */
    @TableField("pt_sub_dept_name")
    private String ptSubDeptName;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 开票名称
     */
    @TableField("invoicing_name")
    private String invoicingName;

    /**
     * 申请类型
     */
    @TableField("apply_type")
    private String applyType;

    /**
     * 税率
     */
    @TableField("tax_rate")
    private String taxRate;

    /**
     * 订单合同的id集合
     */
    @TableField("order_contract_id")
    private Integer orderContractId;

    /**
     * 订单合同总金额
     */
    @TableField("sum_amount_order")
    private BigDecimal sumAmountOrder;

    /**
     * 申请总金额
     */
    @TableField("sum_amount_apply")
    private BigDecimal sumAmountApply;

    /**
     * 简易征收项目
     */
    @TableField("collection_project")
    private String collectionProject;

    /**
     * 凭证
     */
    @TableField("attachment")
    private String attachment;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 合作比例
     */
    @TableField("ratio")
    private String ratio;

    /**
     * 客户信息表id
     */
    @TableField("customer_id")
    private Integer customerId;

    /**
     * 公司名称
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 税号
     */
    @TableField("tax_number")
    private String taxNumber;

    /**
     * 开户行
     */
    @TableField("bank_name")
    private String bankName;

    /**
     * 银行账号
     */
    @TableField("bank_account")
    private String bankAccount;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 发票编号
     */
    @TableField("invoicing_time")
    private String invoicingTime;

    /**
     * 发票编号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 税额
     */
    @TableField("tax_amount")
    private BigDecimal taxAmount;

    /**
     * 报账材料
     */
    @TableField("reimbursement_data")
    private String reimbursementData;

    /**
     * 到款进度
     */
    @TableField("payment_progress")
    private String paymentProgress;

    /**
     * 到款总金额
     */
    @TableField("sum_payment_amount")
    private BigDecimal sumPaymentAmount;

    /**
     * 是否全部到款
     */
    @TableField("is_all")
    private Boolean isAll;

    /**
     * 创建人id/发起人
     */
    @TableField("creator_id")
    private String creatorId;

    /**
     * 创建人姓名
     */
    @TableField("creator_name")
    private String creatorName;

    /**
     * 创建人部门id
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 审批编号
     */
    @TableField("voucher_num")
    private String voucherNum;

    /**
     * 审批URL
     */
    @TableField("voucher_url")
    private String voucherUrl;

    /**
     * 创建日期
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    @TableField("change_time")
    private LocalDateTime changeTime;


}
