package com.example.bimfacedemo.service.serviceImpl;

import com.bimface.api.bean.response.FileTranslateBean;
import com.bimface.exception.BimfaceException;
import com.bimface.file.bean.FileBean;
import com.bimface.sdk.BimfaceClient;
import com.example.bimfacedemo.entity.BimfileMessage;
import com.example.bimfacedemo.mapper.FileMapper;
import com.example.bimfacedemo.service.BimfaceFileService;
import com.example.bimfacedemo.utils.BimfaceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author by cb
 * @description bimface接口调用
 * @date 2019/7/9
 */
@Service
public class BimfaceFileServiceImpl implements BimfaceFileService{

    @Autowired
    FileMapper fileMapper;

    private BimfaceClient bimfaceClient = new BimfaceFactory().getBimfaceClient();
    private Log logger = LogFactory.getLog(BimfaceFileServiceImpl.class);

    /**
     * @Description 传入文件名和文件路径上传文件
     * @Params [fileName, fileUrl]
     * @Return void
     */
    @Override
    public FileBean fileUpload(MultipartFile file) {
        //文件输入为空直接返回
        if (file == null){
            logger.debug("文件输入为空");
            return null;
        }
        //文件上传成功返回类型参数
        FileBean upload = null;
        try {
            String fileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            InputStream inputStream = file.getInputStream();
            logger.debug("上传文件的fileName: " + fileName + ", fileSize: "+ fileSize + "B");
            upload = bimfaceClient.upload(fileName, fileSize, inputStream);
            logger.debug("文件上传成功");
        } catch (BimfaceException e) {
            logger.error("文件上传失败");
            logger.debug("error", e);
        } catch (IOException e){
            logger.error("获取文件输入流失败");
            logger.debug("error", e);
        }
        return upload;
    }

    @Override
    public FileTranslateBean fileTranslate(Long fileId ){
        FileTranslateBean translateBean = null;
        try {
            logger.debug("开始转换文件");
            translateBean = bimfaceClient.translate(fileId);
            logger.debug("文件转换成功");
        } catch (BimfaceException e) {
            logger.error("文件转换失败");
            logger.debug("error", e);
        }
        return translateBean;
    }

    @Override
    public String getFileViewToken(Long fieldId) {
        String viewToken = "";
        try {
            viewToken = bimfaceClient.getViewTokenByFileId(fieldId);
            logger.debug("fieldId" + fieldId + "文件获取viewToken成功：" + viewToken);
        } catch (BimfaceException e) {
            logger.debug("文件获取viewToken失败");
            logger.debug("error", e);
        }
        return viewToken;
    }

    @Override
    public BimfileMessage saveFileId(String fileId) {
        BimfileMessage bimfileMessage = new BimfileMessage(fileId);
        fileMapper.saveFileId(bimfileMessage);
        return bimfileMessage;
    }

    @Override
    public BimfileMessage getBimfileMessageById(Integer id) {
        return fileMapper.getBimfileMessageById(id);
    }

    @Override
    public void updateBimfileViewTokenByFileId(String fileId, String fileViewToken) {
        logger.debug("修改bimfile表请求参数：" + "fileId：" + fileId + " ，fileViewToken：" + fileViewToken);
        BimfileMessage bimfileMessageByFileId = fileMapper.getBimfileMessageByFileId(fileId);
        logger.debug("在表中查询到fileId:" + fileId + "对应的Bimfile信息为：" + bimfileMessageByFileId);
        bimfileMessageByFileId.setViewToken(fileViewToken );
        fileMapper.updateBimfileMessage(bimfileMessageByFileId);
    }

    @Override
    public List<BimfileMessage> getAllBimfileMessage() {
        return fileMapper.getAllBimfileMessage();
    }


}
