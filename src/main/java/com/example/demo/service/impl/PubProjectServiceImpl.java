package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.example.demo.config.Constant;
import com.example.demo.entity.*;
import com.example.demo.mapper.MonthlyReportSingleProjectMapper;
import com.example.demo.mapper.ProjectApprovalProjectMapper;
import com.example.demo.mapper.PubProjectMapper;
import com.example.demo.service.IPubProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.untils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjp
 * @since 2021-03-30
 */
@Service
@Slf4j
public class PubProjectServiceImpl extends ServiceImpl<PubProjectMapper, PubProject> implements IPubProjectService {
    private static final List<String> SMALL_WIDTH_FILEDS = Arrays.asList("系统编号", "立项编号", "年份", "月份", "合作比例");
    private static final List<String> LARGE_WIDTH_FILEDS = Arrays.asList("单册名称", "名称", "立项名");

    private final PubProjectMapper pubProjectMapper;
    private final MonthlyReportSingleProjectMapper singleProjectMapper;
    private final ProjectApprovalProjectMapper approvalProjectMapper;

    @Autowired
    public PubProjectServiceImpl(PubProjectMapper pubProjectMapper, MonthlyReportSingleProjectMapper singleProjectMapper, ProjectApprovalProjectMapper approvalProjectMapper) {
        this.pubProjectMapper = pubProjectMapper;
        this.singleProjectMapper = singleProjectMapper;
        this.approvalProjectMapper = approvalProjectMapper;
    }


    @Override
    public List<ReportMajor> getMajorList() {
        return pubProjectMapper.selectMajorList();
    }

    @Override
    public PageInfo<PubProject> getPublishProjectList(PublishSearchParams params) {
        Page<PubProject> page = new Page<>(params.getPageNumber(), params.getPageSize());
        QueryWrapper<PubProject> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(!StringUtils.isEmpty(params.getName()), "pub_project_name", params.getName());
        // 如果是部门负责人，查询部门所有的项目（除了废弃的）,否则查询自己创建的所有状态（除废弃）和创建人为空且状态为审核通过
        if (params.getUser().getIsLeader()) {
            queryWrapper.eq("project_approval_id", params.getProjectApprovalId()).ne("state", "废弃").orderByDesc("change_time");
        } else {
            queryWrapper.eq("project_approval_id", params.getProjectApprovalId())
                    .and(i -> i.and(j -> j.eq("creator_id", params.getUser().getUserId()).ne("state", "废弃"))
                            .or(k -> k.isNull("creator_id").eq("state", "审核通过")))
                    .orderByDesc("change_time");
        }

        Page<PubProject> pubProjectPage = pubProjectMapper.selectPage(page, queryWrapper);
        return new PageInfo<>(pubProjectPage.getTotal(), pubProjectPage.getCurrent(), pubProjectPage.getPages(), pubProjectPage.getRecords());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result saveProject(PubProject project) {

        if (StringUtils.isEmpty(project.getId())) {

            List<Integer> ids = JSON.parseArray(project.getSingleProjectIds(), Integer.class);
            List<MonthlyReportSingleProject> illegalSingleProjects = getIllegalSingleProjects(ids, Constant.STATE_UNPUBLISH);
            if (illegalSingleProjects.isEmpty()) {
                //更新单点的状态为已锁定
                singleProjectMapper.updateSingleProjectListState(Constant.STATE_CLOCK, ids);
                boolean b = save(project);
                if (b) {
                    return Response.success("保存成功");
                } else {
                    return Response.fail("保存失败");
                }
            } else {
                return Response.fail("选择单点已在其他单册中，请重新选择", illegalSingleProjects.stream().map(MonthlyReportSingleProject::getId).collect(Collectors.toList()));
            }
        } else {

            List<Integer> ids = JSON.parseArray(project.getSingleProjectIds(), Integer.class);
            PubProject oldPubProject = getById(project.getId());
            List<Integer> oldIds = JSON.parseArray(oldPubProject.getSingleProjectIds(), Integer.class);
            if (!ListUtil.compareLists(ids, oldIds)) {

                List<MonthlyReportSingleProject> illegalOldSingleProjects = getIllegalSingleProjects(oldIds, Constant.STATE_CLOCK);
                if (!illegalOldSingleProjects.isEmpty()) {
                    return Response.fail("选择单点已在其他单册中，请重新选择", illegalOldSingleProjects.stream().map(MonthlyReportSingleProject::getId).collect(Collectors.toList()));
                } else {

                    List<Integer> reduce = ListUtil.subtraction(ids, oldIds);
                    List<Integer> reduce1 = ListUtil.subtraction(oldIds, ids);

                    if (!reduce.isEmpty()) {
                        List<MonthlyReportSingleProject> illegalSingleProjects = getIllegalSingleProjects(reduce, Constant.STATE_UNPUBLISH);

                        if (illegalSingleProjects.isEmpty()) {
                            singleProjectMapper.updateSingleProjectListState(Constant.STATE_CLOCK, reduce);
                        } else {
                            return Response.fail("选择单点已在其他单册中，请重新选择", illegalSingleProjects.stream().map(MonthlyReportSingleProject::getId).collect(Collectors.toList()));
                        }
                    }

                    if (!reduce1.isEmpty()) {
                        singleProjectMapper.updateSingleProjectListState(Constant.STATE_UNPUBLISH, reduce1);
                    }
                }
            }
            boolean b = updateById(project);
            return b ? Response.success("更新成功") : Response.fail("更新失败");
        }
    }

    private List<MonthlyReportSingleProject> getIllegalSingleProjects(List<Integer> ids, String state) {
        QueryWrapper<MonthlyReportSingleProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(MonthlyReportSingleProject.class, info -> !"designer_team".equals(info.getColumn()) && !"files".equals(info.getColumn()) && !"update_date".equals(info.getColumn()))
                .in("id", ids);
        List<MonthlyReportSingleProject> singleProjectList = singleProjectMapper.selectList(queryWrapper);

        return singleProjectList.stream().filter(item -> !item.getPubState().equals(state)).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result commitProject(PubProjectVO project) {
        //获取单点信息
        List<Integer> ids = JSON.parseArray(project.getSingleProjectIds(), Integer.class);
        QueryWrapper<MonthlyReportSingleProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(MonthlyReportSingleProject.class, info -> !"designer_team".equals(info.getColumn()) && !"files".equals(info.getColumn()) && !"update_date".equals(info.getColumn()))
                .in("id", ids);
        List<MonthlyReportSingleProject> singleProjectList = singleProjectMapper.selectList(queryWrapper);
        project.setSingleProjects(singleProjectList);

        //判断出版文件是否存在，存在更新，不存在添加到数据库，然后创建审批实例
        if (StringUtils.isEmpty(project.getPubId())) {
            // 筛选单点状态不合法的
            List<MonthlyReportSingleProject> illegalSingleProjects = singleProjectList.stream().filter(item -> !item.getPubState().equals(Constant.STATE_UNPUBLISH)).collect(Collectors.toList());
            if (illegalSingleProjects.isEmpty()) {
                singleProjectMapper.updateSingleProjectListState(Constant.STATE_PUBLISHING, ids);
                PubProject pubProject = project.toPubProject();
                boolean b = save(pubProject);
                project.setPubId(pubProject.getId());
                return createProcessInstance(project, b);
            } else {
                return Response.fail("选择单点已在其他单册中，请重新选择", illegalSingleProjects.stream().map(MonthlyReportSingleProject::getId).collect(Collectors.toList()));
            }

        } else {
            //获取数据库存储的原来单点
            PubProject pubProject = getById(project.getPubId());
            List<Integer> oldIds = JSON.parseArray(pubProject.getSingleProjectIds(), Integer.class);

            List<MonthlyReportSingleProject> illegalOldSingleProjects = getIllegalSingleProjects(oldIds, Constant.STATE_CLOCK);
            if (!illegalOldSingleProjects.isEmpty()) {
                return Response.fail("选择单点已在其他单册中，请重新选择", illegalOldSingleProjects.stream().map(MonthlyReportSingleProject::getId).collect(Collectors.toList()));
            }

            if (!ListUtil.compareLists(ids, oldIds)) {

                List<Integer> reduce = ListUtil.subtraction(ids, oldIds);
                List<Integer> reduce1 = ListUtil.subtraction(oldIds, ids);

                if (!reduce.isEmpty()) {

                    List<MonthlyReportSingleProject> reduceSingleProjects = singleProjectList.stream().filter(item -> reduce.contains(item.getId())).collect(Collectors.toList());
                    List<MonthlyReportSingleProject> illegalSingleProjects = reduceSingleProjects.stream().filter(item -> !item.getPubState().equals(Constant.STATE_UNPUBLISH)).collect(Collectors.toList());
                    if (illegalSingleProjects.isEmpty()) {
                        singleProjectMapper.updateSingleProjectListState(Constant.STATE_PUBLISHING, reduce);
                    } else {
                        return Response.fail("选择单点已在其他单册中，请重新选择", illegalSingleProjects.stream().map(MonthlyReportSingleProject::getId).collect(Collectors.toList()));
                    }
                }
                if (!reduce1.isEmpty()) {
                    singleProjectMapper.updateSingleProjectListState(Constant.STATE_UNPUBLISH, reduce1);
                }
            } else {
                singleProjectMapper.updateSingleProjectListState(Constant.STATE_PUBLISHING, oldIds);
            }
            boolean b = updateById(project.toPubProject());
            return createProcessInstance(project, b);
        }
    }

    private Result createProcessInstance(PubProjectVO project, boolean b) {
        if (b) {
            // 拉起审批流程，返回 结果
            boolean isSuccess = DingServiceApi.processPublishInstance(project, DingServiceApi.getToken());
            if (isSuccess) {
                return Response.success("提交成功");
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Response.fail("创建审批实例失败");
            }
        } else {
            return Response.fail("提交失败");
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteProject(Integer id) {

        QueryWrapper<PubProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("single_project_ids").eq("id", id);

        Map<String, Object> map = getMap(queryWrapper);
        String singleProjectIds = (String) map.get("single_project_ids");
        List<Integer> ids = JSON.parseArray(singleProjectIds, Integer.class);
        singleProjectMapper.updateSingleProjectListState(Constant.STATE_UNPUBLISH, ids);

        UpdateWrapper<PubProject> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("state", "废弃").eq("id", id);

        boolean b = update(updateWrapper);
        return b ? Response.success("删除成功") : Response.fail("删除失败");
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Result callback(JSONObject json) {

        OapiProcessinstanceGetResponse.ProcessInstanceTopVo vo = DingServiceApi.getProcessinstance(json.getString("processInstanceId"), DingServiceApi.getToken());
        if (vo == null) {
            log.error("publish callback fail || processInstanceTopVo is null || {}", json.getString("processInstanceId"));
            return Response.fail("处理回调失败，获取审批详情失败", json.getString("processInstanceId"));
        }

        UpdateWrapper<PubProject> updateWrapper = new UpdateWrapper<>();

        if (!Constant.TYPE_START.equals(json.getString(Constant.TYPE))) {

            if (Constant.TYPE_FINISH.equals(json.getString(Constant.TYPE))) {
                if (Constant.RESULT_AGREE.equals(json.getString(Constant.RESULT))) {
                    //同意
                    //更新出版文件状态&单点的状态
                    List<OapiProcessinstanceGetResponse.FormComponentValueVo> vos = vo.getFormComponentValues();
                    JSONObject object = PublishFormComponent.getInfoByProcessInstance(vos);
                    Object pubId = object.get("pubId");
                    List<Integer> ids = object.getJSONArray("ids").toJavaList(Integer.class);

                    updateWrapper.set("state", Constant.STATE_PASSED).eq("id", pubId);
                    update(updateWrapper);
                    singleProjectMapper.updateSingleProjectListState(Constant.STATE_PUBLISHED, ids);


                } else if (Constant.RESULT_REFUSE.equals(json.getString(Constant.RESULT))) {
                    //拒绝
                    List<OapiProcessinstanceGetResponse.FormComponentValueVo> vos = vo.getFormComponentValues();
                    JSONObject object = PublishFormComponent.getInfoByProcessInstance(vos);
                    Object pubId = object.get("pubId");
                    List<Integer> ids = object.getJSONArray("ids").toJavaList(Integer.class);

                    updateWrapper.set("state", Constant.STATE_FAIL).eq("id", pubId);
                    update(updateWrapper);
                    singleProjectMapper.updateSingleProjectListState(Constant.STATE_CLOCK, ids);

                }
            } else if (json.getString(Constant.TYPE).equals(Constant.TYPE_TERMINATE)) {
                //终止
                List<OapiProcessinstanceGetResponse.FormComponentValueVo> vos = vo.getFormComponentValues();
                JSONObject object = PublishFormComponent.getInfoByProcessInstance(vos);
                Object pubId = object.get("pubId");
                List<Integer> ids = object.getJSONArray("ids").toJavaList(Integer.class);

                updateWrapper.set("state", Constant.STATE_SAVE).eq("id", pubId);
                update(updateWrapper);
                singleProjectMapper.updateSingleProjectListState(Constant.STATE_CLOCK, ids);

            }
        }

        return Response.success("处理回调成功");
    }

    /**
     * 获取该立项下的出版文件的数量 & 出版金额
     *
     * @param id 立项id
     * @return code
     */
    @Override
    public Amt getCountAndSum(Integer id) {
        return pubProjectMapper.selectCountAndSum(id);
    }

    /**
     * 数据导出
     *
     * @param params 参数
     */
    @Override
    public void exportPub(HttpServletResponse response, PublishSearchParams params) {
        String[] excelHeader = {"系统编号", "立项编号", "单册编号", "单册名称", "出版文件的状态", "单点编号集合", "总设计", "出版设计费（降点含税）", "偏差", "创建人", "创建时间"};

        String[] excelHeaderKey = {"id", "project_approval_id", "pub_project_num", "pub_project_name", "state", "single_project_ids", "sum_design_money", "publish_money", "deviation", "creator_name", "create_time"};

        QueryWrapper<PubProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("state", "废弃").eq(!StringUtils.isEmpty(params.getState()), "state", params.getState());
        if (!params.getUser().getIsLeader()) {
            queryWrapper.eq("creator_id", params.getUser().getUserId());
        }
        if ("0".equals(params.getApproval())) {
            queryWrapper.inSql("project_approval_id", "SELECT id FROM project_approval_project WHERE dept_id = " + params.getUser().getDeptId()).orderByAsc("id");
        } else {
            queryWrapper.eq("project_approval_id", params.getApproval());
        }


        List<Map<String, Object>> list = pubProjectMapper.selectMaps(queryWrapper);

        ExcelUtils.exportExcel(response, excelHeader, excelHeaderKey, list, params.getUser().getDeptName() + "文件出版" + LocalDate.now(), "单册文件");

    }

    /**
     * 导出模板 并且将立项，现有的出版，单点的数据一起。
     *
     * @param response response
     * @param params   参数
     */
    @Override
    public void exportModule(HttpServletResponse response, PublishSearchParams params) {
        String[] publishExcelHeader = {"系统编号", "立项编号", "单册编号", "单册名称", "出版文件的状态", "单点编号集合", "总设计", "出版设计费（降点后含税）", "偏差", "创建人", "创建时间"};
        String[] newPublishExcelHeader = {"立项编号", "单册编号", "单册名称", "总设计", "出版费"};
        String[] approvalExcelHeader = {"立项编号", "年", "合作比例", "立项名", "类型", "属性", "部门编号", "部门", "项目组编号", "项目组", "项目收入", "自有收入", "状态", "创建时间", "更新时间"};
        String[] singleProjectExcelHeader = {"系统编号", "部门编号", "部门", "项目组编号", "项目组", "大项", "名称", "年份", "月份", "勘察日期", "专业", "设计费", "备注", "状态", "出版状态", "创建人", "创建时间", "更新时间", "单册编号"};

        String[] publishExcelHeaderKey = {"id", "project_approval_id", "pub_project_num", "pub_project_name", "state", "single_project_ids", "sum_design_money", "publish_money", "deviation", "creator_name", "create_time"};
        String[] newPublishExcelHeaderKey = {"project_approval_id", "pub_project_num", "pub_project_name", "sum_design_money", "publish_money"};
        String[] approvalExcelHeaderKey = {"id", "year", "cooperate_ratio", "name", "type", "attribute", "dept_id", "dept_name", "sub_dept_id", "sub_dept_name", "project_income", "self_income", "state", "create_date", "update_date",};
        String[] singleProjectExcelHeaderKey = {"id", "dept_id", "dept_name", "sub_dept_id", "sub_dept_name", "parent_project", "name", "year", "month", "survey_date", "major", "design_money", "note", "state", "pub_state", "creator_name", "create_date", "update_date", "pub_project_num"};

        QueryWrapper<PubProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("state", "废弃").inSql("project_approval_id", "SELECT id FROM project_approval_project WHERE dept_id = " + params.getUser().getDeptId()).orderByAsc("id");

        List<Map<String, Object>> pubProjectList = pubProjectMapper.selectMaps(queryWrapper);

        QueryWrapper<ProjectApprovalProject> projectQueryWrapper = new QueryWrapper<>();
        projectQueryWrapper.eq("dept_id", params.getUser().getDeptId()).eq("state", "审核").orderByDesc("id");
        List<Map<String, Object>> approvalList = approvalProjectMapper.selectMaps(projectQueryWrapper);

        QueryWrapper<MonthlyReportSingleProject> singleProjectQueryWrapper = new QueryWrapper<>();
        singleProjectQueryWrapper.select(MonthlyReportSingleProject.class, info -> !"designer_team".equals(info.getColumn()) && !"files".equals(info.getColumn()))
                .eq("dept_id", params.getUser().getDeptId()).eq("state", "审核");

        List<Map<String, Object>> singleProjectList = singleProjectMapper.selectMaps(singleProjectQueryWrapper);

        SXSSFWorkbook workbook = new SXSSFWorkbook();
        addExcelSheet(publishExcelHeader, publishExcelHeaderKey, pubProjectList, workbook, "现有出版数据");
        addExcelSheet(newPublishExcelHeader, newPublishExcelHeaderKey, null, workbook, "新增单册");
        addExcelSheet(approvalExcelHeader, approvalExcelHeaderKey, approvalList, workbook, "立项");
        addExcelSheet(singleProjectExcelHeader, singleProjectExcelHeaderKey, singleProjectList, workbook, "单点数据");

        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String("导入模板".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        try {
            workbook.write(response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException e) {
            log.error("ExcelUtils - exportExcel {} [error]:  {}", LocalDateTime.now(), e);
        }

    }

    private void addExcelSheet(String[] excelHeader, String[] keys, List<Map<String, Object>> list, SXSSFWorkbook workbook, String sheetName) {
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        settingExcelHeader(workbook, sheet, excelHeader);
        if (list != null) {
            settingExcelContent(keys, list, workbook, sheet);
        }

    }

    private void settingExcelContent(String[] keys, List<Map<String, Object>> list, SXSSFWorkbook workbook, SXSSFSheet sheet) {
        SXSSFRow row;
        CellStyle cellStyle = ExcelUtils.contentStyle(workbook);

        row = sheet.createRow(1);
        row.setHeight((short) 500);

        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            row = sheet.createRow(i + 1);
            row.setHeight((short) 500);

            for (int j = 0; j < keys.length; j++) {
                if (map.get(keys[j]) == null) {
                    SXSSFCell cell = row.createCell(j);
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyle);
                } else {
                    fillCell(map.get(keys[j]), j, row, cellStyle);
                }

            }
        }
    }

    private static void fillCell(Object value, int i, SXSSFRow sxssfRow, CellStyle cellStyle) {
        if (value instanceof Date) {
            SXSSFCell sxssfCell = sxssfRow.createCell(i, CellType.STRING);
            Date dateValue = (Date) value;
            sxssfCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(dateValue));
            sxssfCell.setCellStyle(cellStyle);
        } else if (value instanceof LocalDate) {
            SXSSFCell sxssfCell = sxssfRow.createCell(i, CellType.STRING);
            LocalDate localDateValue = (LocalDate) value;
            sxssfCell.setCellValue(localDateValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            sxssfCell.setCellStyle(cellStyle);
        } else if (value instanceof LocalDateTime) {
            SXSSFCell sxssfCell = sxssfRow.createCell(i, CellType.STRING);
            LocalDateTime localDateTimeValue = (LocalDateTime) value;
            sxssfCell.setCellValue(localDateTimeValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            sxssfCell.setCellStyle(cellStyle);
        } else if (value instanceof Integer) {
            SXSSFCell sxssfCell = sxssfRow.createCell(i, CellType.NUMERIC);
            sxssfCell.setCellValue((Integer) value);
            sxssfCell.setCellStyle(cellStyle);
        } else if (value instanceof Double) {
            SXSSFCell sxssfCell = sxssfRow.createCell(i, CellType.NUMERIC);
            sxssfCell.setCellValue((Double) value);
            sxssfCell.setCellStyle(cellStyle);
        } else if (value instanceof BigDecimal) {
            SXSSFCell sxssfCell = sxssfRow.createCell(i, CellType.NUMERIC);
            sxssfCell.setCellValue(((BigDecimal) value).doubleValue());
            sxssfCell.setCellStyle(cellStyle);
        } else {
            SXSSFCell sxssfCell = sxssfRow.createCell(i, CellType.STRING);
            sxssfCell.setCellValue(value.toString());
            sxssfCell.setCellStyle(cellStyle);
        }

    }

    private void settingExcelHeader(SXSSFWorkbook workbook, SXSSFSheet sheet, String[] header) {
        SXSSFRow row = sheet.createRow(0);

        // 设置行高
        row.setHeight((short) 700);

        // 设置标题样式
        CellStyle cellStyle = ExcelUtils.headerStyle(workbook);

        // 设置列宽
        for (int i = 0; i < header.length; i++) {
            if (LARGE_WIDTH_FILEDS.contains(header[i])) {
                sheet.setColumnWidth(i, 50 * 256);
            } else if (SMALL_WIDTH_FILEDS.contains(header[i])) {
                sheet.setColumnWidth(i, 10 * 256);
            } else {
                sheet.setColumnWidth(i, 20 * 256);
            }

            SXSSFCell cell = row.createCell(i);
            cell.setCellValue(header[i]);
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 便捷录入
     *
     * @param file 上传文件
     * @return 响应码
     */
    @Override
    public Result importExcel(MultipartFile file, User user) {

        //系统编号	部门编号	部门	项目组编号	项目组	大项	名称	年份	月份	勘察日期	专业	设计费	设计人员	附件	备注	状态	出版状态	创建人	创建时间	更新时间	单册编号

        String[] excelHeaderKey = {"id", "dept_id", "dept_name", "sub_dept_id", "sub_dept_name", "parent_project", "name", "year", "month", "survey_date", "major", "design_money",
                "designer_team", "files", "note", "state", "pub_state", "creator_name", "create_date", "update_date", "pub_project_num"};

        String[] excelHeaderKey2 = {"project_approval_id", "pub_project_num", "pub_project_name", "sum_design_money", "publish_money"};


        try {
            List<Map<String, Object>> list = ExcelUtils.importExcel(file, "单点数据", excelHeaderKey);
            List<Map<String, Object>> pubList = ExcelUtils.importExcel(file, "新增单册", excelHeaderKey2);

            pubList.forEach(pub -> {
                String pubProjectNum = (String) pub.get("pub_project_num");

                List<Map<String, Object>> num = list.stream().filter(f -> pubProjectNum.equals(f.get("pub_project_num"))).collect(Collectors.toList());
                List<Integer> ids = num.stream().map(item -> (Integer) item.get("id")).collect(Collectors.toList());
                BigDecimal sum = num.stream().map(item -> new BigDecimal((String) item.get("design_money"))).reduce(BigDecimal.ZERO, BigDecimal::add);

                pub.put("single_project_ids", JSON.toJSONString(ids));
                pub.put("sum_design_money", sum);
                pub.put("creator_id", user.getUserId());
                pub.put("creator_name", user.getName());
                BigDecimal publishMoney = new BigDecimal((String) pub.get("publish_money"));
                BigDecimal divide = publishMoney.subtract(sum).abs().divide(publishMoney, 2, BigDecimal.ROUND_HALF_UP);
                pub.put("deviation", divide);

            });

            List<PubProject> pubProjects = JSON.parseArray(JSON.toJSONString(pubList), PubProject.class);

            return Response.success("导入成功", pubProjects);


        } catch (RuntimeException re) {
            log.error("便捷录入 RuntimeException {}", re);
            return Response.fail(re.getMessage());
        } catch (Exception e) {
            log.error("便捷录入 Exception {}", e);
            return Response.fail("数据导入失败！");
        }

    }

    @Override
    public Result batchSaveProject(List<PubProject> projectList) {
        boolean b = saveBatch(projectList);
        return b ? Response.success("保存成功！") : Response.fail("保存失败！");
    }

}
