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
 * 
 * </p>
 *
 * @author zjp
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("contribution_cost")
public class ContributionCost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 钉钉用户id
     */
    @TableField("userid")
    private String userid;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 岗位职级
     */
    @TableField("position_level")
    private String positionLevel;

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
    @TableField("team_id")
    private String teamId;

    /**
     * 项目自名称
     */
    @TableField("team_name")
    private String teamName;

    /**
     * 年份
     */
    @TableField("year")
    private Integer year;

    /**
     * 月份
     */
    @TableField("month")
    private Integer month;

    /**
     * 贡献产值
     */
    @TableField("contribution")
    private BigDecimal contribution;

    /**
     * 合作比例
     */
    @TableField("cooperate_ratio")
    private String cooperateRatio;

    /**
     * 公司结算值
     */
    @TableField("settlement")
    private BigDecimal settlement;

    /**
     * 工作日
     */
    @TableField("working_day")
    private BigDecimal workingDay;

    /**
     * 人力成本/应发工资
     */
    @TableField("labor_cost")
    private BigDecimal laborCost;

    /**
     * 个人报销
     */
    @TableField("expense_person")
    private BigDecimal expensePerson;

    /**
     * 公共分摊
     */
    @TableField("expense_public")
    private BigDecimal expensePublic;

    /**
     * 驻点房租分摊
     */
    @TableField("expense_public_rent")
    private BigDecimal expensePublicRent;

    /**
     * 驻点代付分摊
     */
    @TableField("expense_public_replace")
    private BigDecimal expensePublicReplace;

    /**
     * 所内费用分摊
     */
    @TableField("expense_public_inside")
    private BigDecimal expensePublicInside;

    /**
     * 成本合计
     */
    @TableField("cost")
    private BigDecimal cost;

    /**
     * 贡献产值成本比
     */
    @TableField("contribution_productivity")
    private Double contributionProductivity;

    /**
     * 公司结算值成本比
     */
    @TableField("settlement_ratio")
    private Double settlementRatio;

    /**
     * 效益
     */
    @TableField("benefit")
    private Double benefit;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    public void setSalaryDetailData(SalaryDetailVO vo) {
        this.userid = vo.getUserId();
        this.name = vo.getUserName();
        this.positionLevel = vo.getPositionLevel();
        this.deptId = vo.getDeptId();
        this.deptName = vo.getDeptName();
        this.teamId = vo.getTeamId();
        this.teamName = vo.getTeamName();
        this.workingDay = vo.getActualWorkingDays();
        this.laborCost = vo.getGrossPaySum();
    }
}
