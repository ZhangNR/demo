package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.ContributionCost;
import com.example.demo.entity.Params;
import com.example.demo.entity.Result;
import com.example.demo.entity.TreeDepartment;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zjp
 * @since 2020-09-23
 */
public interface IContributionCostService extends IService<ContributionCost> {


    /**
     * 登录获取用户的信息
     *
     * @param code 免登授权码
     * @return 用户信息
     */
    Result login(String code);

    /**
     * 获取 项目组*年*月的数据
     *
     * @param param 查询参数
     * @return list
     */
    List<ContributionCost> getSomeCost(Params param);

    /**
     * 获取 项目组*年的数据 汇总
     *
     * @param param 查询参数
     * @return list
     */
    List<ContributionCost> getAllCost(Params param);

    /**
     * 获取 树形部门
     *
     * @return list
     */
    List<TreeDepartment> getDeptList();

    /**
     * 获取 contribution_cost 表累计计算数据
     *
     * @param param year年份
     * @return list
     */
    List<ContributionCost> getAll(Params param);

}
