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
@TableName("framework_contract")
public class FrameworkContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 合同类型
     */
    @TableField("type")
    private String type;

    /**
     * 合同编号
     */
    @TableField("num")
    private String num;

    /**
     * 合同名称
     */
    @TableField("name")
    private String name;

    /**
     * 框架合同金额
     */
    @TableField("money")
    private Double money;

    /**
     * 合作区域
     */
    @TableField("cooperation_area")
    private String cooperationArea;

    /**
     * 合作比例
     */
    @TableField("cooperation_ratio")
    private Double cooperationRatio;

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
     * 合作方联系方式
     */
    @TableField("partner_contact_phone")
    private String partnerContactPhone;

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
