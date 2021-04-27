package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeListRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.example.demo.config.Constant;
import com.example.demo.entity.WebExchangeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * UploadController
 *
 * @author ZhangJP
 * @date 2020/11/10
 */
@RestController
@RequestMapping("web")
public class UploadController {
    private static final Logger bizLogger = LoggerFactory.getLogger(UploadController.class);


    @RequestMapping("uploadFile")
    private JSONObject uploadFile(@RequestParam("WebData") String webData, @RequestParam("files") MultipartFile[] file) {
        bizLogger.info("[uploadFile]: Start to Upload File and Call Audit ...");

        WebExchangeInfo webExchangeInfo = JSON.parseObject(webData, WebExchangeInfo.class);
        System.out.println(JSON.toJSONString(webExchangeInfo));

        JSONObject jsonResult = new JSONObject();

        try {
//            if (file.length > webExchangeInfo.getAuthCode().size()) {
//                throw new Exception("选择的文件数目和授权码数目不匹配，文件上传失败！");
//            }

            String path = Constant.FILE_PATH;
            if (webExchangeInfo.getMonth() < 10) {
                path += webExchangeInfo.getYear() + "-0" + webExchangeInfo.getMonth() + "/";
            } else {
                path += webExchangeInfo.getYear() + "-" + webExchangeInfo.getMonth() + "/";
            }

            File dest = new File(path);

            if (!dest.exists()) {
                dest.mkdirs();
            }

            JSONArray fileArray = new JSONArray();

            for (int i = 0; i < file.length; i++) {
                System.out.println(file[i].getOriginalFilename());
                JSONObject fileInfo = new JSONObject();

                File destFile = new File(path + file[i].getOriginalFilename());

                file[i].transferTo(destFile);

                fileInfo.put("fileName", file[i].getOriginalFilename());
                fileInfo.put("filePath", destFile.getAbsolutePath());
                fileInfo.put("fileSize", destFile.length());

                fileArray.add(i, fileInfo);
            }

            System.out.println("fileArray: " + JSON.toJSONString(fileArray));

//            SalaryUtil.sendSalaryData4Approval(webExchangeInfo, fileArray);


            jsonResult.put("code", "0");

        } catch (Exception e) {
            bizLogger.error("[uploadFile]: --> " + e.getMessage(), e);

            jsonResult.put("code", "99");
            jsonResult.put("errorMsg", e.getMessage());

        }

        return jsonResult;
    }
}