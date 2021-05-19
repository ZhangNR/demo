package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * RentDetail
 *
 * @author ZhangJP
 * @date 2020/10/26
 */
@Data
public class RentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("dept_id")
    private String deptId;

    @TableField("dept_name")
    private String deptName;

    @TableField("team_id")
    private String teamId;

    @TableField("team_name")
    private String teamName;

    @TableField("year")
    private Integer year;

    @TableField("month")
    private Integer month;

    @TableField("rent")
    private BigDecimal rent;

    @TableField("create_time")
    private LocalDateTime createTime;
}
