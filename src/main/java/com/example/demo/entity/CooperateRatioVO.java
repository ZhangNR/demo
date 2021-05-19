package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * CooperateRatioVO
 *
 * @author ZhangJP
 * @date 2021/5/8
 */
@Data
public class CooperateRatioVO {

    @TableField("id")
    private Integer id;

    @TableField("year")
    private Integer year;

    @TableField("dept_id")
    private String deptId;

    @TableField("sub_dept_id")
    private String subDeptId;

    @TableField("cooperate_ratio")
    private BigDecimal cooperateRatio;

}
