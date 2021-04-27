package com.example.demo.service.impl;

import com.example.demo.entity.InvoiceApply;
import com.example.demo.mapper.InvoiceApplyMapper;
import com.example.demo.service.IInvoiceApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.untils.DingServiceApi;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Service
public class InvoiceApplyServiceImpl extends ServiceImpl<InvoiceApplyMapper, InvoiceApply> implements IInvoiceApplyService {

    @Override
    public void grantCustomSpace(String fileIds, String userId, String type) {
        if ("add".equals(type)) {
            DingServiceApi.grantCustomSpace(true, null, userId, DingServiceApi.getToken());
        } else {
            DingServiceApi.grantCustomSpace(false, fileIds, userId, DingServiceApi.getToken());
        }
    }

    @Override
    public void grantPreview(String fileId, String userId, String processInstanceId) {
        DingServiceApi.preview(fileId, userId, processInstanceId, DingServiceApi.getToken());
    }

    @Override
    public void dentry(String fileId, String spaceId, String userId) {
        DingServiceApi.dentry(fileId, spaceId, userId, DingServiceApi.getToken());
    }
}
