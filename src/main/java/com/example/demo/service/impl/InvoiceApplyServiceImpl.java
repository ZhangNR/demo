package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.InvoiceApply;
import com.example.demo.entity.InvoiceApplyParams;
import com.example.demo.mapper.InvoiceApplyMapper;
import com.example.demo.service.IInvoiceApplyService;
import com.example.demo.untils.DingServiceApi;
import com.example.demo.untils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
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
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjp
 * @since 2020-11-30
 */
@Service
@Slf4j
public class InvoiceApplyServiceImpl extends ServiceImpl<InvoiceApplyMapper, InvoiceApply> implements IInvoiceApplyService {

    private final InvoiceApplyMapper invoiceApplyMapper;

    @Autowired
    public InvoiceApplyServiceImpl(InvoiceApplyMapper invoiceApplyMapper) {
        this.invoiceApplyMapper = invoiceApplyMapper;
    }

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

    /**
     * 导出invoiceApply 申请记录
     *
     * @param response 响应
     * @param params   参数
     */
    @Override
    public void exportApply(HttpServletResponse response, InvoiceApplyParams params) {
        String[] excelHeader = {
                "系统编号", "归属部门", "归属项目组", "开票名称", "申请类型", "税率",
                "订单合同ID", "合同编号", "合同名称", "合同总金额", "申请总金额", "是否简易项目", "备注",
                "公司名称", "税号", "开户行", "银行账号", "地址", "电话",
                "开票时间", "发票编号", "累计到款金额", "全部到款", "创建人", "创建时间",
                "项目ID", "项目编号", "项目总金额", "本次申请金额"
        };

        String[] excelHeaderKey = {
                "id", "pt_dept_name", "pt_sub_dept_name", "invoicing_name", "apply_type", "tax_rate",
                "order_contract_id", "num", "name", "sum_amount_order", "sum_amount_apply", "collection_project", "note",
                "customer_name", "tax_number", "bank_name", "bank_account", "address", "phone",
                "invoicing_time", "invoice_no", "sum_payment_amount", "is_all", "creator_name", "create_time",
                "order_contract_project_id", "project_num", "sum_project_money", "apply_amount"
        };

        List<Map<String, Object>> list = invoiceApplyMapper.listByParams(params);


        // excelNoMerge(response, excelHeader, excelHeaderKey, list);
        excelMerge(response, excelHeader, excelHeaderKey, list);

    }


    private void excelMerge(HttpServletResponse response, String[] excelHeader, String[] excelHeaderKey, List<Map<String, Object>> list) {
        List<String> column10Width = Arrays.asList("系统编号", "申请类型", "税率", "订单合同ID", "创建人", "项目ID");
        List<String> column60Width = Arrays.asList("合同名称", "公司名称");

        List<String> cellTypeNumber = Arrays.asList("系统编号", "税率", "订单合同ID", "合同总金额", "申请总金额", "发票编号", "累计到款金额", "项目ID", "项目总金额", "本次申请金额");
        List<String> cellTypeBoolean = Collections.singletonList("全部到款");


        try (SXSSFWorkbook workbook = new SXSSFWorkbook(list.size() + 1)) {

            SXSSFSheet sheet = workbook.createSheet("开票记录");
            addExcelHeader(workbook, sheet, excelHeader, column10Width, null, column60Width);

            int m = 1;
            int startRow = 1;
            CellStyle contentCellStyle = ExcelUtils.contentStyle(workbook);
            SXSSFRow sheetRow;
            for (int i = 0, length = list.size(); i < length; i++) {
                Map<String, Object> map = list.get(i);
                sheetRow = sheet.createRow(i + 1);
                sheetRow.setHeight((short) 500);

                setCellValue(contentCellStyle, sheetRow, map, excelHeader, excelHeaderKey, cellTypeNumber, cellTypeBoolean);

                int currentId = (int) map.get("id");
                int nextId = i == length - 1 ? 0 : (int) list.get(i + 1).get("id");

                if (currentId == nextId) {
                    m++;
                } else {
                    if (m != 1) {
                        // 合并单元格
                        for (int n = 0; n < 25; n++) {
                            sheet.addMergedRegion(new CellRangeAddress(startRow, startRow + m - 1, n, n));
                        }
                    }

                    startRow = startRow + m;
                    m = 1;
                }

            }

            // excel output
            excelOutput(response, workbook, "开票记录" + LocalDate.now() + ".xlsx");

        } catch (IOException e) {
            log.error("ExcelUtils - exportExcel {} [error]: ", LocalDateTime.now(), e);
        }
    }

    private void excelNoMerge(HttpServletResponse response, String[] excelHeader, String[] excelHeaderKey, List<Map<String, Object>> list) {
        List<String> column10Width = Arrays.asList("系统编号", "申请类型", "税率", "订单合同ID", "创建人", "项目ID");
        List<String> column60Width = Arrays.asList("合同名称", "公司名称");
        // 设置cell type 为 number 类型的列名
        List<String> cellTypeNumber = Arrays.asList("系统编号", "税率", "订单合同ID", "合同总金额", "申请总金额", "发票编号", "累计到款金额", "项目ID", "项目总金额", "本次申请金额");

        try (SXSSFWorkbook workbook = new SXSSFWorkbook(list.size() + 1)) {

            SXSSFSheet sheet = workbook.createSheet("开票记录");
            // excel header
            addExcelHeader(workbook, sheet, excelHeader, column10Width, null, column60Width);

            // content style
            CellStyle contentCellStyle = ExcelUtils.contentStyle(workbook);
            // cell context
            addCellContent(sheet, list, excelHeader, excelHeaderKey, cellTypeNumber, null, contentCellStyle);

            // excel output
            excelOutput(response, workbook, "开票记录" + LocalDate.now() + ".xlsx");
        } catch (IOException e) {
            log.error("ExcelUtils - exportExcel {} [error]: ", LocalDateTime.now(), e);
        }
    }

}
