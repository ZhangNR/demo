package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import com.example.demo.service.IPubProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjp
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/project")
public class PubProjectController {
    private final IPubProjectService pubProjectService;

    @Autowired
    public PubProjectController(IPubProjectService pubProjectService) {
        this.pubProjectService = pubProjectService;
    }

    @GetMapping("getMajorList")
    public List<ReportMajor> getMajorList() {
        return pubProjectService.getMajorList();
    }

    @PostMapping("getPublishProjectList")
    public PageInfo<PubProject> getPublishProjectList(@RequestBody PublishSearchParams params) {
        return pubProjectService.getPublishProjectList(params);
    }

    @PostMapping("saveProject")
    public Result saveProject(@RequestBody PubProject project) {
        return pubProjectService.saveProject(project);
    }

    @PostMapping("batchSaveProject")
    public Result batchSaveProject(@RequestBody List<PubProject> projectList) {
        return pubProjectService.batchSaveProject(projectList);
    }

    @PostMapping("commitProject")
    public Result commitProject(@RequestBody PubProjectVO project) {
        return pubProjectService.commitProject(project);
    }

    @PostMapping("callback")
    public Result callback(@RequestBody JSONObject json) {
        return pubProjectService.callback(json);
    }

    @GetMapping("deleteProject")
    public Result deleteProject(@RequestParam("id") Integer id) {
        return pubProjectService.deleteProject(id);
    }

    @GetMapping("getCountAndSum")
    public Amt getCountAndSum(@RequestParam("id") Integer id) {
        return pubProjectService.getCountAndSum(id);
    }

    @PostMapping("exportPub")
    public void exportPub(HttpServletResponse response, @RequestBody PublishSearchParams params) {
        pubProjectService.exportPub(response, params);
    }

    @PostMapping("exportModule")
    public void exportModule(HttpServletResponse response, @RequestBody PublishSearchParams params) {
        pubProjectService.exportModule(response, params);
    }

    @PostMapping("importExcel")
    public Result importExcel(MultipartFile file, User user) {
        return pubProjectService.importExcel(file, user);
    }
}
