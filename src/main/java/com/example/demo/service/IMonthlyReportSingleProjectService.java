package com.example.demo.service;

import com.example.demo.entity.MonthlyReportSingleProject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.PageInfo;
import com.example.demo.entity.PublishSearchParams;
import com.example.demo.entity.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjp
 * @since 2021-03-30
 */
public interface IMonthlyReportSingleProjectService extends IService<MonthlyReportSingleProject> {
    /**
     * 获取单点集合 by 查询条件
     * 分页
     *
     * @param searchParams 查询参数
     * @return 分页查询结果
     */
    PageInfo<MonthlyReportSingleProject> getSingleProjectList(PublishSearchParams searchParams);

    /**
     * 获取单点集合 by ids
     *
     * @param idsStr id
     * @return code+data
     */
    Result getSingleProjectListByIds(String idsStr);
}
