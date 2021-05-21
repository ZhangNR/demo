package com.example.demo.untils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ListUtil
 *
 * @author ZhangJP
 * @date 2020/7/31
 */
public class ListUtil {

    /**
     * 比较两个集合元素是否相同
     *
     * @param a list
     * @param b list
     * @return boolean
     */
    public static boolean compareLists(List<Integer> a, List<Integer> b) {
        Collections.sort(a);
        Collections.sort(b);
        return a.toString().equals(b.toString());
    }

    /**
     * 交集
     *
     * @param a list
     * @param b list
     * @return intersection
     */
    public static List<Integer> intersection(List<Integer> a, List<Integer> b) {
        return a.stream().filter(b::contains).collect(Collectors.toList());
    }

    /**
     * 差集 a-b
     *
     * @param a list
     * @param b list
     * @return reduce
     */
    public static List<Integer> subtraction(List<Integer> a, List<Integer> b) {
        return a.stream().filter(item -> !b.contains(item)).collect(Collectors.toList());
    }

    /**
     * 差集 b-a
     *
     * @param a list
     * @param b list
     * @return reduce
     */
    public static List<Integer> subtractionBA(List<Integer> a, List<Integer> b) {
        return b.stream().filter(item -> !a.contains(item)).collect(Collectors.toList());
    }

    /**
     * 去重并集
     * 
     * @param a list
     * @param b list
     * @return listAllDistinct
     */
    public static List<Integer> listAllDistinct(List<Integer> a, List<Integer> b) {
        List<Integer> listAll = a.parallelStream().collect(Collectors.toList());
        List<Integer> listAll2 = b.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        return listAll.stream().distinct().collect(Collectors.toList());
    }

}
