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
@TableName("order_contract")
public class OrderContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目归属部门
     */
    @TableField("pt_dept_id")
    private String ptDeptId;

    @TableField("pt_dept_name")
    private String ptDeptName;

    /**
     * 项目归属项目组
     */
    @TableField("pt_sub_dept_id")
    private String ptSubDeptId;

    @TableField("pt_sub_dept_name")
    private String ptSubDeptName;

    /**
     * 合同类型
     */
    @TableField("type")
    private String type;

    /**
     * 合同类别
     */
    @TableField("category")
    private String category;

    /**
     * 框架合同id
     */
    @TableField("framework_id")
    private Integer frameworkId;

    /**
     * 框架合同编号
     */
    @TableField("framework_num")
    private String frameworkNum;

    /**
     * 框架合同名称
     */
    @TableField("framework_name")
    private String frameworkName;

    /**
     * 框架合同金额
     */
    @TableField("framework_money")
    private Double frameworkMoney;

    /**
     * 订单合同编号
     */
    @TableField("num")
    private String num;

    /**
     * 合作方项目
     */
    @TableField("project")
    private String project;

    /**
     * 关联立项
     */
    @TableField("projects")
    private String projects;

    /**
     * 关联出版
     */
    @TableField("publishs")
    private String publishs;

    /**
     * 订单合同名称
     */
    @TableField("name")
    private String name;

    /**
     * 合同金额
     */
    @TableField("money")
    private Double money;

    /**
     * 合作比例
     */
    @TableField("cooperation_ratio")
    private Double cooperationRatio;

    /**
     * 税率
     */
    @TableField("tax_rate")
    private String taxRate;

    /**
     * 立项id
     */
    @TableField("p_id")
    private Integer pId;

    /**
     * 合作方名称
     */
    @TableField("partner_name")
    private String partnerName;

    /**
     * 合作方联系人
     */
    @TableField("partner_contact_name")
    private String partnerContactName;

    /**
     * 联系电话
     */
    @TableField("partner_contact_phone")
    private String partnerContactPhone;

    /**
     * 地址
     */
    @TableField("partner_address")
    private String partnerAddress;

    /**
     * 税号
     */
    @TableField("tax_num")
    private String taxNum;

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
     * 生产部门领导
     */
    @TableField("produce_leaders")
    private String produceLeaders;

    /**
     * 合同签订时间
     */
    @TableField("sign_date")
    private LocalDateTime signDate;

    /**
     * 电子归档时间
     */
    @TableField("electronic_file_date")
    private LocalDateTime electronicFileDate;

    /**
     * 纸质归档时间
     */
    @TableField("paper_file_date")
    private LocalDateTime paperFileDate;

    /**
     * 纸质归档编号
     */
    @TableField("paper_file_num")
    private String paperFileNum;

    /**
     * 提交人id
     */
    @TableField("creator_id")
    private String creatorId;

    /**
     * 提交人名
     */
    @TableField("creator_name")
    private String creatorName;

    /**
     * 提交人部门id
     */
    @TableField("creator_dept_id")
    private String creatorDeptId;

    /**
     * 审批编号
     */
    @TableField("voucher_num")
    private String voucherNum;

    /**
     * 审批url
     */
    @TableField("voucher_url")
    private String voucherUrl;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @TableField("update_date")
    private LocalDateTime updateDate;


}
