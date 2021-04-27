package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjp
 * @since 2020-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("monthly_report_single_project")
public class MonthlyReportSingleProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 大项
     */
    @TableField("parent_project")
    private String parentProject;

    /**
     * 单点名称
     */
    @TableField("name")
    private String name;

    /**
     * 年
     */
    @TableField("year")
    private Integer year;

    /**
     * 月
     */
    @TableField("month")
    private Integer month;

    /**
     * 勘察日期
     */
    @TableField("survey_date")
    private LocalDate surveyDate;

    /**
     * 专业
     */
    @TableField("major")
    private String major;

    /**
     * 设计费用
     */
    @TableField("design_money")
    private Double designMoney;

    /**
     * 设计人员
     */
    @TableField("designer_team")
    private String designerTeam;

    @TableField("files")
    private String files;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 状态
     */
    @TableField("state")
    private String state;

    /**
     * 出版状态
     */
    @TableField("pub_state")
    private String pubState;

    @TableField("dp_id")
    private Integer dpId;

    /**
     * 关联的勘察id
     */
    @TableField("survey_id")
    private Integer surveyId;

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
