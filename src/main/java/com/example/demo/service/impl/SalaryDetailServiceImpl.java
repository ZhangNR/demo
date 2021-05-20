package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.example.demo.config.Constant;
import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.service.ISalaryDetailService;
import com.example.demo.untils.CalcUtil;
import com.example.demo.untils.DingServiceApi;
import com.example.demo.untils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 记录公司员工逐月的工资信息 服务实现类
 * </p>
 *
 * @author zjp
 * @since 2020-09-25
 */
@Service
@Slf4j
public class SalaryDetailServiceImpl extends ServiceImpl<SalaryDetailMapper, SalaryDetail> implements ISalaryDetailService {

    private final SalaryDetailMapper salaryDetailMapper;
    private final ExpenseEntry2020Mapper expenseEntry2020Mapper;
    private final ContributionCostServiceImpl costService;
    private final RentDetailMapper rentDetailMapper;
    private final ContributionCostMapper costMapper;
    private final PerformanceDetailMapper performanceDetailMapper;

    @Autowired
    public SalaryDetailServiceImpl(SalaryDetailMapper salaryDetailMapper, ExpenseEntry2020Mapper expenseEntry2020Mapper, ContributionCostServiceImpl costService, RentDetailMapper rentDetailMapper, ContributionCostMapper costMapper, PerformanceDetailMapper performanceDetailMapper) {
        this.salaryDetailMapper = salaryDetailMapper;
        this.expenseEntry2020Mapper = expenseEntry2020Mapper;
        this.costService = costService;
        this.rentDetailMapper = rentDetailMapper;
        this.costMapper = costMapper;
        this.performanceDetailMapper = performanceDetailMapper;
    }

    /**
     * 分组时进行的标记 （无项目组）
     */
    private static final String NO_TEAM_GROUP_MARK = "0";
    /**
     * 指定生成月份数量 2 表示生成过去两个月的数据
     */
    private static final int MONTH_SPECIFY = 2;


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void generatedLastMonth() {

        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue() - 1;
        if (month == 0) {
            year = year - 1;
            month = 12;
        }

        process(year, month);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void generatedAutomatically() {
        long start = System.currentTimeMillis();
        int monthValue = LocalDate.now().getMonthValue();
        if (monthValue <= Month.APRIL.getValue()) {
            generatedBeforeApril();
        } else {
            generatedAfterApril();
        }
        long end = System.currentTimeMillis();
        log.info("运行时间：" + (end - start));

    }

    /**
     * （管理员）生成自定义年、月数据
     *
     * @param year  年
     * @param month 月
     */
    @Override
    public void generatedCustomerYearMonth(int year, int month) {
        log.info("（管理员）{} 生成自定义 {} 年 {} 月数据", LocalDate.now(), year, month);
        process(year, month);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void generatedLast2Months() {

        // 对最近的两个月进行重新估值
        LocalDate now = LocalDate.now();
        switch (now.getMonthValue()) {
            case 1:
                process(now.getYear() - 1, 11);
                process(now.getYear() - 1, 12);
                break;
            case 2:
                process(now.getYear() - 1, 12);
                process(now.getYear(), 1);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                for (int i = MONTH_SPECIFY; i > 0; i--) {
                    process(now.getYear(), now.getMonthValue() - i);
                }
                break;
            default:
                log.warn("generatedAutomatically: now.getMonthValue() =" + now.getMonthValue());
                break;
        }
    }

    private void generatedAfterApril() {
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), 4, 1);
        int months = Period.between(startDate, LocalDate.now()).getMonths();

        for (int i = 0; i < months; i++) {
            LocalDate date = startDate.plusMonths(i);
            process(date.getYear(), date.getMonthValue());
        }
    }

    private void generatedBeforeApril() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = LocalDate.of(LocalDate.now().getYear() - 1, 4, 1);
        for (int i = 0; i < Month.DECEMBER.getValue(); i++) {
            LocalDate date = startDate.plusMonths(i);
            process(date.getYear(), date.getMonthValue());
        }
        int months = Period.between(LocalDate.of(now.getYear(), 1, 1), now).getMonths();
        if (months != 0) {
            for (int j = 0; j < months; j++) {
                LocalDate date = LocalDate.of(now.getYear(), 1, 1).plusMonths(j);
                process(date.getYear(), date.getMonthValue());
            }
        }
    }

    /**
     * 计算*年*月数据
     * int表示的最大范围 -2147483648 ~ 2147483647 工作天数按28天算 部门list最大可以为76695844 不需要将int转换为long类型
     *
     * @param year  年
     * @param month 月
     * @
     */
    public void process(int year, int month) {
        long startTime = System.currentTimeMillis();
        // 先删除*年*月 所有部门数据
        QueryWrapper<ContributionCost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", year).eq("month", month);
        costService.remove(queryWrapper);

        // 获取*年*月工作日
        int workingDay = salaryDetailMapper.selectWorkingDay(year, month);

        // 12月的工资在 工资表中的月份为1月，2020年12月工资 获取参数年：2021月：1，其他月份加1
        List<SalaryDetailVO> detailVOList;
        if (month == Month.DECEMBER.getValue()) {
            detailVOList = salaryDetailMapper.listGrossPay(year, 12, year + 1, 1);
        } else {
            detailVOList = salaryDetailMapper.listGrossPay(year, month, year, month + 1);
        }

        // 获取个人报销 根据部门/项目组分组后，设计所的成员根据user_id合计报销为自己总的报销，所长对应部门的报销进行分摊。
        List<ExpenseVO> personExpenseList = expenseEntry2020Mapper.listPersonExpense(year, month);

        // 获取公共报销 根据部门/项目组分组后，项目组不为空对应项目组进行分摊，项目组为空的公共报销如何处理？ 1 不处理 2 全所的项目组成员进行分摊（与所长报销一起）
        List<ExpenseVO> publicExpenseList = expenseEntry2020Mapper.listPublicExpense(year, month);

        List<DepartmentManager> departmentManagers = costMapper.listManager(year, month);

        // 获取贡献产值
        List<PerformanceDetailVO> contribution = performanceDetailMapper.listContribution(year, month);

        // 获取房租分摊
        List<RentVO> rentList = rentDetailMapper.listMonthlyRent(year, month);

        // 获取合作比例
        List<CooperateRatioVO> cooperateRatioList = salaryDetailMapper.listCooperateRatio(year);

        // 批量插入

        List<TreeDepartment> departments = costMapper.selectDeptList(Constant.PRODUCTION_DEPARTMENT_ID);

        List<ContributionCost> list = new ArrayList<>();

        departments.forEach(department -> {
            List<SalaryDetailVO> salaryDetailVOList = detailVOList.stream().filter(i -> department.getDeptId().equals(i.getDeptId())).collect(Collectors.toList());

            BigDecimal departmentActualWorkingDays = salaryDetailVOList.stream().map(SalaryDetailVO::getActualWorkingDays).reduce(BigDecimal.ZERO, BigDecimal::add);
            /*
             * 部门理论工作天数
             * int表示的最大范围 -2147483648 ~ 2147483647
             */
            int departmentTheoreticallyWorkingDays = salaryDetailVOList.size() * workingDay;
            log.info("{} || 实际工作天数总和 {} || 部门理论工作天数 {}", department.getDeptName(), departmentActualWorkingDays, departmentTheoreticallyWorkingDays);

            BigDecimal expensePublicInsidePer = getExpensePublicInside(personExpenseList, departmentManagers, department, departmentActualWorkingDays, departmentTheoreticallyWorkingDays);

            salaryDetailVOList.stream().collect(Collectors.groupingBy(t -> t.getTeamId() == null ? NO_TEAM_GROUP_MARK : t.getTeamId(), Collectors.toList())).forEach((key, groupMap) -> {

                if (NO_TEAM_GROUP_MARK.equals(key)) {

                    processKeyType(year, month, workingDay, personExpenseList, contribution, expensePublicInsidePer, groupMap, list);

                } else {
                    processTeamType(year, month, workingDay, personExpenseList, publicExpenseList, contribution, rentList, cooperateRatioList, department, expensePublicInsidePer, key, groupMap, list);
                }

            });

        });
        costService.saveBatch(list);

        long entTime = System.currentTimeMillis();
        log.info("运行时间：{}", entTime - startTime);

    }

    private void processTeamType(int year, int month, int workingDay, List<ExpenseVO> personExpenseList, List<ExpenseVO> publicExpenseList, List<PerformanceDetailVO> contribution, List<RentVO> rentList, List<CooperateRatioVO> cooperateRatioList, TreeDepartment department, BigDecimal expensePublicInsidePer, String key, List<SalaryDetailVO> groupMap, List<ContributionCost> list) {
        BigDecimal teamActualWorkingDays = groupMap
                .stream()
                .map(SalaryDetailVO::getActualWorkingDays)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int teamTheoreticallyWorkingDays = groupMap.size() * workingDay;

        // 公共报销
        BigDecimal publicExpense = publicExpenseList
                .stream()
                .filter(expenseVO -> department.getDeptId().equals(expenseVO.getDeptId()) && key.equals(expenseVO.getSubDeptId()))
                .map(ExpenseVO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal teamExpensePublic = apportion(teamActualWorkingDays, teamTheoreticallyWorkingDays, publicExpense);

        // 房租公摊
        BigDecimal rentAmount = rentList
                .stream()
                .filter(rentVO -> department.getDeptId().equals(rentVO.getDeptId()) && key.equals(rentVO.getTeamId()))
                .map(RentVO::getRentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expensePublicRent = apportion(teamActualWorkingDays, teamTheoreticallyWorkingDays, rentAmount);

        // 合作比例
        List<CooperateRatioVO> teamCooperateRatioList = cooperateRatioList
                .stream()
                .filter(cooperateRatioVO -> department.getDeptId().equals(cooperateRatioVO.getDeptId()) && key.equals(cooperateRatioVO.getSubDeptId()))
                .collect(Collectors.toList());

        BigDecimal teamCooperateRatio = teamCooperateRatioList.isEmpty()
                ? BigDecimal.ONE
                : teamCooperateRatioList.get(0).getCooperateRatio();
        log.info("{} - {} || 实际工作天数 {} || 理论工作天数 {} || 公共报销 {} || 公摊{} || 房租 {} || 公摊 {} || 合作比例 {}",
                department.getDeptName(), groupMap.get(0).getTeamName(), teamActualWorkingDays, teamTheoreticallyWorkingDays, publicExpense, teamExpensePublic, rentAmount, expensePublicRent, teamCooperateRatio);

        BigDecimal expensePublicReplace = BigDecimal.ZERO;

        // 个人报销
        groupMap.forEach(vo -> {
            ContributionCost cost = new ContributionCost();
            cost.setSalaryDetailData(vo);
            cost.setYear(year);
            cost.setMonth(month);
            cost.setCooperateRatio(String.valueOf(teamCooperateRatio));

            BigDecimal expensePerson = personExpenseList
                    .stream()
                    .filter(p -> p.getUserId().equals(vo.getUserId()))
                    .map(ExpenseVO::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cost.setExpensePerson(expensePerson);

            BigDecimal contributionPerson = contribution
                    .stream()
                    .filter(performanceDetailVO -> performanceDetailVO.getUserId().equals(vo.getUserId()))
                    .map(PerformanceDetailVO::getContributionOutput)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cost.setContribution(contributionPerson);

            if (vo.getActualWorkingDays().intValue() == 0 && teamActualWorkingDays.intValue() == 0) {
                cost.setExpensePublic(CalcUtil.mul(teamExpensePublic, BigDecimal.valueOf(workingDay), 2));
                cost.setExpensePublicRent(CalcUtil.mul(expensePublicRent, BigDecimal.valueOf(workingDay), 2));
                cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, BigDecimal.valueOf(workingDay), 2));
            } else {
                cost.setExpensePublic(CalcUtil.mul(teamExpensePublic, vo.getActualWorkingDays(), 2));
                cost.setExpensePublicRent(CalcUtil.mul(expensePublicRent, vo.getActualWorkingDays(), 2));
                cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, vo.getActualWorkingDays(), 2));
            }

            cost.setExpensePublicReplace(expensePublicReplace);
            cost.setCost(CalcUtil.sumOwn(cost.getLaborCost(), cost.getExpensePerson(), cost.getExpensePublic(), cost.getExpensePublicRent(), cost.getExpensePublicReplace(), cost.getExpensePublicInside()));

            cost.setSettlement(CalcUtil.mul(cost.getContribution(), new BigDecimal(cost.getCooperateRatio()), 2));

            list.add(cost);
        });
    }

    private void processKeyType(int year, int month, int workingDay, List<ExpenseVO> personExpenseList, List<PerformanceDetailVO> contribution, BigDecimal expensePublicInsidePer, List<SalaryDetailVO> groupMap, List<ContributionCost> list) {
        BigDecimal teamActualWorkingDays = groupMap
                .stream()
                .map(SalaryDetailVO::getActualWorkingDays)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (SalaryDetailVO vo : groupMap) {

            ContributionCost cost = new ContributionCost();
            cost.setSalaryDetailData(vo);
            cost.setYear(year);
            cost.setMonth(month);

            BigDecimal expensePerson = personExpenseList
                    .stream()
                    .filter(expenseVO -> expenseVO.getUserId().equals(vo.getUserId()))
                    .map(ExpenseVO::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cost.setExpensePerson(expensePerson);

            BigDecimal contributionPerson = contribution
                    .stream()
                    .filter(performanceDetailVO -> performanceDetailVO.getUserId().equals(vo.getUserId()))
                    .map(PerformanceDetailVO::getContributionOutput)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cost.setContribution(contributionPerson);
            cost.setCooperateRatio("1");
            cost.setExpensePublic(BigDecimal.ZERO);
            cost.setExpensePublicRent(BigDecimal.ZERO);
            cost.setExpensePublicReplace(BigDecimal.ZERO);
            cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, vo.getActualWorkingDays(), 2));
            if (vo.getActualWorkingDays().intValue() == 0 || teamActualWorkingDays.intValue() == 0) {
                cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, BigDecimal.valueOf(workingDay), 2));
            } else {
                cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, vo.getActualWorkingDays(), 2));
            }
            cost.setCost(CalcUtil.sumOwn(cost.getLaborCost(), cost.getExpensePerson(), cost.getExpensePublic(), cost.getExpensePublicRent(), cost.getExpensePublicReplace(), cost.getExpensePublicInside()));

            cost.setSettlement(CalcUtil.mul(cost.getContribution(), new BigDecimal(cost.getCooperateRatio()), 2));

            list.add(cost);

            log.info("部门人员 {} || 个人报销 {} || 贡献产值 {} || 合作比例 1 || 公共分摊 0 || 房租 0 || 所内分摊 {} ", vo.getUserName(), expensePerson, contributionPerson, expensePublicInsidePer);
        }
    }

    private BigDecimal apportion(BigDecimal teamActualWorkingDays, int teamTheoreticallyWorkingDays, BigDecimal amount) {
        if (teamTheoreticallyWorkingDays == 0) {
            return BigDecimal.ZERO;
        }
        return teamActualWorkingDays.intValue() == 0
                ? CalcUtil.divide(amount, BigDecimal.valueOf(teamTheoreticallyWorkingDays), 4)
                : CalcUtil.divide(amount, teamActualWorkingDays, 4);
    }

    private BigDecimal getExpensePublicInside(List<ExpenseVO> personExpenseList, List<DepartmentManager> departmentManagers, TreeDepartment department, BigDecimal departmentActualWorkingDays, int departmentTheoreticallyWorkingDays) {

        List<DepartmentManager> collect = departmentManagers
                .stream()
                .filter(departmentManager -> department.getDeptId().equals(departmentManager.getDeptId()))
                .collect(Collectors.toList());

        if (collect.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // managerList 理论上肯定不为空
        List<String> managerList = Arrays.asList(collect.get(0).getManagers().split(","));

        if (managerList.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal managerExpense = personExpenseList
                .stream()
                .filter(expenseVO -> managerList.contains(expenseVO.getUserId()) && expenseVO.getDeptId().equals(department.getDeptId()))
                .map(ExpenseVO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return apportion(departmentActualWorkingDays, departmentTheoreticallyWorkingDays, managerExpense);
    }

    private void dealData(int finalYear, int finalMonth) {

        // 删除*年*月 所有部门数据
        QueryWrapper<ContributionCost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("year", finalYear).eq("month", finalMonth);
        costService.remove(queryWrapper);

        // 获取*年*月工作日
        int workingDay = salaryDetailMapper.selectWorkingDay(finalYear, finalMonth);

        List<Department> departments = costMapper.selectDeptList2(finalYear,finalMonth, Constant.PRODUCTION_DEPARTMENT_ID);
        departments.forEach(department -> {
            List<ContributionCost> list = new ArrayList<>();
            String departmentId = department.getDeptId();
            List<SalaryDetailVO> detailVOList;
            // 12月的工资在 工资表中的月份为1月，2020年12月工资 获取参数年：2021月：1，其他月份加1
            if (finalMonth == 12) {
                detailVOList = salaryDetailMapper.selectGrossPaySum2(departmentId, finalYear + 1, 1);
            } else {
                detailVOList = salaryDetailMapper.selectGrossPaySum2(departmentId, finalYear, finalMonth + 1);
            }

            List<ExpenseVO> expenseAllDepartment = getExpenseInTeam2(departmentId, finalYear, finalMonth);
            List<PerformanceDetailVO> performanceDetailList = getDeptContribution(departmentId, finalYear, finalMonth);
            // 计算所内分摊
            BigDecimal expensePublicInsidePer = getDeptExpensePublicInside(finalYear, finalMonth, workingDay, detailVOList, departmentId);

            detailVOList.stream().collect(Collectors.groupingBy(t -> t.getTeamId() == null ? "0" : t.getTeamId(), Collectors.toList())).forEach((key, groupMap) -> {
                //key为0 说明项目组的id为null，在部门下不在项目组下，此时不进行分摊
                if ("0".equals(key)) {

                    for (SalaryDetailVO vo : groupMap) {
                        ContributionCost cost = new ContributionCost();
                        cost.setYear(finalYear);
                        cost.setMonth(finalMonth);
                        cost.setSalaryDetailData(vo);

                        // 获取用户当月的个人报销
                        ExpenseVO expensePerson = expenseAllDepartment.stream().filter(p -> p.getUserId().equals(vo.getUserId())).findAny().orElse(null);
                        cost.setExpensePerson(expensePerson == null ? BigDecimal.valueOf(0) : expensePerson.getAmount());
                        PerformanceDetailVO contributionPerson = performanceDetailList.stream().filter(performanceDetail -> performanceDetail.getUserId().equals(vo.getUserId())).findAny().orElse(null);
                        cost.setContribution(contributionPerson == null ? BigDecimal.ZERO : contributionPerson.getContributionOutput());
                        cost.setCooperateRatio("1");
                        cost.setExpensePublic(BigDecimal.valueOf(0));
                        cost.setExpensePublicRent(BigDecimal.valueOf(0));
                        if (vo.getActualWorkingDays().intValue() == 0 && detailVOList.stream().map(SalaryDetailVO::getActualWorkingDays).reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue() == 0) {
                            cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, BigDecimal.valueOf(workingDay), 2));
                        } else {
                            cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, vo.getActualWorkingDays(), 2));
                        }
                        cost.setExpensePublicReplace(BigDecimal.valueOf(0));
                        cost.setCost(CalcUtil.sumOwn(cost.getLaborCost(), cost.getExpensePerson(), cost.getExpensePublic(), cost.getExpensePublicRent(), cost.getExpensePublicReplace(), cost.getExpensePublicInside()));
                        cost.setSettlement(CalcUtil.mul(cost.getContribution(), new BigDecimal(cost.getCooperateRatio()), 2));
                        list.add(cost);
                    }

                } else {
                    BigDecimal workingDays = detailVOList.stream().map(SalaryDetailVO::getActualWorkingDays).reduce(BigDecimal.ZERO, BigDecimal::add);

                    if (workingDays.intValue() == 0) {
                        workingDays = BigDecimal.valueOf(workingDay * groupMap.size());
                    }

                    //计算该项目组的公共费用分摊
                    BigDecimal sumExpensePublic = getExpensePublic(departmentId, key, finalYear, finalMonth);
                    BigDecimal expensePublic;
                    if (sumExpensePublic == null) {
                        expensePublic = BigDecimal.valueOf(0);
                    } else {
                        expensePublic = CalcUtil.divide(sumExpensePublic, workingDays, 4);
                    }

                    // 获取项目组的合作比例
                    String cooperateRatio = getCooperateRatio(departmentId, key, finalYear);
                    if (StringUtils.isEmpty(cooperateRatio)) {
                        cooperateRatio = getCooperateRatio(departmentId, key, finalYear - 1);
                        if (StringUtils.isEmpty(cooperateRatio)) {
                            cooperateRatio = "1";
                        }
                    }

                    BigDecimal rent = getRent(departmentId, key, finalYear, finalMonth);
                    BigDecimal expensePublicRent;
                    if (rent == null) {
                        expensePublicRent = BigDecimal.valueOf(0);
                    } else {
                        expensePublicRent = CalcUtil.divide(rent, workingDays, 4);
                    }

                    BigDecimal expensePublicReplace = BigDecimal.valueOf(0);

                    for (SalaryDetailVO vo : groupMap) {
                        ContributionCost cost = new ContributionCost();
                        cost.setYear(finalYear);
                        cost.setMonth(finalMonth);
                        cost.setSalaryDetailData(vo);

                        // 获取用户当月的个人报销
                        ExpenseVO expensePerson = expenseAllDepartment.stream().filter(p -> p.getUserId().equals(vo.getUserId())).findAny().orElse(null);
                        cost.setExpensePerson(expensePerson == null ? BigDecimal.valueOf(0) : expensePerson.getAmount());
                        PerformanceDetailVO contributionPerson = performanceDetailList.stream().filter(performanceDetail -> performanceDetail.getUserId().equals(vo.getUserId())).findAny().orElse(null);
                        cost.setContribution(contributionPerson == null ? BigDecimal.valueOf(0) : contributionPerson.getContributionOutput());
                        cost.setCooperateRatio(cooperateRatio);
                        if (vo.getActualWorkingDays().intValue() == 0) {
                            cost.setExpensePublic(CalcUtil.mul(expensePublic, BigDecimal.valueOf(workingDay), 2));
                            cost.setExpensePublicRent(CalcUtil.mul(expensePublicRent, BigDecimal.valueOf(workingDay), 2));
                            cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, BigDecimal.valueOf(workingDay), 2));
                        } else {
                            cost.setExpensePublic(CalcUtil.mul(expensePublic, vo.getActualWorkingDays(), 2));
                            cost.setExpensePublicRent(CalcUtil.mul(expensePublicRent, vo.getActualWorkingDays(), 2));
                            cost.setExpensePublicInside(CalcUtil.mul(expensePublicInsidePer, vo.getActualWorkingDays(), 2));
                        }
                        cost.setExpensePublicReplace(expensePublicReplace);
                        cost.setCost(CalcUtil.sumOwn(cost.getLaborCost(), cost.getExpensePerson(), cost.getExpensePublic(), cost.getExpensePublicRent(), cost.getExpensePublicReplace(), cost.getExpensePublicInside()));

                        cost.setSettlement(CalcUtil.mul(cost.getContribution(), new BigDecimal(cost.getCooperateRatio()), 2));
                        list.add(cost);
                    }
                }
            });

            costService.saveBatch(list);
        });
    }

    /**
     * 计算所内分摊
     */
    private BigDecimal getDeptExpensePublicInside(int finalYear, int finalMonth, int workingDay, List<SalaryDetailVO> allDeptVOList, String departmentId) {
        log.info("计算所内分摊: {} 年, {} 月, 部门: {} ,工作日: {} 天, 部门人数: {} ", finalYear, finalMonth, departmentId, workingDay, allDeptVOList.size());
        //所内所有工作日
        BigDecimal deptWorkingDays = allDeptVOList.stream().map(SalaryDetailVO::getActualWorkingDays).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (deptWorkingDays.intValue() == 0) {
            deptWorkingDays = BigDecimal.valueOf((long) workingDay * allDeptVOList.size());
        }
        BigDecimal deptExpenseInsides = null;

        //获取部门正副职
        OapiDepartmentGetResponse deptInfo = DingServiceApi.getDeptInfo(String.valueOf(departmentId), DingServiceApi.getToken());
        if (deptInfo != null) {
            String managerUseridList = deptInfo.getDeptManagerUseridList();
            //说明：|在正则里是个特殊字符，或的意思。比如 ",|;"按,或;来分隔；"|"等同于""，空或空的意思，所以使用"\\|"或者"[|]"
            String[] str = managerUseridList.split("\\|");
            List<String> userIdList = Arrays.asList(str);
            //获取正副所长上月份报销
            deptExpenseInsides = getExpenseByManager(userIdList, finalYear, finalMonth, departmentId);
        }
        BigDecimal expensePublicInside;
        if (deptExpenseInsides == null || deptWorkingDays.intValue() == 0) {
            expensePublicInside = BigDecimal.valueOf(0);
        } else {
            expensePublicInside = CalcUtil.divide(deptExpenseInsides, deptWorkingDays, 4);
        }
        return expensePublicInside;
    }

    /**
     * 计算所内分摊
     */
    public List<String> getExpenseOfManager(String departmentId) {

        //获取部门正副职
        OapiDepartmentGetResponse deptInfo = DingServiceApi.getDeptInfo(String.valueOf(departmentId), DingServiceApi.getToken());
        if (deptInfo != null) {
            String managerUseridList = deptInfo.getDeptManagerUseridList();
            //说明：|在正则里是个特殊字符，或的意思。比如 ",|;"按,或;来分隔；"|"等同于""，空或空的意思，所以使用"\\|"或者"[|]"
            String[] str = managerUseridList.split("\\|");
            return Arrays.asList(str);
        }

        return Collections.emptyList();
    }

    /**
     * 获取*部门*项目组*年*月房租费用
     */
    private BigDecimal getRent(String deptId, String teamId, int year, int month) {
        return rentDetailMapper.selectMonthlyRent(deptId, teamId, year, month);
    }

    /**
     * 获取部门内合作比例
     */
    private String getCooperateRatio(String deptId, String teamId, int year) {
        return expenseEntry2020Mapper.selectCooperateRatio(deptId, teamId, year);
    }


    /**
     * 获取正副所长上月份报销
     */
    private BigDecimal getExpenseByManager(List<String> userIdList, int year, int month, String departmentId) {
        return expenseEntry2020Mapper.selectManagerExpense(userIdList, year, month, departmentId);
    }

    /**
     * 获取部门内公共报销
     */
    private BigDecimal getExpensePublic(String deptId, String teamId, int year, int month) {
        return expenseEntry2020Mapper.selectPublicExpense(deptId, teamId, year, month);
    }

    /**
     * 获取项目组内所有人的个人报销
     */
    private List<ExpenseVO> getExpenseInTeam(String deptId, String teamId, int year, int month) {
        return expenseEntry2020Mapper.getExpenseInDept(deptId, teamId, year, month);
    }

    /**
     * 获取部门内所有人的个人报销
     */
    private List<ExpenseVO> getExpenseInTeam2(String deptId, int year, int month) {
        return expenseEntry2020Mapper.getExpenseInDept2(deptId, year, month);
    }

    /**
     * 获取生产所*年*月贡献产值
     */
    private List<PerformanceDetailVO> getDeptContribution(String deptId, int year, int month) {
        return expenseEntry2020Mapper.selectDeptContribution(deptId, year, month);
    }

}
