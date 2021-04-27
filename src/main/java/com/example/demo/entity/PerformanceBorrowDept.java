package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("performance_borrow_dept")
public class PerformanceBorrowDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请部门
     */
    @TableField("dept_id")
    private String deptId;

    @TableField("sub_dept_id")
    private String subDeptId;

    /**
     * 所借部门
     */
    @TableField("borrow_dept_id")
    private String borrowDeptId;

    @TableField("borrow_sub_dept_id")
    private String borrowSubDeptId;

    /**
     * 所借产值
     */
    @TableField("output")
    private Double output;

    /**
     * 金额是否使用
     */
    @TableField("dept_use")
    private String deptUse;

    /**
     * 借出部门是否扣除
     */
    @TableField("borrow_dept_use")
    private String borrowDeptUse;

    /**
     * 审批编号
     */
    @TableField("voucher_num")
    private String voucherNum;

    @TableField("creator_id")
    private String creatorId;

    @TableField("create_date")
    private LocalDateTime createDate;


}
