package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ExpensePenson
 *
 * @author ZhangJP
 * @date 2020/10/30
 */
@Data
public class ExpenseVO {

    @TableField("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("dept_id")
    private String deptId;

    @TableField("sub_dept_id")
    private String subDeptId;

    @TableField("amount")
    private BigDecimal amount;

}
