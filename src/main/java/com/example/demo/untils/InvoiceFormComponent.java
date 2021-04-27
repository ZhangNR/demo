package com.example.demo.untils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.response.OapiProcessinstanceGetResponse;
import com.example.demo.config.Constant;
import com.example.demo.entity.InvoicePaidVO;
import com.example.demo.entity.OrderContract;
import com.example.demo.entity.OrderContractProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * FormComponent
 *
 * @author ZhangJP
 * @date 2020/8/5
 */
public class InvoiceFormComponent {

    private InvoiceFormComponent() {
    }

    private static final Logger logger = LoggerFactory.getLogger(InvoiceFormComponent.class);

    @SuppressWarnings("AlibabaMethodTooLong")
    static List<OapiProcessinstanceCreateRequest.FormComponentValueVo> getFormComponentValues(InvoicePaidVO invoice) {
        logger.info("审批表单内容：{}", invoice);

        // 审批流表单控件，设置各表单项值
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<>();

        // 开票名称
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo0 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo0.setName("开票名称");
        vo0.setValue(invoice.getInvoicingName());

        // 订单合同总金额
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo1.setName("订单合同总金额");
        vo1.setValue(String.valueOf(invoice.getSumOrderAmount()));

        // 申请总金额
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo2 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo2.setName("申请总金额");
        vo2.setValue(String.valueOf(invoice.getSumApplyAmount()));

        // 税率
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo3.setName("税率");
        vo3.setValue(invoice.getTaxRate());

        // 申请类型
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo4.setName("申请类型");
        vo4.setValue(invoice.getApplyType());

        // 合作比例
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo5.setName("合作比例");
        vo5.setValue(invoice.getRatio());

        // 简易征收项目
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo6.setName("简易征收项目");
        vo6.setValue(String.valueOf(invoice.getCollectionProject()));

        if (Constant.CHECK_TRUE.equals(invoice.getCollectionProject()) && !invoice.getAttachment().isEmpty()) {
            // 凭证
            OapiProcessinstanceCreateRequest.FormComponentValueVo vo7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            vo7.setName("凭证");
            vo7.setValue(JSON.toJSONString(invoice.getAttachment()));
            formComponentValues.add(vo7);
        }

        // 备注
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo8.setName("备注");
        vo8.setValue(invoice.getNote());

        // 订单合同信息
        OrderContract order = invoice.getOrder();
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo81 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo81.setName("订单合同编号");
        vo81.setValue(order.getNum());

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo82 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo82.setName("订单合同名称");
        vo82.setValue(order.getName());

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo83 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo83.setName("订单合同金额");
        vo83.setValue(String.valueOf(order.getMoney()));

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo85 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo85.setName("框架合同编号");
        vo85.setValue(order.getFrameworkNum());

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo86 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo86.setName("框架合同名称");
        vo86.setValue(order.getFrameworkName());

        //格式化设置
        DecimalFormat decimalFormat = new DecimalFormat("###0.00");

        OapiProcessinstanceCreateRequest.FormComponentValueVo vo87 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo87.setName("框架合同金额");
        vo87.setValue(String.valueOf(order.getFrameworkMoney() == null ? 0 : decimalFormat.format(order.getFrameworkMoney())));

        // 项目信息
        ArrayList<Object> list = new ArrayList<>();
        List<OrderContractProjectVO> projectList = invoice.getProjectList();
        for (OrderContractProjectVO project : projectList) {
            ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo> itemNameList = new ArrayList<>();
            // 明细包含控件
            // 明细-订单合同编号
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName1 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName1.setName("项目编号");
            itemName1.setValue(project.getProjectNum());
            itemNameList.add(itemName1);
            // 明细-订单合同金额
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName3 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName3.setName("项目总金额");
            itemName3.setValue(String.valueOf(project.getSumProjectMoney()));
            itemNameList.add(itemName3);
            // 明细-订单合同已开票金额
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName4 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName4.setName("已开票金额");
            itemName4.setValue(String.valueOf(project.getAmountPaid()));
            itemNameList.add(itemName4);
            // 明细-订单合同剩余金额
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName5 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName5.setName("剩余金额");
            itemName5.setValue(String.valueOf(project.getBalance()));
            itemNameList.add(itemName5);
            // 明细-申请金额
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName6 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName6.setName("申请金额");
            itemName6.setValue(String.valueOf(project.getApplyAmount()));
            itemNameList.add(itemName6);
            // 明细-是否结算完毕
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName7 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName7.setName("是否结算完毕");
            itemName7.setValue(project.getSettlementCompleted() == 1 ? "是" : "否");
            itemNameList.add(itemName7);
            // 明细-是否结算完毕
            OapiProcessinstanceCreateRequest.FormComponentValueVo itemName8 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
            itemName8.setName("序号");
            itemName8.setValue(String.valueOf(project.getOrderContractProjectId()));
            itemNameList.add(itemName8);

            list.add(itemNameList);
        }
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo9 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo9.setName("项目信息");
        vo9.setValue(JSON.toJSONString(list));

        // 是否归属生产部门
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo10 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo10.setName("是否归属生产部门");
        vo10.setValue(invoice.getPtProductionDept());

        //项目归属部门
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo11 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo11.setName("项目归属部门");
        vo11.setValue(invoice.getPtDeptName());

        //项目归属项目组
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo12 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo12.setName("项目归属项目组");
        vo12.setValue(invoice.getPtSubDeptName() == null ? "" : invoice.getPtSubDeptName());

        // 归属部门负责人
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo13 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo13.setName("归属部门负责人");
        vo13.setValue(invoice.getPrincipalUserId());

        // 公司名称
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo14 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo14.setName("公司名称");
        vo14.setValue(invoice.getCustomerName());

        // 合作比例
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo15 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo15.setName("合作比例");
        vo15.setValue(invoice.getRatio());

        // 税号
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo16 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo16.setName("税号");
        vo16.setValue(invoice.getTaxNumber());

        // 开户行
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo17 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo17.setName("开户行");
        vo17.setValue(invoice.getBankName());

        // 银行账号
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo18 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo18.setName("银行账号");
        vo18.setValue(invoice.getBankAccount());

        // 地址
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo19 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo19.setName("地址");
        vo19.setValue(invoice.getAddress());

        // 电话
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo20 = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo20.setName("电话");
        vo20.setValue(invoice.getPhone());


        // 添加到表单
        formComponentValues.add(vo0);
        formComponentValues.add(vo1);
        formComponentValues.add(vo2);
        formComponentValues.add(vo3);
        formComponentValues.add(vo4);
        formComponentValues.add(vo5);
        formComponentValues.add(vo6);
        formComponentValues.add(vo8);

        formComponentValues.add(vo81);
        formComponentValues.add(vo82);
        formComponentValues.add(vo83);

        formComponentValues.add(vo85);
        formComponentValues.add(vo86);
        formComponentValues.add(vo87);

        formComponentValues.add(vo9);
        formComponentValues.add(vo10);
        formComponentValues.add(vo11);
        formComponentValues.add(vo12);
        formComponentValues.add(vo13);
        formComponentValues.add(vo14);
        formComponentValues.add(vo15);
        formComponentValues.add(vo16);
        formComponentValues.add(vo17);
        formComponentValues.add(vo18);
        formComponentValues.add(vo19);
        formComponentValues.add(vo20);

        return formComponentValues;
    }

    public static List<Integer> getUpdateIdsByProcessInstance(List<OapiProcessinstanceGetResponse.FormComponentValueVo> vos) {
        List<Integer> ids = new ArrayList<>();

        for (OapiProcessinstanceGetResponse.FormComponentValueVo vo : vos) {
            if ("项目信息".equals(vo.getName())) {
                JSONArray array = JSON.parseArray(vo.getValue());
                for (int i = 0; i < array.size(); i++) {
                    settlementCompletedIds(ids, array.getJSONObject(i).getJSONArray("rowValue"));
                }
            }
        }
        return ids;
    }

    private static void settlementCompletedIds(List<Integer> ids, JSONArray rowValue) {
        HashMap<String, String> map = new HashMap<>(16);

        for (int j = 0; j < rowValue.size(); j++) {
            map.put(rowValue.getJSONObject(j).getString("label"), rowValue.getJSONObject(j).getString("value"));
        }

        if ("是".equals(map.get("是否结算完毕"))) {
            ids.add(Integer.valueOf(map.get("序号")));
        }

    }
}
