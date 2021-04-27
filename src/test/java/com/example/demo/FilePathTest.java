package com.example.demo;

import org.junit.Test;

import java.io.File;

/**
 * FilePathTest
 * 文件路径
 * getPath（获取路径）
 * getAbsolutePath（获取绝对路径）
 * getCanonicalPath（获取规范路径）
 *
 * @author ZhangJP
 * @date 2021/3/19
 */
public class FilePathTest {

    /*
     .  用来代表当前的目录
     .. 用来代表父目录
     /  为Linux/Mac等操作系统的路径分隔符
     \  为 Windows 路径分隔符
     :  为 Windows磁盘分割符,比如C:
     */

    /**
     * 相对路径    指的是某个文件相对于当前目录的路径
     * 绝对路径    指的是从文件系统的根目录到当前文件的路径
     * 两个目录 C:\temp C:\temp1
     * C:\temp\test.txt; C:\temp\test.txt; C:\temp\TEST.TXT; C:\temp\.\test.txt; C:\temp1\..\temp\test.txt 是指向同一个文件的绝对路径，且都是合法的
     * 规范路径    是从文件系统的根目录到当前文件的唯一的路径(规范路径不像绝对路径那样有多个不同的值指向同一文件;规范路径是绝对路径，但是绝对路径不一定是规范路径;规范路径中移除了.和..等特殊字符)
     * 一个相对路径为.././Java.txt的文件，
     * 它的绝对路径是 /Users/androidyue/Documents/projects/PathSamples/.././Java.txt
     * 它的规范路径是 /Users/androidyue/Documents/projects/Java.txt
     */

    @Test
    public static void main(String args[]) {
        System.out.println("----------------------------------");
        File child = new File(".././Java.txt");
        displayPath(child);
        File parent = child.getParentFile();
        System.out.println("父路径: ");
        displayPath(parent);

        System.out.println("----------------------------------");
        File anotherFile = new File("a.txt");
        displayPath(anotherFile);

        System.out.println("----------------------------------");
        File anotherAbsFile = new File("/tmp/a.txt");
        displayPath(anotherAbsFile);
    }


    private static void displayPath(File testFile) {
        System.out.println("路径 : " + testFile.getPath());
        System.out.println("绝对路径 : " + testFile.getAbsolutePath());
        try {
            System.out.println("规范路径 : " + testFile.getCanonicalPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
