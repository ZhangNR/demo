package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.example.demo.config.Constant;
import com.example.demo.entity.*;
import com.example.demo.mapper.ContributionCostMapper;
import com.example.demo.service.IContributionCostService;
import com.example.demo.untils.CalcUtil;
import com.example.demo.untils.DingServiceApi;
import com.example.demo.untils.Response;
import com.example.demo.untils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjp
 * @since 2020-09-23
 */
@Service
public class ContributionCostServiceImpl extends ServiceImpl<ContributionCostMapper, ContributionCost> implements IContributionCostService {

    private final ContributionCostMapper costMapper;

    @Autowired
    public ContributionCostServiceImpl(ContributionCostMapper costMapper) {
        this.costMapper = costMapper;
    }

    @Override
    public Result login(String code) {
        JSONObject object = new JSONObject();
        String token = DingServiceApi.getToken();
        String userid = DingServiceApi.getUserInfo(code, token);
        OapiUserGetResponse user = DingServiceApi.getUser(userid, token);
        if (user == null) {
            return Response.fail(HttpStatus.BAD_REQUEST.getReasonPhrase(), "无权限");
        }

        String deptList = DingServiceApi.getUserDeptList(userid, token);
        JSONArray array = JSON.parseArray(deptList);
        List<Department> departments = new ArrayList<>();

        if (deptList == null) {
            return Response.fail(HttpStatus.BAD_REQUEST.getReasonPhrase(), "未找不到部门");
        }

        if (array.size() > 1) {
            for (int i = 0; i < array.size(); i++) {
                departments.add(checkDepartment(array.getJSONArray(i), token));
            }
        } else {
            departments.add(checkDepartment(array.getJSONArray(0), token));
        }

        object.put("userid", user.getUserid());
        object.put("name", user.getName());
        object.put("department", toTreeDepartment(departments));
        object.put("spaceId", DingServiceApi.getSpaceId(userid, token));

        return Response.success("success", object);
    }

    @Override
    public List<ContributionCost> getSomeCost(Params param) {
        QueryWrapper<ContributionCost> queryWrapper = new QueryWrapper<>();
        queryWrapper.func(i -> {
            if (!"0".equals(param.getDeptId())) {
                i.eq("dept_id", param.getDeptId()).func(k -> {
                    if (StringUtils.isEmpty(param.getTeamId())) {
                        k.isNull("team_id");
                    } else if (!"0".equals(param.getTeamId())) {
                        k.eq("team_id", param.getTeamId());
                    }
                });
            }
        })
                .eq("year", param.getYear())
                .eq("month", param.getMonth());


        List<ContributionCost> contributionCosts = costMapper.selectList(queryWrapper);
        itemCalc(contributionCosts);
        return contributionCosts;

    }

    private void itemCalc(List<ContributionCost> contributionCosts) {
        contributionCosts.forEach(item -> {
            if (item.getCost().intValue() == 0) {
                item.setContributionProductivity(0d);
                item.setSettlementRatio(0d);
                item.setBenefit(0d);
            } else {
                item.setContributionProductivity(CalcUtil.divide(item.getContribution(), item.getCost(), 4).doubleValue());
                item.setSettlementRatio(CalcUtil.divide(item.getContribution(), item.getCost(), 4).doubleValue());
                item.setBenefit(CalcUtil.divide(item.getContribution(), CalcUtil.mul(item.getCost(), BigDecimal.valueOf(1.48), 4), 4).doubleValue());
            }
        });
    }

    @Override
    public List<ContributionCost> getAllCost(Params param) {
        List<ContributionCost> contributionCosts = costMapper.selectSumData(param);
        itemCalc(contributionCosts);
        return contributionCosts;
    }

    @Override
    public List<TreeDepartment> getDeptList() {
        List<TreeDepartment> treeDepartments = costMapper.selectDeptList(Constant.PRODUCTION_DEPARTMENT_ID);
        treeDepartments.forEach(item -> {
            List<TreeDepartment.SubDepartment> subDepartments = costMapper.selectTeamList(item.getDeptId());
            subDepartments.add(0, new TreeDepartment.SubDepartment("0", "全部项目组"));
            subDepartments.add(new TreeDepartment.SubDepartment(null, null));
            item.setChildren(subDepartments);
        });
        return treeDepartments;

    }

    @Override
    public List<ContributionCost> getAll(Params param) {
        List<ContributionCost> contributionCosts = costMapper.selectAll(param.getYear());
        itemCalc(contributionCosts);
        return contributionCosts;
    }

    private Department checkDepartment(JSONArray deptList, String accessToken) {
        Department department;
        // 长度为4则为项目组,其他为部门
        if (deptList.size() == 4) {
            department = getDepartment(deptList.getString(1), deptList.getString(0), accessToken);
        } else {
            department = getDepartment(deptList.getString(0), null, accessToken);
        }
        return department;
    }

    private Department getDepartment(String deptId, String subDeptId, String accessToken) {
        Department department = new Department();

        OapiDepartmentGetResponse deptInfo = DingServiceApi.getDeptInfo(deptId, accessToken);
        if (deptInfo == null) {
            throw new RuntimeException("部门不存在");
        }
        department.setDeptId(deptInfo.getId().toString());
        department.setDeptName(deptInfo.getName());

        if (subDeptId == null) {
            department.setTeamId("0");
            department.setTeamName("无");
        } else {
            OapiDepartmentGetResponse subDeptInfo = DingServiceApi.getDeptInfo(subDeptId, accessToken);
            if (subDeptInfo == null) {
                throw new RuntimeException("部门不存在");
            }
            department.setTeamId(subDeptInfo.getId().toString());
            department.setTeamName(subDeptInfo.getName());
        }

        return department;
    }

    private ArrayList<TreeDepartment> toTreeDepartment(List<Department> departments) {
        ArrayList<TreeDepartment> treeDepartments = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        departments.forEach(item -> {
            if (!list.contains(item.getDeptId())) {
                list.add(item.getDeptId());
            }
        });

        list.forEach(item -> {
            TreeDepartment treeDepartment = new TreeDepartment();

            ArrayList<TreeDepartment.SubDepartment> subDepartments = new ArrayList<>();
            List<Department> collect = departments.stream().filter(department -> department.getDeptId().equals(item)).collect(Collectors.toList());
            if (!collect.isEmpty()) {
                treeDepartment.setDeptId(collect.get(0).getDeptId());
                treeDepartment.setDeptName(collect.get(0).getDeptName());

                collect.forEach(c -> {
                    TreeDepartment.SubDepartment subDepartment = new TreeDepartment.SubDepartment();
                    subDepartment.setTeamId(c.getTeamId());
                    subDepartment.setTeamName(c.getTeamName());
                    subDepartments.add(subDepartment);
                });
                treeDepartment.setChildren(subDepartments);
            }

            treeDepartments.add(treeDepartment);
        });
        return treeDepartments;
    }

}
