package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.RentDetail;
import com.example.demo.entity.RentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
public interface RentDetailMapper extends BaseMapper<RentDetail> {

    /**
     * 计算*年*月*部门*项目组房租总和
     *
     * @param deptId 部门id
     * @param teamId 项目组id
     * @param year   年份
     * @param month  月份
     * @return 房租总和
     */
    @Select("SELECT SUM(rent) FROM rent_detail WHERE dept_id=#{deptId} AND team_id=#{teamId} AND year=#{year} AND month=#{month}")
    BigDecimal selectMonthlyRent(@Param("deptId") String deptId, @Param("teamId") String teamId, @Param("year") int year, @Param("month") int month);

    /**
     * *年*月*房租总和/部门/项目组
     *
     * @param year  年份
     * @param month 月份
     * @return list
     */
    @Select("SELECT SUM(rent) AS rentAmount,dept_id,team_id FROM rent_detail WHERE year=#{year} AND month=#{month} GROUP BY dept_id,team_id")
    List<RentVO> listMonthlyRent(@Param("year") int year, @Param("month") int month);
}
