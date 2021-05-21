package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.CooperateRatioVO;
import com.example.demo.entity.SalaryDetail;
import com.example.demo.entity.SalaryDetailVO;
import com.example.demo.entity.TreeDepartment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 记录公司员工逐月的工资信息 Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2020-09-25
 */
public interface SalaryDetailMapper extends BaseMapper<SalaryDetail> {

    /**
     * 获取*年*月工作日
     *
     * @param year  年份
     * @param month 月份
     * @return 工作日
     */
    @Select("SELECT num_of_workday FROM sys_workday WHERE year =#{year} AND month = #{month}")
    int selectWorkingDay(@Param("year") int year, @Param("month") int month);

    /**
     * 查询项目组 成员 工作日&应发工资
     *
     * @param deptId 部门id
     * @param teamId 项目组id
     * @param year   年份
     * @param month  月份
     * @return list
     */
    @Select("select sd.year,sd.month,sd.user_id,sd.user_name,sd.dept_id,sd.dept_name,sd.team_id,sd.team_name,sd.position_level,sd.actual_working_days,sd.gross_pay,sd.gross_pay_sum from salary_entry se, salary_detail sd " +
            "where se.year = #{year} and se.month = #{month} and se.status in ('批准', '归档') and se.id = sd.salary_id and dept_id = #{deptId} AND team_id = #{teamId}")
    List<SalaryDetailVO> selectGrossPaySum(@Param("deptId") String deptId, @Param("teamId") Long teamId, @Param("year") int year, @Param("month") int month);

    /**
     * 查询项目组 成员 工作日&应发工资
     *
     * @param deptId 部门id
     * @param year   年份
     * @param month  月份
     * @return list
     */
    @Select("select sd.year,sd.month,sd.user_id,sd.user_name,sd.dept_id,sd.dept_name,sd.team_id,sd.team_name,sd.position_level,sd.actual_working_days,sd.gross_pay,sd.gross_pay_sum " +
            "from salary_entry se, salary_detail sd,sys_employee_info AS ei where se.year=#{year} and se.month=#{month} and se.status in ('批准', '归档') and se.id = sd.salary_id and sd.dept_id=#{deptId} AND ei.user_id=sd.user_id AND ei.position !='所长' AND ei.position !='副所长' AND ei.position != '副总经理'")
    List<SalaryDetailVO> selectGrossPaySum2(@Param("deptId") String deptId, @Param("year") int year, @Param("month") int month);

    /**
     * 查询*年*月某部门下的项目组
     *
     * @param deptId 部门id
     * @param year   年份
     * @param month  月份
     * @return 项目组list
     */
    @Select("select sd.team_id,sd.team_name from salary_entry se, salary_detail sd where se.year=#{year} and se.month=#{month} and se.status in ('批准', '归档') and se.id = sd.salary_id AND sd.dept_id=#{deptId} AND sd.team_id IS NOT NULL GROUP BY sd.team_id,sd.team_name")
    List<TreeDepartment.SubDepartment> selectTeamList(@Param("deptId") String deptId, @Param("year") int year, @Param("month") int month);

    /**
     * 查询*年*月 当前部门、实际工作时间、职位、工资 数据
     *
     * @param finalYear  实际年
     * @param finalMonth 实际月
     * @param tableYear  对应工资表年
     * @param tableMonth 对应工资表月
     * @return list
     */
    List<SalaryDetailVO> listGrossPay(@Param("finalYear") int finalYear, @Param("finalMonth") int finalMonth, @Param("tableYear") int tableYear, @Param("tableMonth") int tableMonth);

    /**
     * 查询合作比例集合
     *
     * @param year 年
     * @return list
     */
    @Select("SELECT id,year,dept_id,sub_dept_id,cooperate_ratio FROM project_approval_project WHERE state='审核' AND (year=#{year} OR year=#{year}-1) ORDER BY id DESC")
    List<CooperateRatioVO> listCooperateRatio(int year);
}
