package com.example.demo.untils;

import java.io.File;

/**
 * DeleteUselessRepository
 * Maven本地仓库越用越大，删除无效版本文件
 * 从各个maven库下载下的jar越来越多，同时由于网络或其他问题导致jar没下载下来，徒留一堆无用非jar文件。另外还有许多自己本地打包的无效版本等等。
 *
 * @author ZhangJP
 * @date 2020/9/21
 */
public class DeleteUselessRepository {

    public static void main() {

        // 仓库根目录
        String root = "D:\\maven-repository";

        File file = new File(root);

        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    validate(f);
                }
            }
        }
    }


    private static boolean validate(File file) {
        boolean isHaveJar = false;
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            // 判断是否有*jar 是否是有文件夹
            for (File f : files) {
                if (f.getName().endsWith(".jar")) {
                    isHaveJar = true;
                }
                if (f.isDirectory()) {
                    boolean isNextHaveJar = validate(f);
                    if (isNextHaveJar) {
                        isHaveJar = true;
                    }
                }
            }
        }
        if (!isHaveJar) {
            delete(file);
        }
        return isHaveJar;
    }

    private static void delete(File file) {
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) {
                if (f.isDirectory()) {
                    delete(f);
                }
                if (f.delete()) {
                    System.out.println("已删除：" + f.getAbsolutePath());
                }

            }
        } else {
            if (file.delete()){
                System.out.println("已删除：" + file.getAbsolutePath());
            }
        }
    }


}


