package com.example.demo.entity;

import lombok.Data;

/**
 * InvoiceRecordParams
 *
 * @author ZhangJP
 * @date 2021/6/7
 */
@Data
public class InvoiceRecordParams {

    private Integer pageSize;

    private Integer pageNumber;

    private String applyReason;

}
