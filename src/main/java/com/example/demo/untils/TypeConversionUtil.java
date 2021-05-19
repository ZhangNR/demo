package com.example.demo.untils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TypeConversionUtil
 *
 * @author ZhangJP
 * @date 2021/5/7
 */
public class TypeConversionUtil {

    private TypeConversionUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(TypeConversionUtil.class);

    /**
     * 去除字符串中的中括号
     *
     * @param stringWithSquareBrackets /
     * @return /
     */
    public static String removeBrackets(String stringWithSquareBrackets) {
        return stringWithSquareBrackets.substring(1, stringWithSquareBrackets.length() - 1);
    }

    /**
     * 将字符串转换成时间
     *
     * @param dateString /
     * @return /
     */
    public static Date string2Date(String dateString) {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            date = sf.parse(dateString);
        } catch (ParseException e) {
            logger.error("字符串转换成时间:", e);
        }
        return date;
    }

    /**
     * 将字符串转换成时间戳
     *
     * @param time /
     * @return /
     */
    public static String string2Timestamp(String time) {
        String timestamp = null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long longTime = sdf.parse(time).getTime();
            timestamp = Long.toString(longTime);

        } catch (ParseException e) {
            logger.error("字符串转换成时间戳:", e);
        }
        return timestamp;
    }

    /**
     * 格式化时间
     *
     * @param timestamp /
     * @return /
     */
    public static String formatDate(String timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.parseLong(timestamp));
        return simpleDateFormat.format(date);
    }
}
