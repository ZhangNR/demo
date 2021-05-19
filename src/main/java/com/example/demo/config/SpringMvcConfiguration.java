package com.example.demo.config;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * SpringMvcConfiguration
 *
 * @author ZhangJP
 * @date 2021/5/10
 */
public class SpringMvcConfiguration implements WebMvcConfigurer {
    /**
     * 将Spring IoC中的ObjectMapper注入到MappingJackson2HttpMessageConverter中去。
     * 或者你使用Debug调试出系统默认的MappingJackson2HttpMessageConverter的位置，比如我的索引为7
     *
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决 String 统一封装RestBody的问题
        HttpMessageConverter<?> httpMessageConverter = converters.get(7);
        if (!(httpMessageConverter instanceof MappingJackson2HttpMessageConverter)) {
            // 确保正确，如果有改动就重新debug
            throw new RuntimeException("MappingJackson2HttpMessageConverter is not here");
        }
        converters.add(0, httpMessageConverter);

    }
}
