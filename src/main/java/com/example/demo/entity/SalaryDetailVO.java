package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 记录公司员工逐月的工资信息
 * </p>
 *
 * @author zjp
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode()
public class SalaryDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 年信息
     */
    @TableField("year")
    private Integer year;

    /**
     * 月信息
     */
    @TableField("month")
    private Integer month;

    /**
     * 员工ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 员工姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 员工的部门ID
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 员工部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 员工的项目组ID（如果有）
     */
    @TableField("team_id")
    private String teamId;

    /**
     * 员工的项目组名称（如果有）
     */
    @TableField("team_name")
    private String teamName;

    /**
     * 岗位职级
     */
    @TableField("position_level")
    private String positionLevel;

    /**
     * 当月实际工作天数
     */
    @TableField("actual_working_days")
    private BigDecimal actualWorkingDays;

    /**
     * 应发工资
     */
    @TableField("gross_pay")
    private BigDecimal grossPay;

    /**
     * 应发工资合计
     */
    @TableField("gross_pay_sum")
    private BigDecimal grossPaySum;

    /**
     * 个人报销费用
     */
    private BigDecimal expensePerson;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;


}
