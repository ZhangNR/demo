package com.example.demo.filter;

import com.example.demo.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * AuthFilterRegistrationBean
 * FilterRegistrationBean本身不是Filter，它实际上是Filter的工厂。Spring Boot会调用getFilter()，把返回的Filter注册到Servlet容器中。
 * 因为我们可以在FilterRegistrationBean中注入需要的资源，然后，在返回的AuthFilter中，这个内部类可以引用外部类的所有字段，自然也包括注入的UserService，
 * 整个过程完全基于Spring的IoC容器完成
 * 标记了一个@Order(10)，因为Spring Boot支持给多个Filter排序，数字小的在前面，所以，多个Filter的顺序是可以固定的
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Order(10)
//@Component
@Slf4j
public class AuthFilterRegistrationBean extends FilterRegistrationBean<Filter> {

    @Autowired
    UserServiceImpl userService;

    @Override
    public Filter getFilter() {
        return new AuthFilter();
    }

    class AuthFilter implements Filter {
        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            log.info("AuthFilter doFilter...");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
