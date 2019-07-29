package com.example.bimfacedemo.service;

import com.example.bimfacedemo.entity.FileMessage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/18
 */
public interface FileService {
    Integer singleFileUpload(MultipartFile file, String destPath);

    Integer fileMessageSave (FileMessage fileMessage);

    FileMessage getFileMessageById (Integer id);

    List<FileMessage> getAllFileMessage ();

    Map<String, Object> httpSingleFileDownload(File sourceFile,  HttpServletResponse response);

    Map<String, Object> filePathCheck(String sourcePath, String fileName, String destPath);

    Map<String, Object> singleFileDownload(File sourceFile, File destFile);

    Map<String, Object> deleteFileMessageById(Integer id);

    Map<String, Object> moveOrDeleteFile(File sourceFile, File destFile);

    Map<String, Object> modifyFileMessage(FileMessage fileMessage);

    String addFileSeparator(Map<String, Object> result, String path);
}
