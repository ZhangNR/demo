package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.others.npe.optional.NullOfOptional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * NullOfOptionalTest
 *
 * @author ZhangJP
 * @date 2021/5/14
 */
@SpringBootTest
@Slf4j
public class NullOfOptionalTest {

    @Test
    void context() {
            JSONObject test = new JSONObject();
            test.put("test3", null);
            String testOptional = NullOfOptional.testOptional(test);

            log.info(testOptional);
    }
}
