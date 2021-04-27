package com.example.demo.untils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Import
 * excel 导入功能
 * a、通过流获取指定文件中的exl工作薄
 * b、遍历工作薄中的工作表
 * c、遍历工作表中的行
 * d、遍历工作表中的行中的列
 * e、判断指定单元表格中值得类型并获取值，进而进行其他操作
 *
 * @author ZhangJP
 * @date 2021/3/29
 */
public class Import {

    public static void get(File file) {

        // 获取工作簿
        HSSFWorkbook hssfWorkBook = getHSSFWorkBook(file);


    }


    /**
     * 根据文本获取exl工作簿
     *
     * @param file 文件
     * @return hssfworkBook 工作簿
     */
    private static HSSFWorkbook getHSSFWorkBook(File file) {
        // poi文件流
        POIFSFileSystem fs;
        // 工作簿
        HSSFWorkbook hssfworkBook;
        try {
            // 得到文件输入流
            fs = new POIFSFileSystem(new FileInputStream(file));
            hssfworkBook = new HSSFWorkbook(fs);
            return hssfworkBook;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
