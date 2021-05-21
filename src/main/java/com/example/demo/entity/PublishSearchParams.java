package com.example.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * SearchParams
 *
 * @author ZhangJP
 * @date 2020/7/29
 */
@Data
public class PublishSearchParams {

    private String project;

    private String name;

    private Integer year;

    private Integer month;

    private String major;

    private String creatorName;

    private String startDate;

    private String endDate;

    private String note;

    private Integer pageSize;

    private Integer pageNumber;

    private Integer projectApprovalId;

    private List<Integer> ids;

    private String approval;

    private String state;

    private User user;
}
