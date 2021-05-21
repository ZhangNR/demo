package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * PageInfo
 *
 * @author ZhangJP
 * @date 2020/7/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> {

    /**
     * 总数
     */
    private long total;

    /**
     * 当前页
     */
    protected long current;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 查询数据列表
     */
    private List<T> records;
}
