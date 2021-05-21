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
@TableName("project_approval_project")
public class ProjectApprovalProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年份
     */
    @TableField("year")
    private Integer year;

    /**
     * 合作比例
     */
    @TableField("cooperate_ratio")
    private String cooperateRatio;

    /**
     * 项目名称
     */
    @TableField("name")
    private String name;

    /**
     * 项目类型
     */
    @TableField("type")
    private String type;

    /**
     * 项目属性
     */
    @TableField("attribute")
    private String attribute;

    /**
     * 部门id
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 部门名
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 项目组id
     */
    @TableField("sub_dept_id")
    private String subDeptId;

    /**
     * 项目组名
     */
    @TableField("sub_dept_name")
    private String subDeptName;

    /**
     * 客户名称
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 客户联系人
     */
    @TableField("customer_contact_name")
    private String customerContactName;

    /**
     * 客户联系电话
     */
    @TableField("customer_contact_phone")
    private String customerContactPhone;

    /**
     * 立项依据类型
     */
    @TableField("basis_type")
    private String basisType;

    /**
     * 立项依据附件路径
     */
    @TableField("basis_files")
    private String basisFiles;

    /**
     * 项目收入
     */
    @TableField("project_income")
    private Double projectIncome;

    /**
     * 自有收入
     */
    @TableField("self_income")
    private Double selfIncome;

    /**
     * 审批编号
     */
    @TableField("voucher_num")
    private String voucherNum;

    /**
     * 创建人id
     */
    @TableField("creator_id")
    private String creatorId;

    /**
     * 创建人名
     */
    @TableField("creator_name")
    private String creatorName;

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
