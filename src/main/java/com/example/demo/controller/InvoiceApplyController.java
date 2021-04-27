package com.example.demo.controller;


import com.example.demo.service.IInvoiceApplyService;
import com.example.demo.untils.DingServiceApi;
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
@RequestMapping("//invoice-apply")
public class InvoiceApplyController {

    private final IInvoiceApplyService service;

    @Autowired
    public InvoiceApplyController(IInvoiceApplyService service) {
        this.service = service;
    }

    @RequestMapping("grantCustomSpace")
    public void grantCustomSpace(@RequestParam("fileIds") String fileIds, @RequestParam("userId") String userId, @RequestParam("type") String type) {
        service.grantCustomSpace(fileIds, userId, type);
    }

    @RequestMapping("grantPreview")
    public void grantPreview(@RequestParam("fileId") String fileId, @RequestParam("userId") String userId, @RequestParam("processInstanceId") String processInstanceId) {
        service.grantPreview(fileId, userId, processInstanceId);
    }

    @RequestMapping("grantPreview2")
    public void grantPreview2(@RequestBody List<String> fileIdList, @RequestParam("userId") String userId, @RequestParam("processInstanceId") String processInstanceId) {
        DingServiceApi.preview2(fileIdList, userId, processInstanceId, DingServiceApi.getToken());
    }

    @RequestMapping("dentry")
    public void dentry(@RequestParam("fileId") String fileId, @RequestParam("userId") String userId, @RequestParam("spaceId") String spaceId) {
        service.dentry(fileId, spaceId, userId);
    }
}
