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
 * <p>
 * Voucher Entry
 * </p>
 *
 * @author zjp
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode()
@TableName("expense_entry_2020")
public class ExpenseEntry2020 implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 报销状态
     */
    @TableField("status")
    private String status;

    /**
     * 提交者ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 提交者姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 提交者所属部门ID
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 提交者所属部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 提交者所属项目组ID
     */
    @TableField("sub_dept_id")
    private String subDeptId;

    /**
     * 提交者所属项目组名称
     */
    @TableField("sub_dept_name")
    private String subDeptName;

    /**
     * 报销年份
     */
    @TableField("year")
    private LocalDateTime year;

    /**
     * 报销月份
     */
    @TableField("month")
    private Integer month;

    /**
     * 报销填报总金额
     */
    @TableField("expense_sum")
    private BigDecimal expenseSum;

    /**
     * 上月借款
     */
    @TableField("last_loan")
    private BigDecimal lastLoan;

    /**
     * 本次借款
     */
    @TableField("this_loan")
    private BigDecimal thisLoan;

    /**
     * 合计报销金额
     */
    @TableField("total_sum")
    private BigDecimal totalSum;

    /**
     * 实际报销金额
     */
    @TableField("reality_sum")
    private BigDecimal realitySum;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 报销审批编号
     */
    @TableField("voucher_num")
    private String voucherNum;

    /**
     * 报销审批状态
     */
    @TableField("voucher_statue")
    private String voucherStatue;

    /**
     * 拒绝时的意见
     */
    @TableField("voucher_comment")
    private String voucherComment;

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
