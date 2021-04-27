package com.example.demo.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 记录审批表单内容
 * </p>
 *
 * @author zjp
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InvoicePaidVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发起人部门id
     */
    private String deptId;

    /**
     * 创建人id/发起人
     */
    private String creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 开票名称
     */
    private String invoicingName;

    /**
     * 订单合同id
     */
    private Integer orderContractId;

    /**
     * 订单合同总金额
     */
    private BigDecimal sumOrderAmount;

    /**
     * 申请总金额
     */
    private BigDecimal sumApplyAmount;

    /**
     * 税率
     */
    private String taxRate;

    /**
     * 申请类型
     */
    private String applyType;

    /**
     * 简易征收项目
     */
    private String collectionProject;

    /**
     * 凭证
     */
    private List<DingFile> attachment;

    /**
     * 备注
     */
    private String note;

    /**
     * 订单合同信息
     */
    private OrderContract order;

    /**
     * 订单合同项目
     */
    private List<OrderContractProjectVO> projectList;

    /**
     * 项目归属部门id
     */
    private String ptDeptId;

    /**
     *  项目归属部门名称
     */
    private String ptDeptName;

    /**
     * 项目归属项目组id
     */
    private String ptSubDeptId;

    /**
     * 项目归属项目组名称
     */
    private String ptSubDeptName;

    /**
     * 合作比例
     */
    private String ratio;

    /**
     * 是否归属生产部门
     */
    private String ptProductionDept;

    /**
     * 负责人userid
     */
    private String principalUserId;

    /**
     * 客户信息id
     */
    private Integer customerId;

    /**
     * 公司名称
     */
    private String customerName;

    /**
     * 税号
     */
    private String taxNumber;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;


    public InvoiceApply getInvoicePaid() {
        InvoiceApply invoicePaid = new InvoiceApply();
        invoicePaid.setPtDeptId(this.ptDeptId);
        invoicePaid.setPtDeptName(this.ptDeptName);
        invoicePaid.setPtSubDeptId(this.ptSubDeptId);
        invoicePaid.setPtSubDeptName(this.ptSubDeptName);

        invoicePaid.setInvoicingName(this.invoicingName);
        invoicePaid.setApplyType(this.applyType);
        invoicePaid.setTaxRate(this.taxRate);
        invoicePaid.setOrderContractId(this.orderContractId);
        invoicePaid.setSumAmountOrder(this.sumOrderAmount);
        invoicePaid.setSumAmountApply(this.sumApplyAmount);
        invoicePaid.setCollectionProject(this.collectionProject);
        invoicePaid.setAttachment(JSON.toJSONString(this.attachment));
        invoicePaid.setNote(this.note);

        invoicePaid.setRatio(this.ratio);
        invoicePaid.setCustomerId(this.customerId);
        invoicePaid.setCustomerName(this.customerName);
        invoicePaid.setTaxNumber(this.taxNumber);
        invoicePaid.setBankName(this.bankName);
        invoicePaid.setBankAccount(this.bankAccount);
        invoicePaid.setAddress(this.address);
        invoicePaid.setPhone(this.phone);

        invoicePaid.setCreatorId(this.creatorId);
        invoicePaid.setCreatorName(this.creatorName);
        invoicePaid.setDeptId(this.deptId);
        return invoicePaid;
    }

}
