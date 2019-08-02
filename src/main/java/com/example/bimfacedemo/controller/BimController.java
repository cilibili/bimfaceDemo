package com.example.bimfacedemo.controller;

import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.file.bean.FileBean;
import com.bimface.sdk.BimfaceClient;
import com.example.bimfacedemo.entity.BimfileMessage;
import com.example.bimfacedemo.service.serviceImpl.BimfaceFileServiceImpl;
import com.example.bimfacedemo.utils.BimfaceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
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
    private static Log logger = LogFactory.getLog(BimController.class);

    /**
     * @Description 传入上传的文件，返回文件上传结束后的fieldId
     * @Params [file]
     * @Return java.lang.Long
     */
    @PostMapping("/bimFileUpload")
    public Map<String, Object> fileUpload(MultipartFile file){
        HashMap<String, Object> result = new HashMap<>();
        FileBean fileBean = bimfaceFileService.fileUpload(file);

        Long fileId = fileBean.getFileId();
        bimfaceFileService.saveFileId(fileId.toString());

        result.put("fieldId", fileId);
        return result;
    }

    /**
     * @Description 传入文件上传结束后的fieldId，转换文件
     * @Params [fieldId]
     * @Return com.bimface.api.bean.response.FileTranslateBean
     */
    @PostMapping("/bimFileTranslate")
    public FileTranslateBean fileTranslate(Long fileId){
        return bimfaceFileService.fileTranslate(fileId);
    }

    /**
     * @Description 传入文件上传结束后的fieldId，返回文件viewToken
     * @Params [fieldId]
     * @Return java.lang.Long
     */

    @GetMapping("/viewToken")
    public Map<String, Object> getViewToken( Long fileId ){
        logger.debug("调用/viewToken传入的参数:" + "fileId: "+ fileId);
        HashMap<String, Object> result = new HashMap<>();
        String fileViewToken = bimfaceFileService.getFileViewToken(fileId);
        bimfaceFileService.updateBimfileViewTokenByFileId(fileId.toString(), fileViewToken);
        result.put("viewToken", fileViewToken);
        return result;
    }

    @GetMapping("/bimfile")
    public Map<String, Object> getBimfileMessageByid(){
        HashMap<String, Object> result = new HashMap<>();
        List<BimfileMessage> bimfileMessage = bimfaceFileService.getAllBimfileMessage();
        result.put("bimfileMessage", bimfileMessage);
        return result;
    }

    @GetMapping("/bimfile/{id}")
    public Map<String, Object> getBimfileMessageByid(@PathVariable Integer id){
        HashMap<String, Object> result = new HashMap<>();
        BimfileMessage bimfileMessage = bimfaceFileService.getBimfileMessageById(id);
        result.put("bimfileMessage", bimfileMessage);
        return result;
    }

}