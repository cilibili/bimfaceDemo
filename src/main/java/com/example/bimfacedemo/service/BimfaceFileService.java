package com.example.bimfacedemo.service;

import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.file.bean.FileBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/10
 */
public interface BimfaceFileService {

    public FileBean fileUpload(MultipartFile file);

    public FileTranslateBean fileTranslate(Long fileId );

    String getFileViewToken(Long fieldId);
}
