package com.example.demo.service.impl;

import com.example.demo.entity.PaymentDetail;
import com.example.demo.mapper.PaymentDetailMapper;
import com.example.demo.service.IPaymentDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 到款详情 服务实现类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Service
public class PaymentDetailServiceImpl extends ServiceImpl<PaymentDetailMapper, PaymentDetail> implements IPaymentDetailService {

}
