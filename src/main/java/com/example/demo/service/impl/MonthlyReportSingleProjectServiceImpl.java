package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.*;
import com.example.demo.mapper.MonthlyReportSingleProjectMapper;
import com.example.demo.service.IMonthlyReportSingleProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.untils.Response;
import com.example.demo.untils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjp
 * @since 2021-03-30
 */
@Service
public class MonthlyReportSingleProjectServiceImpl extends ServiceImpl<MonthlyReportSingleProjectMapper, MonthlyReportSingleProject> implements IMonthlyReportSingleProjectService {
    private final MonthlyReportSingleProjectMapper singleProjectMapper;

    @Autowired
    public MonthlyReportSingleProjectServiceImpl(MonthlyReportSingleProjectMapper singleProjectMapper) {
        this.singleProjectMapper = singleProjectMapper;
    }

    @Override
    public PageInfo<MonthlyReportSingleProject> getSingleProjectList(PublishSearchParams searchParams) {
        QueryWrapper<MonthlyReportSingleProject> queryWrapper = new QueryWrapper<>();
        User user = searchParams.getUser();
        queryWrapper.select(MonthlyReportSingleProject.class, info -> !"designer_team".equals(info.getColumn()) && !"files".equals(info.getColumn()) && !"update_date".equals(info.getColumn()))
                .func(i -> {
                    if (StringUtils.isEmpty(user.getSubDeptId()) || user.getSubDeptId() == 0) {
                        i.eq("dept_id", user.getDeptId());
                    } else {
                        i.eq("sub_dept_id", user.getSubDeptId());
                    }
                })
                .eq("state", "审核").eq("pub_state", "未出版")
                .like(!StringUtils.isEmpty(searchParams.getProject()), "parent_project", searchParams.getProject())
                .like(!StringUtils.isEmpty(searchParams.getName()), "name", searchParams.getName())
                .eq(!StringUtils.isEmpty(searchParams.getYear()), "year", searchParams.getYear())
                .eq(!StringUtils.isEmpty(searchParams.getMonth()), "month", searchParams.getMonth())
                .eq(!StringUtils.isEmpty(searchParams.getMajor()), "major", searchParams.getMajor())
                .eq(!StringUtils.isEmpty(searchParams.getCreatorName()), "creator_name", searchParams.getCreatorName())
                .like(!StringUtils.isEmpty(searchParams.getNote()), "note", searchParams.getNote())
                .between(!StringUtils.isEmpty(searchParams.getStartDate()), "survey_date", searchParams.getStartDate(), searchParams.getEndDate())
                .notIn(searchParams.getIds() != null && searchParams.getIds().size() > 0, "id", searchParams.getIds())
                .orderByDesc("create_date");

        Page<MonthlyReportSingleProject> page = new Page<>(searchParams.getPageNumber(), searchParams.getPageSize());

        Page<MonthlyReportSingleProject> singleProjectPage = singleProjectMapper.selectPage(page, queryWrapper);

        return new PageInfo<>(singleProjectPage.getTotal(), singleProjectPage.getCurrent(), singleProjectPage.getPages(), singleProjectPage.getRecords());

    }

    @Override
    public Result getSingleProjectListByIds(String idsStr) {
        QueryWrapper<MonthlyReportSingleProject> queryWrapper = new QueryWrapper<>();
        List<Integer> ids = JSON.parseArray(idsStr, Integer.class);
        queryWrapper.select(MonthlyReportSingleProject.class, info -> !"designer_team".equals(info.getColumn()) && !"files".equals(info.getColumn()) && !"update_date".equals(info.getColumn()))
                .in("id", ids);

        List<MonthlyReportSingleProject> singleProjectList = singleProjectMapper.selectList(queryWrapper);
        return Response.success("OK",singleProjectList);
    }
}
