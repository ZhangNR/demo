package com.example.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;
import java.util.Collections;

/**
 * ApiFilterRegistrationBean
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Order(20)
//@Component
@Slf4j
public class ApiFilterRegistrationBean extends FilterRegistrationBean<Filter> {
    @PostConstruct
    public void init() {
        setFilter(new ApiFilter());
        setUrlPatterns(Collections.singletonList("/view/*"));
    }

    class ApiFilter implements Filter {

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            log.info("ApiFilter doFilter...");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
