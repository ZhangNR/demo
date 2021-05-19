package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.SalaryDetail;

/**
 * <p>
 * 记录公司员工逐月的工资信息 服务类
 * </p>
 *
 * @author zjp
 * @since 2020-09-25
 */
public interface ISalaryDetailService extends IService<SalaryDetail> {

    /**
     * 生成上个月的数据
     */
    void generatedLastMonth();

    /**
     * 获取部门的贡献产值、成本等数据
     * 定时任务或者按钮触发\\ 过去两个月的数据
     */
    void generatedLast2Months();

    /**
     * 获取部门的贡献产值、成本等数据 url 生成接口
     * \\定时任务或者按钮触发\\
     */
    void generatedAutomatically();

    /**
     * （管理员）生成自定义年、月数据
     *
     * @param year  年
     * @param month 月
     */
    void generatedCustomerYearMonth(int year, int month);
}
