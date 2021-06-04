package com.example.demo.service;

import com.example.demo.entity.InvoiceApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.InvoiceApplyParams;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
public interface IInvoiceApplyService extends IService<InvoiceApply> {

    /**
     * 授权用户访问企业的自定义空间
     *
     * @param fileId 文件id
     * @param userId 用户id
     * @param type   授权类型 add/download
     */
    void grantCustomSpace(String fileId, String userId, String type);

    /**
     * 授权预览审批附件
     *
     * @param fileId            文件id
     * @param userId            用户id
     * @param processInstanceId 审批实例
     */
    void grantPreview(String fileId, String userId, String processInstanceId);

    /**
     * 授权下载审批钉盘文件
     *
     * @param fileId  文件id
     * @param spaceId 钉盘空间spaceId
     * @param userId  用户id
     */
    void dentry(String fileId, String spaceId, String userId);

    /**
     * 导出invoiceApply 申请记录
     *
     * @param response 响应
     * @param params   参数
     */
    void exportApply(HttpServletResponse response, InvoiceApplyParams params);
}
