package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.PaymentDetail;
import com.example.demo.entity.PaymentSplitParams;
import com.example.demo.mapper.PaymentDetailMapper;
import com.example.demo.service.IPaymentDetailService;
import com.example.demo.untils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.demo.untils.ExcelUtils.*;

/**
 * 到款详情
 *
 * @author zjp
 * @since 2020-11-30
 */
@Service
@Slf4j
public class PaymentDetailServiceImpl extends ServiceImpl<PaymentDetailMapper, PaymentDetail> implements IPaymentDetailService {

    @Autowired
    private PaymentDetailMapper mapper;

    /**
     * 导出开票记录
     *
     * @param response /
     * @param params   /
     */
    @Override
    public void export(HttpServletResponse response, PaymentSplitParams params) {
        long start = System.currentTimeMillis();
        String[] excelHeader = {
                "系统编号", "归属部门", "归属项目组", "开票名称", "税率",
                "订单合同ID", "合同编号", "合同名称", "合同总金额", "开票申请总金额", "开票时间", "发票编号", "累计到款金额", "是否全部到款", "开票发起时间",
                "到款时间", "到款金额", "备注", "自有金额", "外部合作金额", "拆分说明", "附件", "创建时间"
        };

        String[] excelHeaderKey = {
                "id", "pt_dept_name", "pt_sub_dept_name", "invoicing_name", "tax_rate",
                "order_contract_id", "num", "name", "sum_money", "sum_amount_apply", "invoicing_time", "invoice_no", "sum_payment_amount", "is_all", "invoice_create_time",
                "payment_time", "payment_amount", "note", "own_amount", "external_amount", "split_note", "annex", "create_time"
        };

        List<Map<String, Object>> list = mapper.listByParams(params);
        excelNoMerge(response, excelHeader, excelHeaderKey, list);

        long end = System.currentTimeMillis();
        log.warn("运行时间：{}", end - start);
    }


    private void excelNoMerge(HttpServletResponse response, String[] excelHeader, String[] excelHeaderKey, List<Map<String, Object>> list) {
        List<String> column10Width = Arrays.asList("系统编号", "税率", "订单合同ID", "是否全部到款");
        List<String> column60Width = Collections.singletonList("合同名称");
        List<String> column30Width = Arrays.asList("备注", "拆分说明", "附件");

        // 设置cell type 为 number 类型的列名
        List<String> cellTypeNumber = Arrays.asList("系统编号", "税率", "订单合同ID", "合同总金额", "开票申请总金额", "发票编号", "累计到款金额", "到款金额", "自有金额", "外部合作金额");
        List<String> cellTypeBoolean = Collections.singletonList("是否全部到款");


        try (SXSSFWorkbook workbook = new SXSSFWorkbook(list.size() + 1)) {

            SXSSFSheet sheet = workbook.createSheet("到款拆分");
            // excel header
            addExcelHeader(workbook, sheet, excelHeader, column10Width, column30Width, column60Width);

            // content style
            CellStyle contentCellStyle = ExcelUtils.contentStyle(workbook);

            // cell context
            addCellContent(sheet, list, excelHeader, excelHeaderKey, cellTypeNumber, cellTypeBoolean, contentCellStyle);

            excelOutput(response, workbook, "到款拆分" + LocalDate.now() + ".xlsx");
        } catch (IOException e) {
            log.error("ExcelUtils - exportExcel {} [error]: ", LocalDateTime.now(), e);
        }
    }


}
