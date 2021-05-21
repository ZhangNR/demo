package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ProjectApprovalProject;
import com.example.demo.entity.PubProjectVO;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
public interface ProjectApprovalProjectMapper extends BaseMapper<ProjectApprovalProject> {
    /**
     * 根据用户部门查询立项模块中项目 以及 出版文件
     * ("<script>" +
     * "SELECT ap.id,p.id AS pub_id,ap.sub_dept_id,ap.sub_dept_name,ap.partner_num,ap.name,p.pub_project_num,p.pub_project_name,p.state,p.single_project_ids," +
     * "p.sum_design_money,p.publish_money,p.deviation,p.creator_id,p.creator_name FROM project_approval_project AS ap" +
     * " LEFT JOIN pub_project as p ON p.project_approval_id = ap.id WHERE " +
     * "   <choose>" +
     * "       <when test='user.subDeptId == null || user.subDeptId == \"\"'> ap.dept_id = #{user.deptId} AND ((p.creator_id = #{user.userId} AND p.state !='废弃') OR p.state = '审核通过' OR p.id IS NULL) </when>" +
     * "       <otherwise> ap.sub_dept_id = #{user.subDeptId} AND ((p.creator_id = #{user.userId} AND p.state !='废弃') OR p.state = '审核通过' OR p.id IS NULL )  </otherwise>" +
     * "   </choose> " +
     * "ORDER BY ap.create_date DESC, p.change_time DESC" +
     * "</script>")
     *
     * @param page 分页
     * @param user 用户
     * @return PubProjectVO集合
     */

    Page<PubProjectVO> selectApprovalProjectList(Page<PubProjectVO> page, @Param("user") User user);
}
