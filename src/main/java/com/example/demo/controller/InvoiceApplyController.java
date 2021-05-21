package com.example.demo.controller;


import com.example.demo.service.IInvoiceApplyService;
import com.example.demo.untils.DingServiceApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@RestController
@RequestMapping("//invoice-apply")
@Api(tags = "开票申请模块")
public class InvoiceApplyController {

    @Autowired
    IInvoiceApplyService invoiceApplyService;

    @ApiOperation(value = "授权用户访问企业的自定义空间")
    @GetMapping("grantCustomSpace")
    public void grantCustomSpace(@RequestParam("fileIds") String fileIds, @RequestParam("userId") String userId, @RequestParam("type") String type) {
        invoiceApplyService.grantCustomSpace(fileIds, userId, type);
    }

    @ApiOperation(value = "授权预览审批附件", notes = "每次预览前都需要授权")
    @GetMapping("grantPreview")
    public void grantPreview(@RequestParam("fileId") String fileId, @RequestParam("userId") String userId, @RequestParam("processInstanceId") String processInstanceId) {
        invoiceApplyService.grantPreview(fileId, userId, processInstanceId);
    }

    @ApiOperation(value = "批量授权预览审批附件", notes = "每次预览前都需要授权")
    @GetMapping("grantPreview2")
    public void grantPreview2(@RequestBody List<String> fileIdList, @RequestParam("userId") String userId, @RequestParam("processInstanceId") String processInstanceId) {
        DingServiceApi.preview2(fileIdList, userId, processInstanceId, DingServiceApi.getToken());
    }

    @ApiOperation(value = "授权下载审批钉盘文件")
    @GetMapping("dentry")
    public void dentry(@RequestParam("fileId") String fileId, @RequestParam("userId") String userId, @RequestParam("spaceId") String spaceId) {
        invoiceApplyService.dentry(fileId, spaceId, userId);
    }
}
