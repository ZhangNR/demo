package com.example.demo.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ViewController
 *
 * @author ZhangJP
 * @date 2020/11/9
 */
@Controller
@RequestMapping("view")
@Api(tags = "视图模块")
public class ViewController {

    @GetMapping("index")
    private String index() {
        return "index";
    }

    @GetMapping("resource")
    private String resource() {
        return "resource";
    }

    @GetMapping("imageConversion")
    private String imageConversion() {
        return "imageConversion";
    }

}
