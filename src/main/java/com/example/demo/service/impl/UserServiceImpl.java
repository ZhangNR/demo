package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
@Service
public class UserServiceImpl implements IUserService {
    /**
     * 登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return user
     */
    @Override
    public User signin(String email, String password) {
        return new User();
    }
}
