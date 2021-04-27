package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.Constant;
import com.example.demo.entity.WebExchangeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


/**
 * UploadController
 *
 * @author ZhangJP
 * @date 2020/11/10
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    private static final Logger bizLogger = LoggerFactory.getLogger(UploadController.class);


    @PostMapping("uploadFile")
    public JSONObject uploadFile(@RequestParam("WebData") String webData, @RequestParam("files") MultipartFile[] file) {
        bizLogger.info("[uploadFile]: Start to Upload File and Call Audit ...");

        WebExchangeInfo webExchangeInfo = JSON.parseObject(webData, WebExchangeInfo.class);
        JSONObject jsonResult = new JSONObject();

        try {

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
                JSONObject fileInfo = new JSONObject();

                File destFile = new File(path + file[i].getOriginalFilename());

                file[i].transferTo(destFile);

                fileInfo.put("fileName", file[i].getOriginalFilename());
                fileInfo.put("filePath", destFile.getAbsolutePath());
                fileInfo.put("fileSize", destFile.length());

                fileArray.add(i, fileInfo);
            }

            jsonResult.put("code", "0");
            jsonResult.put("data", fileArray);

        } catch (Exception e) {
            bizLogger.error("[uploadFile]: ", e);

            jsonResult.put("code", "99");
            jsonResult.put("errorMsg", e.getMessage());

        }

        return jsonResult;
    }
}