package com.example.demo.untils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * ExcelUtils excel导入导出功能
 *
 * @author ZhangJP
 * @date 2021/3/29
 */
public class ExcelUtils {

    private ExcelUtils() {

    }

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * Excel 导出
     *
     * @param response  HttpServletResponse
     * @param header    表头
     * @param keys      map的key值
     * @param content   内容数据
     * @param title     表格名字
     * @param sheetName sheet名
     */
    public static void exportExcel(HttpServletResponse response, String[] header, String[] keys, List<Map<String, Object>> content, String title, String sheetName) {
        title = title + ".xlsx";

        try (SXSSFWorkbook workbook = new SXSSFWorkbook(content.size() + 1)) {

            SXSSFSheet sheet = workbook.createSheet(sheetName);
            SXSSFRow row = sheet.createRow(0);

            // 设置行高
            row.setHeight((short) 700);
            // 设置列宽
            for (int i = 0; i < header.length; i++) {
                sheet.setColumnWidth(i, 20 * 256);

                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(headerStyle(workbook));
            }

            for (int i = 0; i < content.size(); i++) {
                Map<String, Object> map = content.get(i);
                row = sheet.createRow(i + 1);
                row.setHeight((short) 500);

                for (int j = 0; j < keys.length; j++) {
                    SXSSFCell cell = row.createCell(j);
                    cell.setCellValue(map.get(keys[j]) == null ? "" : map.get(keys[j]).toString());
                    cell.setCellStyle(contentStyle(workbook));
                }
            }

            title = new String(title.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.reset();

            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Content-Disposition", "attachment; filename=" + title);

            workbook.write(response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException e) {
            logger.error("ExcelUtils - exportExcel {} [error]: ", LocalDateTime.now(), e);
        }

    }

    /**
     * Excel 导入
     *
     * @param file 文件
     * @param keys 数据顺序
     */
    public static List<Map<String, Object>> importExcel(MultipartFile file, String sheetName, String[] keys) throws IOException {
        Workbook workbook = null;

        String fileName = file.getOriginalFilename();
        assert fileName != null;
        if (fileName.endsWith("xls")) {
            POIFSFileSystem pois = new POIFSFileSystem(file.getInputStream());
            workbook = new HSSFWorkbook(pois);
        } else if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        }

        Sheet sheet;
        List<Map<String, Object>> result = new ArrayList<>();

        List<String> strings = Arrays.asList("id", "year", "month", "project_approval_id");

        if (workbook != null) {
            sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            if (sheet.getRow(1).getPhysicalNumberOfCells() != keys.length) {
                logger.error("导入的Excel和模板的列不匹配");
                return result;
            }

            for (int i = 0; i < rowCount - 1; i++) {
                Row row = sheet.getRow(i + 1);
                Map<String, Object> tmp = new HashMap<>(keys.length);
                for (int j = 0; j < keys.length; j++) {
                    Cell cell = row.getCell(j);
                    //设置单元格类型
                    if (strings.contains(keys[j])) {
                        tmp.put(keys[j], (int) cell.getNumericCellValue());
                    } else {
                        tmp.put(keys[j], getCellValue(cell));
                    }

                }
                result.add(tmp);
            }
        }
        return result;

    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case NUMERIC:
                //Cell 是数字类型（包括日期），分别处理后返回字符串
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    return new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    return cell.getNumericCellValue() + "";
                }
            case STRING:
                //Cell 是文本类型，直接返回其中的值
                return cell.getStringCellValue();
            case FORMULA:
                //Cell 是计算公式，返回公式的计算结果。如果想返回公式，需要使用：cell.getCellFormula()
                return cell.getStringCellValue();
            case BLANK:
                //Cell 中无数据，直接返回空字符串
                return "";
            case BOOLEAN:
                //需处理的文档中暂时没有如下类型，返回空字符串
            case ERROR:
            case _NONE:
            default:
                //如还有其他类型(?)，返回空字符串
                break;
        }
        return "";
    }

    public static void addExcelHeader(SXSSFWorkbook workbook, SXSSFSheet sheet, String[] excelHeader, List<String> column10Width, List<String> column30Width, List<String> column60Width) {
        // 设置标题
        SXSSFRow row = sheet.createRow(0);
        // 固定表头
        sheet.createFreezePane(0, 1);
        // 设置行高
        row.setHeight((short) 700);
        // 设置样式
        CellStyle cellStyle = headerStyle(workbook);
        // 设置列宽
        SXSSFCell cell;
        for (int i = 0; i < excelHeader.length; i++) {

            if (column60Width != null && column60Width.contains(excelHeader[i])) {
                sheet.setColumnWidth(i, 60 * 256);
            } else if (column30Width != null && column30Width.contains(excelHeader[i])) {
                sheet.setColumnWidth(i, 30 * 256);
            } else if (column10Width != null && column10Width.contains(excelHeader[i])) {
                sheet.setColumnWidth(i, 10 * 256);
            } else {
                sheet.setColumnWidth(i, 20 * 256);
            }

            cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(cellStyle);
        }
    }

    public static void addCellContent(SXSSFSheet sheet, List<Map<String, Object>> list, String[] excelHeader, String[] excelHeaderKey, List<String> cellTypeNumber, List<String> cellTypeBoolean, CellStyle contentCellStyle) {
        SXSSFRow sheetRow;
        for (int i = 0, length = list.size(); i < length; i++) {
            Map<String, Object> map = list.get(i);
            sheetRow = sheet.createRow(i + 1);
            sheetRow.setHeight((short) 500);

            setCellValue(contentCellStyle, sheetRow, map, excelHeader, excelHeaderKey, cellTypeNumber, cellTypeBoolean);
        }
    }

    public static void setCellValue(CellStyle contentCellStyle, SXSSFRow row, Map<String, Object> map, String[] excelHeader, String[] excelHeaderKey, List<String> cellTypeNumber, List<String> cellTypeBoolean) {
        SXSSFCell cell;
        for (int j = 0, length = excelHeader.length; j < length; j++) {

            cell = row.createCell(j);
            cell.setCellStyle(contentCellStyle);

            if (map.get(excelHeaderKey[j]) == null || "null".equals(map.get(excelHeaderKey[j]))) {
                cell.setCellValue("");
                continue;
            }

            if (cellTypeNumber != null && cellTypeNumber.contains(excelHeader[j])) {
                cell.setCellValue(Double.valueOf(String.valueOf(map.get(excelHeaderKey[j]))));
            } else if (cellTypeBoolean != null && cellTypeBoolean.contains(excelHeader[j])) {
                // "true".equals(String.valueOf(map.get(excelHeaderKey[j])))
                cell.setCellValue(Objects.equals(true, map.get(excelHeaderKey[j])) ? "是" : "否");
            } else {
                cell.setCellValue(String.valueOf(map.get(excelHeaderKey[j])));
            }
        }
    }

    public static void excelOutput(HttpServletResponse response, SXSSFWorkbook workbook, String title) throws IOException {
        title = new String(title.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.reset();

        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Disposition", "attachment; filename=" + title);

        workbook.write(response.getOutputStream());
        response.getOutputStream().close();
    }

    /**
     * 表头样式
     */
    public static CellStyle headerStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        CellStyle cellStyle = commonStyle(workbook);
        cellStyle.setFont(font);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());

        return cellStyle;
    }

    /**
     * 内容样式
     */
    public static CellStyle contentStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        CellStyle cellStyle = commonStyle(workbook);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 公共样式
     */
    private static CellStyle commonStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        // 自动换行
        style.setWrapText(true);
        return style;
    }
}

