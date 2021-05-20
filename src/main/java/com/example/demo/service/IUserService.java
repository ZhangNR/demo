package com.example.demo.service;

import com.example.demo.entity.User;

/**
 * IUserService
 *
 * @author ZhangJP
 * @date 2021/5/20
 */
public interface IUserService {

    /**
     * 登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return user
     */
    User signin(String email, String password);
}
