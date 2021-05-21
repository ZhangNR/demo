package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.entity.MonthlyReportSingleProject;
import com.example.demo.entity.PubProject;
import com.example.demo.service.IMonthlyReportSingleProjectService;
import com.example.demo.service.IPubProjectService;
import com.example.demo.untils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ExportTest
 *
 * @author ZhangJP
 * @date 2021/5/21
 */
@SpringBootTest
@Slf4j
public class ExportTest {

    @Autowired
    IPubProjectService service;
    @Autowired
    IMonthlyReportSingleProjectService monthlyReportSingleProjectService;

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

    public void excelImport(MultipartFile file, HttpServletResponse response) throws IOException {
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
        log.info("ids length {} , {}", idsAll.size(), idsAll);

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

        List<PubProject> pubProjects = JSON.parseArray(JSON.toJSONString(pubList), PubProject.class);
        log.info(JSON.toJSONString(pubProjects));

        service.saveBatch(pubProjects);

    }
}
