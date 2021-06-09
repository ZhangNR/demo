package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.InvoiceRecord;
import com.example.demo.entity.InvoiceRecordParams;
import com.example.demo.entity.InvoiceRecordVO;
import com.example.demo.entity.PageInfo;
import com.example.demo.mapper.InvoiceRecordMapper;
import com.example.demo.service.IInvoiceRecordService;
import com.example.demo.untils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.demo.untils.ExcelUtils.addCellContent;
import static com.example.demo.untils.ExcelUtils.addExcelHeader;
import static com.example.demo.untils.ExcelUtils.excelOutput;

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
public class InvoiceRecordServiceImpl extends ServiceImpl<InvoiceRecordMapper, InvoiceRecord> implements IInvoiceRecordService {

    private final InvoiceRecordMapper mapper;

    @Autowired
    public InvoiceRecordServiceImpl(InvoiceRecordMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * 获取历史数据
     *
     * @param params 参数
     * @return list
     */
    @Override
    public PageInfo<InvoiceRecordVO> getHistory(InvoiceRecordParams params) {

        Page<InvoiceRecordVO> page = new Page<>(params.getPageNumber(), params.getPageSize());

        Page<InvoiceRecordVO> list = mapper.selectHistoryList(page, params);

        return new PageInfo<>(list.getTotal(), list.getCurrent(), list.getPages(), list.getRecords());
    }

    /**
     * 导出
     *
     * @param response /
     * @param params   /
     */
    @Override
    public void exportHistory(HttpServletResponse response, InvoiceRecordParams params) {

        String[] excelHeader = {
                "系统编号", "部门", "项目组", "发起人ID",
                "市场部门合作支付", "合作合同ID", "合同编号", "合同名称", "合同总金额", "已支付金额", "可付金额", "收入合同ID", "合同编号", "合同名称", "合同总金额", "到款金额", "应付合作费用",
                "支付方式", "支付类型", "支付类别", "申请支付金额",
                "银行账户名", "银行账户", "开户银行", "开户支行", "申请理由", "备注", "创建时间"
        };

        String[] excelHeaderKey = {
                "id", "dept_name", "sub_dept_name", "user_id",
                "cooperative_payment", "contract_id", "num", "name", "sum_money", "amount_paid", "payable_amount", "income_id", "income_num", "income_name", "income_sum_money", "income_paid", "income_payable_expenses",
                "type", "category", "sub_category", "amount",
                "customer_name", "bank_account", "bank_name", "bank_branch_name", "apply_reason", "note", "create_time"
        };

        List<Map<String, Object>> list = mapper.listByParams(params);

        excelNoMerge(response, excelHeader, excelHeaderKey, list);

    }

    private void excelNoMerge(HttpServletResponse response, String[] excelHeader, String[] excelHeaderKey, List<Map<String, Object>> list) {
        List<String> column10Width = Arrays.asList("系统编号", "市场部门合作支付", "合作合同ID", "收入合同ID", "支付方式", "支付类型", "支付类别");
        List<String> column60Width = Arrays.asList("申请理由", "合同名称");
        List<String> column30Width = Arrays.asList("银行账户名", "银行账户", "开户银行", "开户支行");

        // 设置cell type 为 number 类型的列名
        List<String> cellTypeNumber = Arrays.asList("系统编号", "合作合同ID", "合同总金额", "已支付金额", "可付金额", "收入合同ID", "到款金额", "应付合作费用", "申请支付金额");

        try (SXSSFWorkbook workbook = new SXSSFWorkbook(list.size() + 1)) {

            SXSSFSheet sheet = workbook.createSheet("支付申请");
            // excel header
            addExcelHeader(workbook, sheet, excelHeader, column10Width, column30Width, column60Width);

            // content style
            CellStyle contentCellStyle = ExcelUtils.contentStyle(workbook);

            // cell content
            addCellContent(sheet, list, excelHeader, excelHeaderKey, cellTypeNumber, null, contentCellStyle);

            // excel output
            excelOutput(response, workbook, "支付申请记录" + LocalDate.now() + ".xlsx");
        } catch (IOException e) {
            log.error("ExcelUtils - exportExcel {} [error]: ", LocalDateTime.now(), e);
        }
    }


}
