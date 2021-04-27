package com.example.demo.untils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.example.demo.entity.MonthlyReportSingleProject;
import com.example.demo.entity.PubProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * FormComponent
 *
 * @author ZhangJP
 * @date 2020/8/5
 */
public class PublishFormComponent {

    private static final Logger logger = LoggerFactory.getLogger(PublishFormComponent.class);

    static List<OapiProcessinstanceCreateRequest.FormComponentValueVo> getFormComponentValues(PubProjectVO project) {

        // 审批流表单控件，设置各表单项值
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<>();

        // 文件编号
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo1.setName("文件编号");
        vo1.setValue(String.valueOf(project.getPubId()));

        // 项目编号
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo2.setName("项目编号");
        vo2.setValue(project.getPartnerNum());

        // 项目名称
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo3.setName("项目名称");
        vo3.setValue(project.getName());

        // 单册编号
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("单册编号");
        vo4.setValue(project.getPubProjectNum());

        // 单册名称
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo5.setName("单册名称");
        vo5.setValue(project.getPubProjectName());

        // 单项清单
        ArrayList<Object> list = new ArrayList<>();
        List<MonthlyReportSingleProject> singleProjects = project.getSingleProjects();
        for (MonthlyReportSingleProject singleProject : singleProjects) {
            ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo> itemNameList = new ArrayList<>();
            // 明细包含控件
            // 明细-单项编号
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName1.setName("单项编号");
            itemName1.setValue(String.valueOf(singleProject.getId()));
            itemNameList.add(itemName1);
            // 明细-项目名称
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName2.setName("项目名称");
            itemName2.setValue(singleProject.getParentProject());
            itemNameList.add(itemName2);
            // 明细-单项名称
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName3.setName("单项名称");
            itemName3.setValue(singleProject.getName());
            itemNameList.add(itemName3);
            // 明细-专业
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName4.setName("专业");
            itemName4.setValue(singleProject.getMajor());
            itemNameList.add(itemName4);
            // 明细-创建人
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName5.setName("创建人");
            itemName5.setValue(singleProject.getCreatorName());
            itemNameList.add(itemName5);
            // 明细-设计费用
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName6.setName("设计费用");
            itemName6.setValue(String.valueOf(singleProject.getDesignMoney()));
            itemNameList.add(itemName6);
            // 明细-勘查日期
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName7.setName("勘查日期");
            itemName7.setValue(singleProject.getSurveyDate().toString());
            itemNameList.add(itemName7);

            list.add(itemNameList);

        }
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo6.setName("单项清单");
        vo6.setValue(JSON.toJSONString(list));

        // 月度报表预估产值
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo7.setName("月度报表预估产值");
        vo7.setValue(String.valueOf(project.getSumDesignMoney()));

        // 出版勘察设计费(降点含税)
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo8.setName("出版勘察设计费(降点含税)");
        vo8.setValue(String.valueOf(project.getPublishMoney()));

        // 偏差
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo9.setName("偏差");
        vo9.setValue(String.valueOf(project.getDeviation()));


        // 添加到表单
        formComponentValues.add(vo1);
        formComponentValues.add(vo2);
        formComponentValues.add(vo3);
        formComponentValues.add(vo4);
        formComponentValues.add(vo5);
        formComponentValues.add(vo6);
        formComponentValues.add(vo7);
        formComponentValues.add(vo8);
        formComponentValues.add(vo9);

        return formComponentValues;
    }

    public static JSONObject getInfoByProcessInstance(List<OapiProcessinstanceGetResponse.FormComponentValueVo> vos) {
        JSONObject object = new JSONObject();
        ArrayList<Integer> ids = new ArrayList<>();

        for (OapiProcessinstanceGetResponse.FormComponentValueVo vo : vos) {
            if ("文件编号".equals(vo.getName())) {
                String idValue = vo.getValue();
                object.put("pubId", idValue);
            }
            if ("单项清单".equals(vo.getName())) {
                String listValue = vo.getValue();
                JSONArray array = JSON.parseArray(listValue);
                for (int i = 0; i < array.size(); i++) {

                    getSingleProjectIds(ids, array.getJSONObject(i).getJSONArray("rowValue"));
                }
                logger.info(listValue);
                object.put("ids", ids);
            }
        }
        return object;
    }

    private static void getSingleProjectIds(ArrayList<Integer> ids, JSONArray rowValue) {
        for (int j = 0; j < rowValue.size(); j++) {
            if ("单项编号".equals(rowValue.getJSONObject(j).getString("label"))) {
                String value = rowValue.getJSONObject(j).getString("value");
                ids.add(Integer.valueOf(value));
            }
        }
    }
}
