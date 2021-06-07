package com.example.demo.service;

import com.example.demo.entity.InvoiceRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.InvoiceRecordParams;
import com.example.demo.entity.InvoiceRecordVO;
import com.example.demo.entity.PageInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 支付申请记录
 *
 * @author zjp
 * @since 2020-11-30
 */
public interface IInvoiceRecordService extends IService<InvoiceRecord> {

    /**
     * 获取历史数据
     *
     * @param params 参数
     * @return list
     */
    PageInfo<InvoiceRecordVO> getHistory(InvoiceRecordParams params);

    /**
     * 导出
     *
     * @param response /
     * @param params   /
     */
    void exportHistory(HttpServletResponse response, InvoiceRecordParams params);
}
