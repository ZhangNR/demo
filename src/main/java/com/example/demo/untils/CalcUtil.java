package com.example.demo.untils;

import java.math.BigDecimal;

/**
 * CalcUtil
 *
 * @author ZhangJP
 * @date 2020/9/28
 */
public class CalcUtil {

    private CalcUtil() {
    }

    /**
     * 自定义加法计算
     */
    public static BigDecimal sumOwn(BigDecimal v1, BigDecimal v2, BigDecimal v3, BigDecimal v4, BigDecimal v5, BigDecimal v6) {
        return v1.add(v2).add(v3).add(v4).add(v5).add(v6);
    }

    public static BigDecimal sum(BigDecimal v1, BigDecimal v2) {
        return v1.add(v2);
    }


    /**
     * 乘法计算
     * 四舍五入
     * 精确小数位
     *
     * @param v1    乘数1
     * @param v2    乘数2
     * @param scale 精确小数位（正整数）
     * @return BigDecimal
     */
    public static BigDecimal mul(BigDecimal v1, BigDecimal v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v1.multiply(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 除法计算（四舍五入、精确小数位）
     *
     * @param v1    除数
     * @param v2    被除数
     * @param scale 精确小数位（正整数）
     * @return BigDecimal
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale) {
        if (v2.intValue() == 0) {
            return BigDecimal.ZERO;
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = BigDecimal.valueOf(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

}
