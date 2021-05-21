package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.MonthlyReportSingleProject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2021-03-30
 */
public interface MonthlyReportSingleProjectMapper extends BaseMapper<MonthlyReportSingleProject> {
    /**
     * 查询清单列表中的单点的状态
     *
     * @param ids id集合
     * @return 状态集合
     */
    @Select("<script>" +
            "   SELECT pub_state FROM monthly_report_single_project WHERE id IN" +
            "       <foreach collection='list' item='id' index='index' separator=',' open='(' close=')'> " +
            "           #{id}" +
            "       </foreach>" +
            "</script>")
    List<String> selectSingleProjectStateByIds(List<Integer> ids);

    /**
     * 批量更新单项状态 By id
     *
     * @param state 状态
     * @param ids id集合
     * @return rows
     */
    @Update("<script>" +
            "update monthly_report_single_project set pub_state = #{state} where id in" +
            "   <foreach collection='ids' item='id' index='index' open='(' close=')' separator=','> " +
            "      #{id}" +
            "   </foreach>" +
            "</script>")
    int updateSingleProjectListState(@Param("state") String state, @Param("ids") List<Integer> ids);
}
