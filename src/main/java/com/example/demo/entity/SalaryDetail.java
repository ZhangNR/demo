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
 * 记录公司员工逐月的工资信息
 * </p>
 *
 * @author zjp
 * @since 2020-09-25
 */
@Data
@EqualsAndHashCode()
@TableName("salary_detail")
public class SalaryDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增长ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年信息
     */
    @TableField("year")
    private LocalDateTime year;

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
     * 员工身份证号码
     */
    @TableField("id_card_no")
    private String idCardNo;

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
     * 员工状态
     */
    @TableField("employee_status")
    private String employeeStatus;

    /**
     * 工资属性
     */
    @TableField("salary_attribute")
    private String salaryAttribute;

    /**
     * 是否是当月入职的新员工
     */
    @TableField("is_new")
    private String isNew;

    /**
     * 工资卡开户行
     */
    @TableField("account_bank")
    private String accountBank;

    /**
     * 工资卡开户行账号
     */
    @TableField("bank_account_no")
    private String bankAccountNo;

    /**
     * 当月实际工作天数
     */
    @TableField("actual_working_days")
    private BigDecimal actualWorkingDays;

    /**
     * 当月请假总天数
     */
    @TableField("vacation_sum")
    private BigDecimal vacationSum;

    /**
     * 当月事假天数
     */
    @TableField("absence_leave")
    private BigDecimal absenceLeave;

    /**
     * 考核系数
     */
    @TableField("examine_point")
    private BigDecimal examinePoint;

    /**
     * 职级（岗位）工资
     */
    @TableField("grade_basic_salary")
    private BigDecimal gradeBasicSalary;

    /**
     * 综合绩效奖金（行政）/ 职级奖金（生产）
     */
    @TableField("bonus")
    private BigDecimal bonus;

    /**
     * 通信补贴
     */
    @TableField("communication_allowance")
    private BigDecimal communicationAllowance;

    /**
     * 差旅补贴
     */
    @TableField("travel_allowance")
    private BigDecimal travelAllowance;

    /**
     * 电脑补贴（技术）
     */
    @TableField("computer_allowance")
    private BigDecimal computerAllowance;

    /**
     * 高温补贴
     */
    @TableField("high_temperature_allowance")
    private BigDecimal highTemperatureAllowance;

    /**
     * 车辆补贴
     */
    @TableField("vehicle_allowance")
    private BigDecimal vehicleAllowance;

    /**
     * 职务/特殊津贴
     */
    @TableField("special_allowance")
    private BigDecimal specialAllowance;

    /**
     * 缺勤扣款
     */
    @TableField("absence_deduction")
    private BigDecimal absenceDeduction;

    /**
     * 绩效工资
     */
    @TableField("merit_pay")
    private BigDecimal meritPay;

    /**
     * 季度奖金
     */
    @TableField("quarterly_bonus")
    private BigDecimal quarterlyBonus;

    /**
     * 年度奖金
     */
    @TableField("annual_bonus")
    private BigDecimal annualBonus;

    /**
     * 本月调整1
     */
    @TableField("manual_adjustment_1")
    private BigDecimal manualAdjustment1;

    /**
     * 本月调整1的说明
     */
    @TableField("manual_adjustment_note_1")
    private String manualAdjustmentNote1;

    /**
     * 本月调整2
     */
    @TableField("manual_adjustment_2")
    private BigDecimal manualAdjustment2;

    /**
     * 本月调整2的说明
     */
    @TableField("manual_adjustment_note_2")
    private String manualAdjustmentNote2;

    /**
     * 应发工资
     */
    @TableField("gross_pay")
    private BigDecimal grossPay;

    /**
     * 非薪酬性调整
     */
    @TableField("other_adjustment")
    private BigDecimal otherAdjustment;

    /**
     * 应发工资合计
     */
    @TableField("gross_pay_sum")
    private BigDecimal grossPaySum;

    /**
     * 社保（个人部分）
     */
    @TableField("social_insurance")
    private BigDecimal socialInsurance;

    /**
     * 公积金（个人部分）
     */
    @TableField("housing_fund")
    private BigDecimal housingFund;

    /**
     * 五险一金合计
     */
    @TableField("insurance_fund_sum")
    private BigDecimal insuranceFundSum;

    /**
     * 税前应发
     */
    @TableField("before_tax_payment")
    private BigDecimal beforeTaxPayment;

    /**
     * 个人所得税
     */
    @TableField("personal_income_tax")
    private BigDecimal personalIncomeTax;

    /**
     * 税后实发
     */
    @TableField("after_tax_payment")
    private BigDecimal afterTaxPayment;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField("change_date")
    private LocalDateTime changeDate;


}
