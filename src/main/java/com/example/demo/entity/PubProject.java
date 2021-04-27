package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@TableName("pub_project")
public class PubProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * project_approval表的id
     */
    @TableField("project_approval_id")
    private Integer projectApprovalId;

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

    @TableField("change_time")
    private LocalDateTime changeTime;


}
