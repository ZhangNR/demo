package com.example.demo.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author ZhangJP
 */
@Data
public class User {

    /**
     * 钉钉用户id
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

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
     * 多部门
     */
    private List<Department> departments;

    /**
     * 职位
     */
    private String position;

    /**
     * 是否为领导
     */
    private Boolean isLeader;


}
