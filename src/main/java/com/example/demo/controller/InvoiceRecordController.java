package com.example.demo.controller;


import com.example.demo.entity.InvoiceRecordParams;
import com.example.demo.entity.InvoiceRecordVO;
import com.example.demo.entity.PageInfo;
import com.example.demo.service.IInvoiceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@RestController
@RequestMapping("/invoiceRecord")
public class InvoiceRecordController {

    private final IInvoiceRecordService service;

    @Autowired
    public InvoiceRecordController(IInvoiceRecordService service) {
        this.service = service;
    }

    @PostMapping("getHistory")
    public PageInfo<InvoiceRecordVO> getHistory(@RequestBody InvoiceRecordParams params) {
        return service.getHistory(params);
    }

    @PostMapping("exportHistory")
    public void exportHistory(HttpServletResponse response, @RequestBody InvoiceRecordParams params) {
        service.exportHistory(response,params);
    }

}
