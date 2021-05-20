package com.example.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CustomerConfig
 * 假如有多处(就是不止一个conntroller)需要引用该变量,此时需要定义一个java类来绑定application.properties中的常量;
 * 在需要使用的地方自动注入该类，get属性获取属性值
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Component
@Data
public class CustomerConfig {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Value("${app.version}")
    private String version;
}
