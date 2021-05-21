package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.*;
import com.example.demo.mapper.ProjectApprovalProjectMapper;
import com.example.demo.service.IProjectApprovalProjectService;
import com.example.demo.untils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Service
@Slf4j
public class ProjectApprovalProjectServiceImpl extends ServiceImpl<ProjectApprovalProjectMapper, ProjectApprovalProject> implements IProjectApprovalProjectService {

    private final ProjectApprovalProjectMapper approvalProjectMapper;

    @Autowired
    public ProjectApprovalProjectServiceImpl(ProjectApprovalProjectMapper approvalProjectMapper) {
        this.approvalProjectMapper = approvalProjectMapper;
    }

    @Override
    public PageInfo<PubProjectVO> getApprovalProjectList(PublishSearchParams params) {
        Page<PubProjectVO> page = new Page<>(params.getPageNumber(), params.getPageSize());
        Page<PubProjectVO> approvalProjectList = approvalProjectMapper.selectApprovalProjectList(page, params.getUser());

        return new PageInfo<>(approvalProjectList.getTotal(), approvalProjectList.getCurrent(), approvalProjectList.getPages(), approvalProjectList.getRecords());
    }

    @Override
    public PageInfo<ProjectApprovalProject> getApprovalList(PublishSearchParams params) {
        log.info(JSON.toJSONString(params));

        User user = params.getUser();
        QueryWrapper<ProjectApprovalProject> queryWrapper = new QueryWrapper<>();
        Page<ProjectApprovalProject> page = new Page<>(params.getPageNumber(), params.getPageSize());

        queryWrapper.select(ProjectApprovalProject.class, info -> !"customer_files".equals(info.getColumn()) && !"basis_files".equals(info.getColumn()) && !"update_date".equals(info.getColumn()))
                .func(i -> {
                    if (StringUtils.isEmpty(user.getSubDeptId()) || user.getSubDeptId() == 0) {
                        i.eq("dept_id", user.getDeptId());
                    } else {
                        i.eq("sub_dept_id", user.getSubDeptId());
                    }
                })
                .eq("state", "审核").eq(!StringUtils.isEmpty(params.getYear()), "year", params.getYear()).orderByDesc("create_date");

        Page<ProjectApprovalProject> list = page(page, queryWrapper);
        return new PageInfo<>(list.getTotal(), list.getCurrent(), list.getPages(), list.getRecords());
    }

    @Override
    public List<ProjectApprovalProject> getAllApprovalList(String deptId) {
        QueryWrapper<ProjectApprovalProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(ProjectApprovalProject.class, info -> !"customer_files".equals(info.getColumn()) && !"basis_files".equals(info.getColumn()) && !"update_date".equals(info.getColumn()))
                .eq("dept_id", deptId).eq("state", "审核").orderByDesc("id");

        return list(queryWrapper);
    }

}
