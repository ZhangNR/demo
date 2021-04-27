package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * PubProjectVO
 *
 * @author ZhangJP
 * @date 2020/7/28
 */
@Data
public class PubProjectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    /**
     * 出版文件id
     */
    @TableId("pub_id")
    private Integer pubId;

    /**
     * 部门id
     */
    @TableId("dept_id")
    private Long deptId;

    /**
     * 部门名称
     */
    @TableId("dept_name")
    private String deptName;

    /**
     * 项目组id
     */
    @TableId("sub_dept_id")
    private Long subDeptId;

    /**
     * 项目组id
     */
    @TableId("sub_dept_name")
    private String subDeptName;

    /**
     * 合作方项目编号
     */
    @TableId("partner_num")
    private String partnerNum;

    /**
     * 项目名称
     */
    @TableId("name")
    private String name;

    /**
     * 单册的编号
     */
    @TableField("pub_project_num")
    private String pubProjectNum;

    /**
     * 单册名称
     */
    @TableField("pub_project_name")
    private String pubProjectName;

    /**
     * 出版文件的状态
     */
    @TableField("state")
    private String state;

    /**
     * 单点的id集合
     */
    @TableField("single_project_ids")
    private String singleProjectIds;

    /**
     * 单点集合
     */
    @TableField(exist = false)
    private List<MonthlyReportSingleProject> singleProjects;

    /**
     * 月度报表预估产值
     */
    @TableField("sum_design_money")
    private Double sumDesignMoney;

    /**
     * 出版勘察设计费（降点含税）
     */
    @TableField("publish_money")
    private Double publishMoney;

    /**
     * 偏差/误差
     */
    @TableField("deviation")
    private Double deviation;

    /**
     * 创建人id
     */
    @TableField("creator_id")
    private String creatorId;

    /**
     * 创建人姓名
     */
    @TableField("creator_name")
    private String creatorName;

    public PubProject toPubProject() {
        PubProject project = new PubProject();
        project.setId(this.pubId);
        project.setProjectApprovalId(this.id);
        project.setPubProjectNum(this.pubProjectNum);
        project.setPubProjectName(this.pubProjectName);
        project.setState(this.state);
        project.setSingleProjectIds(this.singleProjectIds);
        project.setSumDesignMoney(this.sumDesignMoney);
        project.setPublishMoney(this.publishMoney);
        project.setDeviation(this.deviation);
        project.setCreatorId(this.creatorId);
        project.setCreatorName(this.creatorName);
        return project;
    }

    public void setPubProject(PubProject project) {
        this.pubId = project.getId();
        this.id = project.getProjectApprovalId();
        this.pubProjectNum = project.getPubProjectNum();
        this.pubProjectName = project.getPubProjectName();
        this.state = project.getState();
        this.singleProjectIds = project.getSingleProjectIds();
        this.sumDesignMoney = project.getSumDesignMoney();
        this.publishMoney = project.getPublishMoney();
        this.deviation = project.getDeviation();
        this.creatorId = project.getCreatorId();
        this.creatorName = project.getCreatorName();
    }

}
