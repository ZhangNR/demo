package com.example.demo.controller;


import com.example.demo.service.ISalaryDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 记录公司员工逐月的工资信息
 *
 * @author zjp
 * @since 2020-09-25
 */
@RestController
@RequestMapping("/detail")
@Api(tags = "处理数据")
public class SalaryDetailController {

    private final ISalaryDetailService service;

    @Autowired
    public SalaryDetailController(ISalaryDetailService service) {
        this.service = service;
    }

    @ApiOperation(value = "处理最近两个月的数据")
    @GetMapping("/generatedLastMonth")
    public void generatedLastMonth() {
        service.generatedLastMonth();
    }

    @ApiOperation(value = "处理财务年数据")
    @GetMapping("/generatedAutomatically")
    public void generatedAutomatically() {
        service.generatedAutomatically();
    }


    /**
     * -@ApiImplicitParams({
     * -@ApiImplicitParam(name = "year", value = "年份", required = true, dataType = "Integer", paramType = "path"),
     * -@ApiImplicitParam(name = "month", value = "月份", required = true, dataType = "Integer", paramType = "path")
     * })
     */
    @ApiOperation(value = "处理指定年指定月数据（管理员）")
    @GetMapping("/customer/{year}/{month}")
    public void generatedCustomerYearMonth(@PathVariable int year, @PathVariable int month) {
        service.generatedCustomerYearMonth(year, month);
    }

    @ApiOperation(value = "定时任务", notes = "每月1号1点开始每隔7天执行一次，生成最近连个月的数据")
    @Scheduled(cron = "0 0 1 1/7 * ?")
    public void generatedLastMonths() {
        service.generatedLast2Months();

    }

}
