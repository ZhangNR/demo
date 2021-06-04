package com.example.demo.untils;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.example.demo.config.Constant;
import com.example.demo.entity.InvoicePaidVO;
import com.example.demo.entity.PubProjectVO;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;


/**
 * DingServiceApi
 *
 * @author ZhangJP
 * @date 2020/6/1
 */
public class DingServiceApi {
    private static final Logger logger = LoggerFactory.getLogger(DingServiceApi.class);

    private DingServiceApi() {

    }

    /**
     * 获取access_token
     */
    public static String getToken() {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest request = new OapiGettokenRequest();
            request.setAppkey(Constant.APP_KEY);
            request.setAppsecret(Constant.APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            if (response.getErrcode() == 0) {
                return response.getAccessToken();
            }
        } catch (ApiException e) {
            logger.error("获取access_token: ", e);
        }
        return "";
    }

    /**
     * 通过免登授权码和access_token获取用户的userid
     */
    public static String getUserInfo(String requestAuthCode, String accessToken) {

        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
            OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
            request.setCode(requestAuthCode);
            request.setHttpMethod("GET");
            OapiUserGetuserinfoResponse response = client.execute(request, accessToken);
            return response.getUserid();
        } catch (ApiException e) {
            logger.error("通过免登授权码和access_token获取用户的userid: ", e);
        }
        return null;
    }


    /**
     * 获取用户详情
     *
     * @param userid      员工id
     * @param accessToken 调用接口凭证
     * @return 用户详情
     */
    public static OapiUserGetResponse getUser(String userid, String accessToken) {

        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userid);
            request.setHttpMethod("GET");
            return client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error("获取用户详情: ", e);
            return null;
        }
    }

    /**
     * 查询指定用户的所有上级父部门路径
     *
     * @param userid      查询的用户的id
     * @param accessToken 调用接口凭证
     * @return department 该部门的所有父部门id列表
     */
    public static String getUserDeptList(String userid, String accessToken) {

        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts");
            OapiDepartmentListParentDeptsRequest request = new OapiDepartmentListParentDeptsRequest();
            request.setUserId(userid);
            request.setHttpMethod("GET");
            OapiDepartmentListParentDeptsResponse response = client.execute(request, accessToken);
            return response.getDepartment();
        } catch (ApiException e) {
            logger.error("查询指定用户的所有上级父部门路径: ", e);
        }
        return null;
    }

    /**
     * 获取部门列表
     *
     * @param id          父部门id
     * @param accessToken token
     * @return list
     */
    public static List<OapiDepartmentListResponse.Department> getDeptList(String id, String accessToken) {

        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
            OapiDepartmentListRequest request = new OapiDepartmentListRequest();
            request.setId(id);
            request.setHttpMethod("GET");
            OapiDepartmentListResponse response = client.execute(request, accessToken);
            return response.getDepartment();
        } catch (ApiException e) {
            logger.error("获取部门信息: ", e);
        }
        return Collections.emptyList();
    }


    /**
     * 获取部门信息
     *
     * @param deptId      部门id
     * @param accessToken accessToken
     * @return Long
     */
    public static OapiDepartmentGetResponse getDeptInfo(String deptId, String accessToken) {

        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/get");
            OapiDepartmentGetRequest req = new OapiDepartmentGetRequest();
            req.setId(deptId);
            req.setHttpMethod("GET");
            return client.execute(req, accessToken);
        } catch (ApiException e) {
            logger.error("获取部门信息: ", e);
        }
        return null;
    }


    /**
     * 查询指定部门的所有上级父部门
     *
     * @param deptId      部门id
     * @param accessToken 调用企业接口凭证
     * @return 指定部门的所有父部门ID列表
     */
    public static List<Long> getParentDeptsList(String deptId, String accessToken) {

        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list_parent_depts_by_dept");
            OapiDepartmentListParentDeptsByDeptRequest req = new OapiDepartmentListParentDeptsByDeptRequest();
            req.setId(deptId);
            req.setHttpMethod("GET");
            OapiDepartmentListParentDeptsByDeptResponse rsp = client.execute(req, accessToken);
            return rsp.getParentIds();
        } catch (ApiException e) {
            logger.error("查询指定部门的所有上级父部门: ", e);
        }
        return Collections.emptyList();
    }

    /**
     * 获取审批钉盘空间信息
     *
     * @param token  token
     * @param userId 用户userid
     * @return 审批钉盘空间id
     */
    public static String getSpaceId(String userId, String token) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/cspace/info");
            OapiProcessinstanceCspaceInfoRequest req = new OapiProcessinstanceCspaceInfoRequest();
            req.setUserId(userId);
            OapiProcessinstanceCspaceInfoResponse rsp = client.execute(req, token);
            if (rsp.isSuccess()) {
                return rsp.getResult().getSpaceId().toString();
            }

        } catch (ApiException e) {
            logger.error("获取审批钉盘空间信息: ", e);
        }
        return "";
    }

    /**
     * 授权预览审批附件
     *
     * @param processInstanceId 审批实例id
     * @param fileId            要预览的文件id
     * @param userId            用户userid
     * @param token             token
     */
    public static void preview(String fileId, String userId, String processInstanceId, String token) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/cspace/preview");
        OapiProcessinstanceCspacePreviewRequest req = new OapiProcessinstanceCspacePreviewRequest();
        OapiProcessinstanceCspacePreviewRequest.GrantCspaceRequest grantCspaceRequest = new OapiProcessinstanceCspacePreviewRequest.GrantCspaceRequest();
        grantCspaceRequest.setAgentid(Long.valueOf(Constant.AGENT_ID));
        grantCspaceRequest.setProcessInstanceId(processInstanceId);
        grantCspaceRequest.setFileId(fileId);
        grantCspaceRequest.setUserid(userId);
        req.setRequest(grantCspaceRequest);
        try {
            client.execute(req, token);
        } catch (ApiException e) {
            logger.error("授权预览审批附件: ", e);
        }
    }

    /**
     * 发起审批实例
     *
     * @param invoice     数据
     * @param accessToken token
     * @apiNote 审核人userid列表 request.setApprovers("") 可填
     * @apiNote 抄送人userid列表 request.setCcList("userid1,userid2"); 可填
     * @apiNote 抄送时间，分为（START, FINISH, START_FINISH request.setCcPosition("START_FINISH"); 可填
     * @apiNote {name:"",value:""} request.setFormComponentValues(formComponentValues); 必填
     */
    public static String processInvoiceInstance(InvoicePaidVO invoice, String accessToken) {

        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setAgentId(Long.valueOf(Constant.AGENT_ID));
            //审批流的唯一码
            request.setProcessCode(Constant.PROCESS_CODE);
            //审批实例发起人的userid
            request.setOriginatorUserId(invoice.getCreatorId());
            //发起人所在的部门
            request.setDeptId(Long.valueOf(invoice.getDeptId()));

            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = InvoiceFormComponent.getFormComponentValues(invoice);
            request.setFormComponentValues(formComponentValues);

            OapiProcessinstanceCreateResponse response = client.execute(request, accessToken);
            if (response.isSuccess()) {
                return response.getProcessInstanceId();
            }
        } catch (ApiException e) {
            logger.error("发起审批实例: ", e);
        }
        return null;
    }

    /**
     * 发起审批实例
     *
     * @param project     数据
     * @param accessToken token
     * @apiNote 审核人userid列表 request.setApprovers("") 可填
     * @apiNote 抄送人userid列表 request.setCcList("userid1,userid2"); 可填
     * @apiNote 抄送时间，分为（START, FINISH, START_FINISH request.setCcPosition("START_FINISH"); 可填
     * @apiNote {name:"",value:""} request.setFormComponentValues(formComponentValues); 必填
     */
    public static boolean processPublishInstance(PubProjectVO project, String accessToken) {

        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
            OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
            request.setAgentId(Long.valueOf(Constant.AGENT_ID));
            //审批流的唯一码
            request.setProcessCode(Constant.PROCESS_CODE);

            List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = PublishFormComponent.getFormComponentValues(project);
            request.setFormComponentValues(formComponentValues);

            //审批实例发起人的userid
            request.setOriginatorUserId(project.getCreatorId());
            //发起人所在的部门
            if (project.getSubDeptId() == 0) {
                request.setDeptId(project.getDeptId());
            } else {
                request.setDeptId(project.getSubDeptId());
            }

            OapiProcessinstanceCreateResponse response = client.execute(request, accessToken);
            logger.info("创建审批实例 {}", JSON.toJSONString(response));
            if (response.getErrcode() == 0L) {
                return true;
            }
        } catch (ApiException e) {
            logger.error("processPublishInstance: ", e);
        }
        return false;
    }

    /**
     * 获取审批实例详情
     *
     * @param processInstanceIdt 审批实例id
     * @param accessToken        token
     * @return result
     */
    public static OapiProcessinstanceGetResponse.ProcessInstanceTopVo getProcessinstance(String processInstanceIdt, String accessToken) {

        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/get");
            OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
            request.setProcessInstanceId(processInstanceIdt);
            OapiProcessinstanceGetResponse response = client.execute(request, accessToken);
            if (response.isSuccess()) {
                return response.getProcessInstance();
            }
        } catch (ApiException e) {
            logger.error("获取审批实例详情: ", e);
        }

        return null;
    }


    /**
     * 获取企业下的自定义空间
     *
     * @param token token
     * @return 自定义空间space_id
     */
    public static String getCustomSpace(String token) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/get_custom_space");
        OapiCspaceGetCustomSpaceRequest req = new OapiCspaceGetCustomSpaceRequest();
        req.setDomain("zjp");
        req.setAgentId(Constant.AGENT_ID);
        req.setHttpMethod("GET");
        try {
            OapiCspaceGetCustomSpaceResponse rsp = client.execute(req, token);
            if (rsp.isSuccess()) {
                return rsp.getSpaceid();
            }
        } catch (ApiException e) {
            logger.error("获取企业下的自定义空间: ", e);
        }
        return "";
    }

    public static void preview2(List<String> fileIdList, String userId, String processInstanceId, String token) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/cspace/preview");
        OapiProcessinstanceCspacePreviewRequest req = new OapiProcessinstanceCspacePreviewRequest();
        OapiProcessinstanceCspacePreviewRequest.GrantCspaceRequest grantCspaceRequest = new OapiProcessinstanceCspacePreviewRequest.GrantCspaceRequest();
        grantCspaceRequest.setAgentid(Long.valueOf(Constant.AGENT_ID));
        grantCspaceRequest.setProcessInstanceId(processInstanceId);
        grantCspaceRequest.setFileId(fileIdList.get(0));
        grantCspaceRequest.setUserid(userId);
        grantCspaceRequest.setFileidList(fileIdList);
        req.setRequest(grantCspaceRequest);
        try {
            client.execute(req, token);
        } catch (ApiException e) {
            logger.error("授权预览审批附件: ", e);
        }
    }

    /**
     * 授权下载审批钉盘文件
     *
     * @param spaceId 审批实例id
     * @param fileId  要预览的文件id
     * @param userId  用户userid
     * @param token   token
     */
    public static void dentry(String fileId, String spaceId, String userId, String token) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/dentry/auth");
        OapiProcessDentryAuthRequest req = new OapiProcessDentryAuthRequest();
        OapiProcessDentryAuthRequest.GrantCspaceRequestV2 grantCspaceRequestV2 = new OapiProcessDentryAuthRequest.GrantCspaceRequestV2();
        grantCspaceRequestV2.setUserid(userId);
        OapiProcessDentryAuthRequest.FileInfo fileInfo = new OapiProcessDentryAuthRequest.FileInfo();
        fileInfo.setFileId(fileId);
        fileInfo.setSpaceId(Long.valueOf(spaceId));
        grantCspaceRequestV2.setFileInfos(Collections.singletonList(fileInfo));
        req.setRequest(grantCspaceRequestV2);
        try {
            client.execute(req, token);
        } catch (ApiException e) {
            logger.error("授权下载审批钉盘文件: ", e);
        }
    }

    /**
     * 授权用户访问企业的自定义空间
     *
     * @param isAdd   添加/下载
     * @param fileIds 授权文件ids
     * @param userId  用户userid
     * @param token   token
     */
    public static void grantCustomSpace(Boolean isAdd, String fileIds, String userId, String token) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/cspace/grant_custom_space");
        OapiCspaceGrantCustomSpaceRequest req = new OapiCspaceGrantCustomSpaceRequest();
        req.setAgentId(Constant.AGENT_ID);
        req.setDomain("zjp");
        if (Boolean.TRUE.equals(isAdd)) {
            req.setType("add");
            req.setPath("/");
        } else {
            req.setType("download");
            req.setFileids(fileIds);
        }
        req.setUserid(userId);
        req.setDuration(60L);
        req.setHttpMethod("GET");
        try {
            client.execute(req, token);
        } catch (ApiException e) {
            logger.error("授权用户访问企业的自定义空间: ", e);
        }
    }

    /**
     * 下载审批附件
     *
     * @param token token
     */
    public static String getFileUrl(String processInstanceId, String fileId, String token) {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/file/url/get");
        OapiProcessinstanceFileUrlGetRequest req = new OapiProcessinstanceFileUrlGetRequest();
        OapiProcessinstanceFileUrlGetRequest.GrantCspaceRequest request = new OapiProcessinstanceFileUrlGetRequest.GrantCspaceRequest();
        request.setProcessInstanceId(processInstanceId);
        request.setFileId(fileId);
        req.setRequest(request);

        try {
            OapiProcessinstanceFileUrlGetResponse rsp = client.execute(req, token);
            return rsp.getBody();
        } catch (ApiException e) {
            logger.error("下载审批附件 ", e);
        }
        return "";
    }

}
