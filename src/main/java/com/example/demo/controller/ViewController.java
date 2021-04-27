package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ViewController
 *
 * @author ZhangJP
 * @date 2020/11/9
 */
@Controller
@RequestMapping("view")
public class ViewController {

    @RequestMapping("index")
    private String index() {
        return "index";
    }

    @RequestMapping("resource")
    private String resource() {
        return "resource";
    }

    @RequestMapping("imageConversion")
    private String imageConversion() {
        return "imageConversion";
    }

}
