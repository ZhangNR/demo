package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DepartmentManager
 *
 * @author ZhangJP
 * @date 2021/5/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentManager {

    @TableField("year")
    private Integer year;

    @TableField("month")
    private Integer month;

    @TableField("dept_id")
    private String deptId;

    @TableField("dept_name")
    private String deptName;

    @TableField("managers")
    private String managers;
}
