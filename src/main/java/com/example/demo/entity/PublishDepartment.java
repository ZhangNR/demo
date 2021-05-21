package com.example.demo.entity;

import lombok.Data;

/**
 * Department
 *
 * @author ZhangJP
 * @date 2020/8/5
 */
@Data
public class PublishDepartment {

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 项目组id
     */
    private Long subDeptId;

    /**
     * 项目组名称
     */
    private String subDeptName;

    /**
     * 是不是该部门的领导
     */
    private Boolean isLeader;

}
