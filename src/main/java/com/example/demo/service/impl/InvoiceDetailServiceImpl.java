package com.example.demo.service.impl;

import com.example.demo.entity.InvoiceDetail;
import com.example.demo.mapper.InvoiceDetailMapper;
import com.example.demo.service.IInvoiceDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票详情 服务实现类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Service
public class InvoiceDetailServiceImpl extends ServiceImpl<InvoiceDetailMapper, InvoiceDetail> implements IInvoiceDetailService {

}
