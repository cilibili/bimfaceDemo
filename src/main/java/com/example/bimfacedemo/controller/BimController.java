package com.example.bimfacedemo.controller;

import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.file.bean.FileBean;
import com.bimface.sdk.BimfaceClient;
import com.example.bimfacedemo.service.serviceImpl.BimfaceFileServiceImpl;
import com.example.bimfacedemo.utils.BimfaceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @description TODO
 * @date 2019/7/8 16:47
 * @author chenb-f
 */
@RestController
public class BimController {
    @Autowired
    BimfaceFileServiceImpl bimfaceFileService;

    /**
     * @Description 传入上传的文件，返回文件上传结束后的fieldId
     * @Params [file]
     * @Return java.lang.Long
     */
    @PostMapping("/bimFileUpload")
    public Map<String, Object> fileUpload(MultipartFile file){
        HashMap<String, Object> result = new HashMap<>();
        FileBean fileBean = bimfaceFileService.fileUpload(file);
        result.put("fieldId", fileBean.getFileId());
        return result;
    }

    /**
     * @Description 传入文件上传结束后的fieldId，转换文件
     * @Params [fieldId]
     * @Return com.bimface.api.bean.response.FileTranslateBean
     */
    @PostMapping("/bimFileTranslate")
    public FileTranslateBean fileTranslate(Long fileId){
        FileTranslateBean fileTranslateBean = bimfaceFileService.fileTranslate(fileId);
        return fileTranslateBean;
    }

    /**
     * @Description 传入文件上传结束后的fieldId，返回文件viewToken
     * @Params [fieldId]
     * @Return java.lang.Long
     */

    @GetMapping("/view/token/{fileId}")
    public Map<String, Object> getViewToken(@PathVariable("fileId") Long fileId ){
        HashMap<String, Object> result = new HashMap<>();
        String fileViewToken = bimfaceFileService.getFileViewToken(fileId);
        result.put("viewToken", fileViewToken);
        return result;
    }
}