package com.example.demo.controller;


import com.example.demo.entity.PageInfo;
import com.example.demo.entity.ProjectApprovalProject;
import com.example.demo.entity.PubProjectVO;
import com.example.demo.entity.PublishSearchParams;
import com.example.demo.service.IProjectApprovalProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/approval")
public class ProjectApprovalProjectController {
    private final IProjectApprovalProjectService approvalProjectService;

    @Autowired
    public ProjectApprovalProjectController(IProjectApprovalProjectService approvalProjectService) {
        this.approvalProjectService = approvalProjectService;
    }

    /**
     *  userId : '0600500566636271114'
     *  name: 'zjp'
     *  deptId: 104882752L
     *  department: [104882752, 296528292],
     */

    @RequestMapping("getApprovalProjectList")
    public PageInfo<PubProjectVO> getApprovalProjectList(@RequestBody PublishSearchParams params) {
        return approvalProjectService.getApprovalProjectList(params);
    }

    @RequestMapping("getApprovalList")
    public PageInfo<ProjectApprovalProject> getApprovalList(@RequestBody PublishSearchParams params) {
        return approvalProjectService.getApprovalList(params);
    }

    @RequestMapping("getAllApprovalList")
    public List<ProjectApprovalProject> getAllApprovalList(@RequestParam("deptId") String deptId) {
        return approvalProjectService.getAllApprovalList(deptId);
    }

}
