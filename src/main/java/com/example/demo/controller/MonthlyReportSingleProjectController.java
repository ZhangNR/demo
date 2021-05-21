package com.example.demo.controller;


import com.alibaba.fastjson.JSON;
import com.example.demo.entity.*;
import com.example.demo.service.IMonthlyReportSingleProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjp
 * @since 2021-03-30
 */
@RestController
@RequestMapping("single")
@Slf4j
public class MonthlyReportSingleProjectController {

    private final IMonthlyReportSingleProjectService singleProjectService;

    @Autowired
    public MonthlyReportSingleProjectController(IMonthlyReportSingleProjectService singleProjectService) {
        this.singleProjectService = singleProjectService;
    }

    @PostMapping("getSingleProjectList")
    public PageInfo<MonthlyReportSingleProject> getSingleProjectList(@RequestBody PublishSearchParams searchParams) {
        log.info(JSON.toJSONString(searchParams));
        return singleProjectService.getSingleProjectList(searchParams);
    }

    @PostMapping("getSingleProjectListByIds")
    public Result getSingleProjectListByIds(@RequestBody PubProject project) {
        log.info(project.getSingleProjectIds());
        return singleProjectService.getSingleProjectListByIds(project.getSingleProjectIds());
    }
}
