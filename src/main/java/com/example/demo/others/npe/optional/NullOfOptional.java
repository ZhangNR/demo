package com.example.demo.others.npe.optional;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 使用Java8特性中的Optional来进行优雅地判空
 * A container object which may or may not contain a non-null value. If a value is present, isPresent() will return true and get() will return the value.
 * 一个可能包含也可能不包含非null值的容器对象。如果存在值， isPresent() 将返回 true，get() 将返回该值
 * 示例：需要获得Test2中的Info信息，但是参数为Test4，要一层层的申请，每一层都获得的对象都可能是空
 *
 * @author ZhangJP
 * @date 2021/5/14
 */
public class NullOfOptional {

    public static String testOptional(JSONObject test) {
        return Optional.ofNullable(test).flatMap(t -> Optional.ofNullable(t.getJSONObject("test3")))
                .flatMap(t3 -> Optional.ofNullable(t3.getJSONObject("test2")))
                .map(t2 -> t2.getString("info"))
                .orElse("");

    }

    /**
     * 示例：需要获得Test2中的Info信息，但是参数为Test4，要一层层的申请，每一层都获得的对象都可能是空
     */
    @SuppressWarnings("all")
    public String testSimple(JSONObject test) {
        if (test == null) {
            return "";
        }
        if (test.getJSONObject("test3") == null) {
            return "";
        }
        if (test.getJSONObject("test3").getJSONObject("test2") == null) {
            return "";
        }
        if (test.getJSONObject("test3").getJSONObject("test2").getString("info") == null) {
            return "";
        }
        return test.getJSONObject("test3").getJSONObject("test2").getString("info");
    }
}
