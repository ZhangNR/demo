package com.example.demo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.example.demo.entity.TestBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    @Test
    public void contextLoads() {

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        System.out.println(list);
        System.out.println(list.toString());
        System.out.println(JSON.toJSONString(list));

        /*String input = "John=first,Adam=second";
        Map<String, String> result = Splitter.on(",")
                .trimResults()  // 去掉非空字符串两端的空格
                .omitEmptyStrings()  // 去掉空字符串
                .withKeyValueSeparator("=") // map中的key-value分隔符
                .split(input);
        System.out.println(result.get("John"));

        assertEquals("first", result.get("John"));
        assertEquals("second", result.get("Adam"));*/

    }

    @Test
    public void contextLoads2() {
        File file = new File("Hello.World.java");
        String fileName = file.getName();
        // 文件前缀
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 自定义文件名
        String publishFileName = 1 + "-" + prefix + "-" + System.currentTimeMillis() + prefix;
        System.out.println("prefix: " + prefix);
        System.out.println("suffix: " + suffix);
        System.out.println("fileName: " + fileName);
        System.out.println("自定义文件名: " + publishFileName);

    }

    @Test
    public void merTest() {
        //测试使用的数据
        TestBean bean1 = new TestBean();
        bean1.setName("张三");
        bean1.setAge(22);
        bean1.setPass(true);
        bean1.setScore(66.30);
        bean1.setExamDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));

        TestBean bean2 = new TestBean();
        bean2.setName("李四");
        bean2.setAge(28);
        bean2.setPass(false);
        bean2.setScore(38.50);
        bean2.setExamDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        //传入list集合数组
        List<TestBean> rows = CollUtil.newArrayList(bean1, bean2);

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getBigWriter();

        String[] names = {"name", "age", "score", "isPass", "examDate"};
        String[] names2 = {"姓名", "年龄", "分数", "是否通过", "考试时间"};

        for (int i = 0; i < names.length; i++) {
            writer.addHeaderAlias(names[i], names2[i]);
        }
        //todo 设置自动换行(时间)
        writer.setColumnWidth(4, 25);
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();

    }

}
