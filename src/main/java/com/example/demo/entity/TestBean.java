package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * TestBean
 *
 * @author ZhangJP
 * @date 2021/3/2
 */
@Data
public class TestBean {

    private String name;
    private Integer age;
    private boolean pass;
    private Double score;
    private String examDate;
}
