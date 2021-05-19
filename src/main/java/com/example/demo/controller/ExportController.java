package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.MonthlyReportSingleProject;
import com.example.demo.entity.PubProject;
import com.example.demo.service.IMonthlyReportSingleProjectService;
import com.example.demo.service.IPubProjectService;
import com.example.demo.untils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ExportController
 *
 * @author ZhangJP
 * @date 2021/3/29
 */
@RestController
@Api(tags = "导入导出模块")
public class ExportController {

    private final IPubProjectService service;
    private final IMonthlyReportSingleProjectService monthlyReportSingleProjectService;

    @Autowired
    public ExportController(IPubProjectService service, IMonthlyReportSingleProjectService monthlyReportSingleProjectService) {
        this.service = service;
        this.monthlyReportSingleProjectService = monthlyReportSingleProjectService;
    }


    @ApiOperation(value = "姓名、年级导出测试")
    @GetMapping("excelExport")
    public void excelExport(HttpServletResponse response) {
        String[] excelHeader = {"姓名", "年纪"};

        String[] excelHeaderKey = {"name", "age"};

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> tmp = new HashMap<>(16);
        tmp.put("name", "张三");
        tmp.put("age", "18");
        list.add(tmp);

        Map<String, Object> tmp1 = new HashMap<>(16);
        tmp1.put("name", "李四");
        tmp1.put("age", "20");
        list.add(tmp1);

        ExcelUtils.exportExcel(response, excelHeader, excelHeaderKey, list, "统计表格", "用户数据");
    }

    @ApiOperation(value = "导入", notes = "出版便捷录入测试")
    @PostMapping("import")
    public void excelImport(MultipartFile file, HttpServletResponse response) throws Exception {
        String[] excelHeader = {"系统编号", "部门编号", "部门", "项目组编号", "项目组", "大项", "名称", "年份", "月份", "勘察日期", "专业", "设计费", "设计人员", "附件", "备注", "状态", "出版状态", "创建人", "创建时间", "更新时间", "单册编号"};
        String[] excelHeaderKey = {"id", "dept_id", "dept_name", "sub_dept_id", "sub_dept_name", "parent_project", "name", "year", "month", "survey_date", "major", "design_money",
                "designer_team", "files", "note", "state", "pub_state", "creator_name", "create_date", "update_date", "pub_project_num"};

        String[] excelHeaderKey2 = {"project_approval_id", "pub_project_num", "pub_project_name", "sum_design_money", "publish_money", "creator_name"};

        List<Map<String, Object>> list = ExcelUtils.importExcel(file, "单点数据", excelHeaderKey);

        List<Map<String, Object>> pubList = ExcelUtils.importExcel(file, "新增出版模板", excelHeaderKey2);

        List<Integer> idsAll = list.stream().filter(item -> item.get("pub_project_num") != "").map(m -> (Integer) m.get("id")).collect(Collectors.toList());

        UpdateWrapper<MonthlyReportSingleProject> updateWrapper = new UpdateWrapper<>();
        idsAll.forEach(id -> {
            updateWrapper.set("pub_state", "已锁定").eq("id", id);
            monthlyReportSingleProjectService.update(updateWrapper);
        });
        System.out.println(idsAll);
        System.out.println("长度：" + idsAll.size());

        pubList.forEach(pub -> {
            String pubProjectNum = (String) pub.get("pub_project_num");

            List<Map<String, Object>> num = list.stream().filter(f -> pubProjectNum.equals(f.get("pub_project_num"))).collect(Collectors.toList());
            List<Integer> ids = num.stream().map(item -> (Integer) item.get("id")).collect(Collectors.toList());
            BigDecimal sum = num.stream().map(item -> new BigDecimal((String) item.get("design_money"))).reduce(BigDecimal.ZERO, BigDecimal::add);

            pub.put("single_project_ids", JSON.toJSONString(ids));
            pub.put("sum_design_money", sum);
            BigDecimal publishMoney = new BigDecimal((String) pub.get("publish_money"));
            BigDecimal divide = publishMoney.subtract(sum).abs().divide(publishMoney, 2, BigDecimal.ROUND_HALF_UP);
            pub.put("deviation", divide);

        });

        List<PubProject> pubProjects = JSONArray.parseArray(JSON.toJSONString(pubList), PubProject.class);
        System.out.println(JSON.toJSONString(pubProjects));

        service.saveBatch(pubProjects);


//        ExcelUtils.exportExcel(response, excelHeader, excelHeaderKey, list, "出版", "出版数据");

        /*list.stream().collect(Collectors.groupingBy(t -> t.get("pub_project_num") == "" ? "0" : t.get("pub_project_num"), Collectors.toList())).forEach((key, groupMap) -> {
            if (!"0".equals(key)) {
                System.out.println(key);

                List<Integer> ids = groupMap.stream().map(item -> (Integer) item.get("id")).collect(Collectors.toList());
                BigDecimal sum = groupMap.stream().map(item -> new BigDecimal((String) item.get("design_money"))).reduce(BigDecimal.ZERO, BigDecimal::add);

                System.out.println(JSON.toJSONString(ids));
                System.out.println(sum);
            }
        });*/

    }


}
