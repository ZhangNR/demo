package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * InvoiceApplyParams
 *
 * @author ZhangJP
 * @date 2021/6/4
 */
@Data
public class InvoiceApplyParams {

    private Boolean isMerge;

    private String applyType;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isAll;

}
