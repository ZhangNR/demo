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
@TableName("invoice_paid")
public class InvoicePaid implements Serializable {

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
     * 订单合同的id
     */
    @TableField("framework_order_id")
    private Integer frameworkOrderId;

    /**
     * 已开票金额(含税)
     */
    @TableField("amount_paid")
    private Double amountPaid;

    /**
     * 剩余金额(含税)
     */
    @TableField("balance")
    private Double balance;

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
     * 税率
     */
    @TableField("tax_rate")
    private String taxRate;

    /**
     * 申请类型
     */
    @TableField("apply_type")
    private String applyType;

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
     * 开票时间
     */
    @TableField("invoicing_time")
    private String invoicingTime;

    /**
     * 发票编号
     */
    @TableField("invoice_no")
    private String invoiceNo;

    /**
     * 发票数量
     */
    @TableField("invoice_number")
    private Integer invoiceNumber;

    /**
     * 发票金额
     */
    @TableField("invoice_amount")
    private Double invoiceAmount;

    /**
     * 税额
     */
    @TableField("tax_amount")
    private Double taxAmount;

    /**
     * 报账材料
     */
    @TableField("reimbursement_data")
    private String reimbursementData;

    /**
     * 到款时间
     */
    @TableField("payment_time")
    private String paymentTime;

    /**
     * 到款金额
     */
    @TableField("payment_amount")
    private Double paymentAmount;

    /**
     * 未到款金额
     */
    @TableField("un_payment_amount")
    private Double unPaymentAmount;

    /**
     * 拆分附件
     */
    @TableField("split_file")
    private String splitFile;

    /**
     * 拆分内容
     */
    @TableField("split")
    private String split;

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
