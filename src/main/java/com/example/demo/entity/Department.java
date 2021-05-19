package com.example.demo.entity;

import lombok.Data;

/**
 * Department
 *
 * @author ZhangJP
 * @date 2020/8/5
 */
@Data
public class Department {

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 项目组id
     */
    private String teamId;

    /**
     * 项目组名称
     */
    private String teamName;

}
