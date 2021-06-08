package com.example.demo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.InvoiceRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.InvoiceRecordParams;
import com.example.demo.entity.InvoiceRecordVO;

import javax.websocket.server.PathParam;
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
public interface InvoiceRecordMapper extends BaseMapper<InvoiceRecord> {

    /**
     * 查询支付申请历史记录
     *
     * @param page   页
     * @param params 其他参数
     * @return list
     */
    Page<InvoiceRecordVO> selectHistoryList(Page<InvoiceRecordVO> page, @PathParam("params") InvoiceRecordParams params);

    /**
     * 查询支付申请历史记录
     * @param params /
     * @return list
     */
    List<Map<String, Object>> listByParams(InvoiceRecordParams params);
}
