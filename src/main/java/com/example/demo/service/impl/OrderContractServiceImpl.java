package com.example.demo.service.impl;

import com.example.demo.entity.OrderContract;
import com.example.demo.mapper.OrderContractMapper;
import com.example.demo.service.IOrderContractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Service
public class OrderContractServiceImpl extends ServiceImpl<OrderContractMapper, OrderContract> implements IOrderContractService {

}
