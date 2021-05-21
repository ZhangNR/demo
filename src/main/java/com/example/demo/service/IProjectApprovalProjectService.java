package com.example.demo.service;

import com.example.demo.entity.PageInfo;
import com.example.demo.entity.ProjectApprovalProject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PubProjectVO;
import com.example.demo.entity.PublishSearchParams;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
public interface IProjectApprovalProjectService extends IService<ProjectApprovalProject> {
    /**
     * 分页查询 已出版的文件 和 立项的项目
     *
     * @param params pageSize && pageNumber && user
     * @return PubProjectVO
     */
    PageInfo<PubProjectVO> getApprovalProjectList(PublishSearchParams params);

    /**
     * 获取项目组立项项目
     *
     * @param params 部门id 项目组id 年份
     * @return list
     */
    PageInfo<ProjectApprovalProject> getApprovalList(PublishSearchParams params);

    /**
     * 获取部门所有的立项
     *
     * @param deptId 部门id
     * @return list
     */
    List<ProjectApprovalProject> getAllApprovalList(String deptId);

}
