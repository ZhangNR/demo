package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjp
 * @since 2020-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_contract_project")
public class OrderContractProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 合同id
     */
    @TableField("contract_id")
    private Integer contractId;

    /**
     * 项目编号
     */
    @TableField("project_num")
    private String projectNum;

    /**
     * 项目金额
     */
    @TableField("project_money")
    private Double projectMoney;

    /**
     * 合计项目金额（累加增补合同金额）
     */
    @TableField("sum_project_money")
    private Double sumProjectMoney;

    /**
     * 关联出版
     */
    @TableField("publishs")
    private String publishs;


}
