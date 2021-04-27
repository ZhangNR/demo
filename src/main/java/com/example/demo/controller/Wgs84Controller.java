package com.example.demo.controller;


import com.example.demo.service.IWgs84Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zjp
 * @since 2020-12-04
 */
@RestController
@RequestMapping("/wgs84")
public class Wgs84Controller {

    @Autowired
    private IWgs84Service wgs84Service;

    @RequestMapping("deal")
    public void deal() {
        wgs84Service.deal();
    }

}
