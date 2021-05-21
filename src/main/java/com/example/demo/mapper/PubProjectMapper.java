package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Amt;
import com.example.demo.entity.PubProject;
import com.example.demo.entity.ReportMajor;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2021-03-30
 */
public interface PubProjectMapper extends BaseMapper<PubProject> {
    /**
     * 查询月度报表中可选专业
     *
     * @return List<ReportMajor>
     */
    @Select("SELECT * FROM sys_monthly_report_major WHERE state = '有效'")
    List<ReportMajor> selectMajorList();

    /**
     * 获取该立项下的出版文件的数量 & 出版金额
     *
     * @param id 立项id
     * @return code
     */
    @Select("SELECT COUNT(id) AS count,IFNULL(SUM(publish_money), 0) AS sum FROM pub_project WHERE project_approval_id=#{id} AND state != '废弃'")
    Amt selectCountAndSum(Integer id);
}
