package com.example.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjp
 * @since 2021-03-30
 */
public interface IPubProjectService extends IService<PubProject> {

    /**
     * 获取专业 （select下拉选项）
     *
     * @return ReportMajor集合
     */
    List<ReportMajor> getMajorList();


    /**
     * 分页 获取出版文件 By 立项项目id
     *
     * @param params pageSize,pageNumber,projectApprovalId,
     * @return list
     */
    PageInfo<PubProject> getPublishProjectList(PublishSearchParams params);

    /**
     * state=保存，保存出版文件（或更新）
     * state=提交，提交到审核
     *
     * @param project 保存参数
     * @return code
     */
    Result saveProject(PubProject project);

    /**
     * state=提交，提交到审核
     *
     * @param project 保存参数
     * @return code
     */
    Result commitProject(PubProjectVO project);

    /**
     * 废弃出版文件 （删除）
     *
     * @param id 废弃出版文件的id
     * @return code
     */
    Result deleteProject(Integer id);

    /**
     * 处理结果回调
     *
     * @param json 审批任务回调结果
     * @return response
     */
    Result callback(JSONObject json);

    /**
     * 获取该立项下的出版文件的数量 & 出版金额
     *
     * @param id 立项id
     * @return code
     */
    Amt getCountAndSum(Integer id);

    /**
     * 数据导出
     *
     * @param response response
     * @param params   参数
     */
    void exportPub(HttpServletResponse response, PublishSearchParams params);

    /**
     * 导出模板 并且将立项，现有的出版，单点的数据一起。
     *
     * @param response response
     * @param params   参数
     */
    void exportModule(HttpServletResponse response, PublishSearchParams params);


    /**
     * 便捷录入
     *
     * @param file 上传文件
     * @param user 创建人
     * @return 响应码
     */
    Result importExcel(MultipartFile file, User user);


    /**
     * 便捷录入
     *
     * @param projectList 出版list
     * @return 响应码
     */
    Result batchSaveProject(List<PubProject> projectList);

}
