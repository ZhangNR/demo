package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.ExpenseEntry2020;
import com.example.demo.entity.ExpenseVO;
import com.example.demo.entity.PerformanceDetailVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Voucher Entry Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2020-09-25
 */
public interface ExpenseEntry2020Mapper extends BaseMapper<ExpenseEntry2020> {


    /**
     * 查询部门正副职*年*月实际报销金额总和
     *
     * @param list         user_id list
     * @param year         年份
     * @param month        月份
     * @param departmentId 部门
     * @return 实际报销费用总和
     */
    @Select("<script>" +
            "   SELECT SUM(reality_sum) FROM expense_entry_2020 WHERE year = #{year} AND month = #{month} AND status = '提交' AND dept_id=#{departmentId} AND user_id IN " +
            "       <foreach collection='list' item='id' index='index' separator=',' open='(' close=')'> " +
            "           #{id}" +
            "       </foreach>" +
            "</script>")
    BigDecimal selectManagerExpense(@Param("list") List<String> list, @Param("year") int year, @Param("month") int month, @Param("departmentId") String departmentId);

    /**
     * 查询项目组公共费用
     *
     * @param deptId 部门id
     * @param teamId 项目组id
     * @param year   月份
     * @param month  年份
     * @return 公共费用总和
     */
    @Select("SELECT SUM(d.amount) FROM expense_entry_2020 AS e, expense_detail_2020 AS d WHERE e.year = #{year} AND e.month = #{month} AND e.status = '提交' AND e.dept_id=#{deptId} AND e.sub_dept_id = #{teamId} AND e.id = d.expense_id AND d.type = '公共费用' AND (d.result != '拒绝' OR d.result IS NULL)")
    BigDecimal selectPublicExpense(@Param("deptId") String deptId, @Param("teamId") String teamId, @Param("year") int year, @Param("month") int month);

    /**
     * 查询用户个人报销费用
     *
     * @param userId 钉钉userid
     * @param year   年份
     * @param month  月份
     * @return 实际个人费用总和
     */
    @Select("SELECT SUM(d.amount) FROM expense_entry_2020 AS e,expense_detail_2020 AS d WHERE e.user_id = #{userId} AND e.year = #{year} AND e.month = #{month} AND e.status = '提交' AND e.id = d.expense_id AND d.type = '个人费用' AND (d.result != '拒绝' OR d.result IS NULL)")
    BigDecimal selectPersonExpense(@Param("userId") String userId, @Param("year") int year, @Param("month") int month);

    /**
     * 查询用户*年*月个人月度产值
     *
     * @param userId 钉钉userid
     * @param year   年份
     * @param month  月份
     * @return 个人月度贡献产值总和
     */
    BigDecimal selectPersonContribution(String userId, int year, int month);

    /**
     * 获取 生产所项目组合作比例
     *
     * @param deptId 部门id
     * @param teamId 项目组id
     * @param year   年份
     * @return cooperate_ratio 合作比例
     */
    @Select("SELECT cooperate_ratio FROM project_approval_project WHERE year=#{year} AND dept_id=#{deptId} AND sub_dept_id=#{teamId} AND state = '审核' ORDER BY create_date DESC LIMIT 1")
    String selectCooperateRatio(@Param("deptId") String deptId, @Param("teamId") String teamId, @Param("year") int year);

    /**
     * 获取生产所*年*月贡献产值
     *
     * @param deptId 部门id
     * @param year   年份
     * @param month  月份
     * @return list
     */
    @Select("SELECT pd.user_id,pd.contribution_output FROM performance_detail AS pd INNER JOIN performance_dept AS d ON d.id = pd.performance_dept_id AND d.dept_id=#{deptId} AND d.year=#{year} AND d.month=#{month} AND d.state = '审核'")
    List<PerformanceDetailVO> selectDeptContribution(@Param("deptId") String deptId, @Param("year") int year, @Param("month") int month);

    /**
     * 批量获取*部门*项目组个人报销
     *
     * @param deptId 部门id
     * @param teamId 项目组id
     * @param year   年份
     * @param month  月份
     * @return list
     */
    @Select("SELECT SUM(d.amount) AS amount,e.user_id FROM expense_entry_2020 AS e,expense_detail_2020 AS d WHERE e.dept_id=#{deptId} AND e.sub_dept_id=#{teamId} AND e.year =#{year} AND e.month = #{month} AND e.status = '提交' AND e.id = d.expense_id AND d.type = '个人费用' AND (d.result != '拒绝' OR d.result IS NULL) GROUP BY e.user_id")
    List<ExpenseVO> getExpenseInDept(@Param("deptId") String deptId, @Param("teamId") String teamId, @Param("year") int year, @Param("month") int month);

    /**
     * 批量获取*部门*项目组个人报销
     *
     * @param deptId 部门id
     * @param year   年份
     * @param month  月份
     * @return list
     */
    @Select("SELECT SUM(d.amount) AS amount,e.user_id FROM expense_entry_2020 AS e,expense_detail_2020 AS d " +
            "WHERE e.dept_id=#{deptId} AND e.year =#{year} AND e.month = #{month} AND e.voucher_statue = '同意' AND e.id = d.expense_id AND d.type = '个人费用' AND (d.result != '拒绝' OR d.result IS NULL) GROUP BY e.user_id")
    List<ExpenseVO> getExpenseInDept2(@Param("deptId") String deptId, @Param("year") int year, @Param("month") int month);

    /**
     * 获取*年*月个人报销集合
     *
     * @param year  年份
     * @param month 月份
     * @return list
     */
    @Select("SELECT SUM(d.amount) AS amount,e.user_id,e.user_name,e.dept_id FROM expense_entry_2020 AS e,expense_detail_2020 AS d " +
            "WHERE e.year=#{year} AND e.month=#{month} AND e.voucher_statue = '同意' AND e.id = d.expense_id AND d.type = '个人费用' AND (d.result != '拒绝' OR d.result IS NULL) GROUP BY e.user_id,e.user_name,e.dept_id")
    List<ExpenseVO> listPersonExpense(@Param("year") int year, @Param("month") int month);

    /**
     * 获取*年*月公共报销集合
     *
     * @param year  年份
     * @param month 月份
     * @return list
     */
    @Select("SELECT SUM(d.amount) AS amount,e.dept_id,e.sub_dept_id FROM expense_entry_2020 AS e, expense_detail_2020 AS d " +
            "WHERE e.year=#{year} AND e.month=#{month} AND e.voucher_statue = '同意' AND e.id = d.expense_id AND d.type = '公共费用' AND (d.result != '拒绝' OR d.result IS NULL) AND d.sub_category != '房租' GROUP BY e.dept_id,e.sub_dept_id")
    List<ExpenseVO> listPublicExpense(@Param("year") int year, @Param("month") int month);
}
