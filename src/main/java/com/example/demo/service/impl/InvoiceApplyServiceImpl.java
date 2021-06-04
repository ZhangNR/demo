package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.InvoiceApply;
import com.example.demo.entity.InvoiceApplyParams;
import com.example.demo.entity.InvoiceApplyVO;
import com.example.demo.mapper.InvoiceApplyMapper;
import com.example.demo.service.IInvoiceApplyService;
import com.example.demo.untils.DingServiceApi;
import com.example.demo.untils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private InvoiceApplyMapper invoiceApplyMapper;

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

        List<String> column10Width = Arrays.asList("系统编号", "申请类型", "税率", "订单合同ID", "创建人", "项目ID");
        List<String> column60Width = Arrays.asList("合同名称", "公司名称");

        List<String> cellTypeNumber = Arrays.asList("系统编号", "税率", "订单合同ID", "合同总金额", "申请总金额", "发票编号", "累计到款金额", "项目ID", "项目总金额", "本次申请金额");


        List<Map<String, Object>> list = invoiceApplyMapper.listByParams(params);

        String title = "开票记录" + LocalDate.now() + ".xlsx";

        try (SXSSFWorkbook workbook = new SXSSFWorkbook(list.size() + 1)) {

            SXSSFSheet sheet = workbook.createSheet("开票记录");
            SXSSFRow row = sheet.createRow(0);

            // 设置行高
            row.setHeight((short) 700);
            // 设置列宽
            for (int i = 0; i < excelHeader.length; i++) {
                if (column10Width.contains(excelHeader[i])) {
                    sheet.setColumnWidth(i, 10 * 256);
                } else if (column60Width.contains(excelHeader[i])) {
                    sheet.setColumnWidth(i, 60 * 256);
                } else {
                    sheet.setColumnWidth(i, 20 * 256);
                }

                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(excelHeader[i]);
                cell.setCellStyle(ExcelUtils.headerStyle(workbook));
            }

            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                row = sheet.createRow(i + 1);
                row.setHeight((short) 500);

                for (int j = 0; j < excelHeader.length; j++) {
                    if (cellTypeNumber.contains(excelHeader[j])) {
                        SXSSFCell cell = row.createCell(j, CellType.NUMERIC);
                        cell.setCellStyle(ExcelUtils.contentStyle(workbook));
                        if (map.get(excelHeaderKey[j]) == null) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(Double.valueOf(String.valueOf(map.get(excelHeaderKey[j]))));
                        }
                    } else if ("全部到款".equals(excelHeader[j])) {
                        SXSSFCell cell = row.createCell(j, CellType.BOOLEAN);
                        cell.setCellStyle(ExcelUtils.contentStyle(workbook));
                        if (map.get(excelHeaderKey[j]) == null) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue("true".equals(String.valueOf(map.get(excelHeaderKey[j]))) ? "是" : "否");
                        }
                    } else {
                        SXSSFCell cell = row.createCell(j, CellType.STRING);
                        cell.setCellStyle(ExcelUtils.contentStyle(workbook));
                        if (map.get(excelHeaderKey[j]) == null) {
                            cell.setCellValue("");
                        } else {
                            cell.setCellValue(String.valueOf(map.get(excelHeaderKey[j])));
                        }
                    }
                }
            }
            //合并单元格


            title = new String(title.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.reset();

            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-Disposition", "attachment; filename=" + title);

            workbook.write(response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException e) {
            log.error("ExcelUtils - exportExcel {} [error]: ", LocalDateTime.now(), e);
        }


    }


}
