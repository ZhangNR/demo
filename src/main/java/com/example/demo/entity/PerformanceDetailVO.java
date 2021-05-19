package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * PerformanceDetail
 *
 * @author ZhangJP
 * @date 2020/10/26
 */
@Data
public class PerformanceDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("user_id")
    private String userId;

    @TableField("contribution_output")
    private BigDecimal contributionOutput;

}
