package com.example.bimfacedemo.service;

import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.file.bean.FileBean;
import com.example.bimfacedemo.entity.BimfileMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/10
 */
public interface BimfaceFileService {

    public FileBean fileUpload(MultipartFile file);

    public FileTranslateBean fileTranslate(Long fileId );

    String getFileViewToken(Long fieldId);

    BimfileMessage saveFileId(String fileId);

    BimfileMessage getBimfileMessageById(Integer id);

    void updateBimfileViewTokenByFileId(String fileId, String fileViewToken);

    List<BimfileMessage> getAllBimfileMessage();
}
