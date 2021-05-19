package com.example.demo.mapper;

import com.example.demo.entity.PerformanceDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zjp
 * @since 2020-09-23
 */
@Repository
@Mapper
public interface PerformanceDetailMapper {

    /**
     * 获取*年*月 贡献产值集合
     *
     * @param year  年
     * @param month 月
     * @return list
     */
    @Select("SELECT pd.user_id,pd.contribution_output FROM performance_detail AS pd INNER JOIN performance_dept AS d ON d.id = pd.performance_dept_id AND d.year=#{year} AND d.month=#{month} AND d.state = '审核'")
    List<PerformanceDetailVO> listContribution(@Param("year") int year, @Param("month") int month);
}
