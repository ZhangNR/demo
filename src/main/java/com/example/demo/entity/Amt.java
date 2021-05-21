package com.example.demo.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Amt
 * 出版文件数量 & 总金额
 *
 * @author ZhangJP
 * @date 2021/3/26
 */
@Data
public class Amt {

    private Integer count;
    private BigDecimal sum;

}
