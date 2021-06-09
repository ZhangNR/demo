package com.example.demo.mapper;

import com.example.demo.entity.PaymentDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.PaymentSplitParams;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 到款详情 Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
public interface PaymentDetailMapper extends BaseMapper<PaymentDetail> {

    /**
     * 到款拆分记录
     *
     * @param params /
     * @return list map
     */
    List<Map<String, Object>> listByParams(PaymentSplitParams params);
}
