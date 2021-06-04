package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.InvoiceApply;
import com.example.demo.entity.InvoiceApplyParams;
import com.example.demo.entity.InvoiceApplyVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
public interface InvoiceApplyMapper extends BaseMapper<InvoiceApply> {

    /**
     * 开票记录
     *
     * @param params 查询参数
     * @return list
     */
//    @Select("SELECT o.num,o.name,i.* FROM invoice_apply i INNER JOIN order_contract o ON o.id = i.order_contract_id WHERE i.state = '同意'")
    List<Map<String, Object>> listByParams(InvoiceApplyParams params);
//    List<InvoiceApplyVO> listByParams(InvoiceApplyParams params);

}
