package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2020-09-23
 */
@Repository
@Mapper
public interface ContributionCostMapper extends BaseMapper<ContributionCost> {

    /**
     * 查询 汇总的部门的数据
     *
     * @param param 条件
     * @return list
     */
    List<ContributionCost> selectSumData(@Param("param") Params param);

    /**
     * 查询生产部门下的所有部门
     *
     * @param productionDepartmentId 生产部门id
     * @return List
     */
    @Select("SELECT dept_id,dept_name FROM sys_department_info WHERE parent_id = #{productionDepartmentId}")
    List<TreeDepartment> selectDeptList(String productionDepartmentId);

    /**
     * 查询生产部门下的/年/月部门
     *
     * @param year                   年
     * @param month                  月
     * @param productionDepartmentId 生产部门id
     * @return List
     */
    @Select("SELECT dept_id,dept_name,manager_list FROM sys_department_info_addup WHERE year=#{year} AND month=#{month} AND parent_id=#{productionDepartmentId}")
    List<Department> selectDeptList2(@PathParam("year") int year, @PathParam("month") int month, @PathParam("productionDepartmentId") String productionDepartmentId);

    /**
     * 查询项目组
     *
     * @param deptId 部门id
     * @return list
     */
    @Select("SELECT dept_id AS team_id,dept_name AS team_name FROM sys_department_info WHERE parent_id = #{deptId}")
    List<TreeDepartment.SubDepartment> selectTeamList(String deptId);

    /**
     * 查询 所有数据 汇总
     *
     * @param year 年份
     * @return list
     */
    @Select("SELECT userid,SUM(contribution) AS contribution,SUM(settlement) AS settlement, SUM(working_day) AS working_day,SUM(labor_cost) AS labor_cost,SUM(expense_person) AS expense_person," +
            "SUM(expense_public) AS expense_public,SUM(expense_public_rent) AS expense_public_rent,SUM(expense_public_replace) AS expense_public_replace,SUM(expense_public_inside) AS expense_public_inside," +
            "SUM(cost) AS cost,name,dept_id,dept_name,team_id,team_name,position_level,cooperate_ratio " +
            "FROM (SELECT * FROM contribution_cost WHERE year=#{year} ORDER BY id DESC LIMIT 100000) a GROUP BY userid")
    List<ContributionCost> selectAll(int year);

    /**
     * 记录*年*月/部门主管
     *
     * @param departmentManager 实例
     * @return 行
     */
    @Insert("INSERT INTO dept_manager(year,month,dept_id,dept_name,managers) VALUES (#{year},#{month},#{deptId},#{deptName},#{managers})")
    int insertManager(DepartmentManager departmentManager);

    /**
     * 获取*年*月部门主管
     *
     * @param year  年
     * @param month 月
     * @return list
     */
    @Select("SELECT * FROM dept_manager WHERE year=#{year} AND month=#{month}")
    List<DepartmentManager> listManager(@Param("year") int year, @Param("month") int month);

}
