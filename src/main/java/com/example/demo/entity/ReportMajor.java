package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ReportMajor
 *
 * @author ZhangJP
 * @date 2020/7/29
 */
@Data
public class ReportMajor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String major;

    private String state;

}
