package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Department
 *
 * @author ZhangJP
 * @date 2020/8/5
 */
@Data
public class TreeDepartment {

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    private List<SubDepartment> children;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubDepartment {
        private static final long serialVersionUID = 1L;

        /**
         * 项目组id
         */
        private String teamId;

        /**
         * 项目组名称
         */
        private String teamName;


    }

}
