package com.example.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * WebExchangeInfo
 *
 * @author ZhangJP
 * @date 2020/11/10
 */
@Data
public class WebExchangeInfo {
    private String deptId;
    private String uploadType;
    private String deptName;
    private String position;
    private String userId;
    private String userName;
    private List<String> authCode;
    private int year;
    private int month;
    private int day;
}
