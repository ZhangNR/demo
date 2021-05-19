package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Rent
 *
 * @author ZhangJP
 * @date 2021/5/8
 */
@Data
public class RentVO {

    @TableField("dept_id")
    private String deptId;

    @TableField("team_id")
    private String teamId;

    @TableField("rentAmount")
    private BigDecimal rentAmount;

}
