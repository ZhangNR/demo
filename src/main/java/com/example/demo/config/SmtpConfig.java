package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * SmtpConfig
 * 简单的JavaBean持有所有的配置，在需要读取的地方，使用#{smtpConfig.host}注入 或例 CustomerConfig
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
//@Component
public class SmtpConfig {

    @Value("${spring.mail.protocol:smtp}")
    private String protocol;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port:25}")
    private int port;

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
