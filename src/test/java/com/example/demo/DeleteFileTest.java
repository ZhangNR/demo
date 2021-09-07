package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

/**
 * DeleteFileTest
 *
 * @author ZhangJP
 * @date 2021/9/2
 */
@SpringBootTest
@Slf4j
public class DeleteFileTest {

    @Test
    public void delete() {
        File srcFolder = new File("D:\\BMDownload\\盐城市_卫图17\\Tiles_BIGEMAP\\17");
        //在windows中斜杠的方向是向右斜的\\，在Linux  中斜杠的方向是向左斜的//
        deleteFolder(srcFolder);
    }

    /**
     * 递归遍历删除目录
     *
     * @param srcFolder
     */
    private void deleteFolder(File srcFolder) {

        File[] fileArray = srcFolder.listFiles();

        if (fileArray == null) {
            return;
        }

        for (File file : fileArray) {
            if (file.isDirectory()) { // isDirectory()方法是判断文件是文件夹还是一个文件
                deleteFolder(file);
            } else {
                file.delete();
            }
        }
        System.out.println(srcFolder.getName() + ":" + srcFolder.delete());

    }

    @Test
    public void delete2() {
        String path = "C:\\Users\\ZhangJP\\node_modules";
        File srcFolder = new File(path);
        //在windows中斜杠的方向是向右斜的\\，在Linux  中斜杠的方向是向左斜的//
        File[] fileArray = srcFolder.listFiles();

        if (fileArray == null) {
            return;
        }
        Runtime runtime = Runtime.getRuntime();
        String[] args;

        try {
            for (File file : fileArray) {
                System.out.println(file.getName());
                args = new String[]{"cmd.exe", "/c", String.format("rd %s /q /s", path + "\\" + file.getName())};
                Thread.sleep(100);
                runtime.exec(args);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}
