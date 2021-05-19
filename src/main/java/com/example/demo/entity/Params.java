package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Param
 *
 * @author ZhangJP
 * @date 2020/9/25
 */
@Data
@ApiModel(description = "获取数据传递参数")
public class Params {

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "项目组id")
    private String teamId;

    @ApiModelProperty(value = "年份", required = true)
    private Integer year;

    @ApiModelProperty(value = "月份")
    private Integer month;

}
