package com.example.bimfacedemo.controller;

import com.example.bimfacedemo.entity.FileMessage;
import com.example.bimfacedemo.service.FileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by cb
 * @description 文件上传下载即文件信息的增删查改功能
 * @date 2019/7/18
 */
@RestController
public class FileController {

    @Autowired
    FileService fileService;

    private static Log logger = LogFactory.getLog(FileController.class);
    /**
     * @Description 传入上传的文件，0：上传成功，-1：文件上传失败，-2：文件为空，-3：上传地址文件已存在，-11：没有传入文件，-12：服务器文件夹不存在
     * @Params [file，destPath]  path是本地文件路径
     * @Return java.lang.Long 返回文件上传结束后的fileId；
     */
    @PostMapping("/fileUpload")
    public Map<String, Object> fileUpload(MultipartFile file, String destPath, String userName){
        logger.debug("上传文件传入参数：file:" + file + ",destPath: " + destPath + ",userName:"  + userName);
        HashMap<String, Object> result = new HashMap<>();
        destPath = fileService.addFileSeparator(result, destPath);
        if (null != result.get("code") ){
            // code 不为空，即-12
            return result;
        }
        if (null == file){
            // 没有传入文件。传入文件为空和没有传入文件是有区别的
            result.put("code", -11);
            logger.debug("没有传入文件");
            return result;
        }
        Integer uploadResult = fileService.singleFileUpload(file, destPath);
        //文件上传成功，保存文件信息
        if (uploadResult == 0){
            FileMessage fileMessage = new FileMessage(destPath, file.getOriginalFilename(), userName);
            Integer fileMessageId = fileService.fileMessageSave(fileMessage);
            result.put("fileMessageId", fileMessageId);
        }
        // 返回上传结果
        result.put("code", uploadResult);
        return result;
    }

    /**
     * @Description 0：下载成功，-1：下载失败，-2：下载的源文件不存在，-12：下载路径不存在
     * 文件默认下载地址：tempDownloads/，注意！下载路径要以“/ ”结尾
     * @Params [sourcePath, fileName, destPath]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/fileDownload")
    public  Map<String, Object> downloadFile(String sourcePath, String fileName, String destPath) {
        logger.debug("文件下载传入参数: sourcePath:" + sourcePath + ",fileName:" + fileName + ",destPath:" + destPath);
        // 默认下载路径
        String defaultDestPath = "tempDownloads/";
        if (destPath == null) {
            destPath = defaultDestPath;
        }
        Map<String, Object> result = fileService.filePathCheck(sourcePath, fileName, destPath);
        //获取文件对象并删除这个对象
        File sourceFile = (File) result.remove("sourceFile");
        File destFile = (File) result.remove("destFile");
        //文件路径检查不成功
        if (!result.get("code").equals(0)){
            return result;
        }
        result = fileService.singleFileDownload(sourceFile, destFile);
        return result;
    }

    /**
     * @Description 查询对应id的fileMessage  0：查询值成功，-10：该id不存在
     * @Params [id]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/fileMessage/{id}")
    public Map<String, Object> getFileMessageById(@PathVariable("id") Integer id){
        HashMap<String, Object> result = new HashMap<>();
        FileMessage fileMessageById = fileService.getFileMessageById(id);
        if (null == fileMessageById) {
            result.put("code", -10);
            return result;
        }
        result.put("code", 0);
        result.put("fileMessage", fileMessageById);
        return result;
    }

    /**
     * @Description 查询所有fileMessage 0：查询值成功，-10：表中没有值
     * @Params []
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/fileMessage")
    public Map<String, Object> getAllFileMessage(){
        HashMap<String, Object> result = new HashMap<>();
        List<FileMessage> allFileMessage = fileService.getAllFileMessage();
        if (null == allFileMessage || allFileMessage.size() == 0){
            result.put("code", -10);
            return result;
        }
        result.put("code", 0);
        result.put("fileMessage", allFileMessage);
        return result;
    }

    /**
     * @Description 删除指定id的fileMessage，并且删除该fileMessage信息中文件地址下的文件
     * 0：文件删除成功，-2：源文件不存在，-3：删除源文件失败，-10：对应id不存在 -1：文件转移失败，-12 文件夹不存在
     * @Params [id]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @DeleteMapping("/fileMessage/{id}")
    public Map<String, Object> deleteFileMessageById(@PathVariable("id") Integer id){
        Map<String, Object> result ;
        result = fileService.deleteFileMessageById(id);
        return result;
    }

    /***
     * @Description 修改指定id的文件信息，转移文件。
     * 0：文件删除成功，-1：文件转移失败，-2：源文件不存在，-3：删除源文件失败，-10：对应id不存在,-11：传入参数为空， -12 文件夹不存在,-13：未传入参数id或则path
     * @Params [id]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @PutMapping("/fileMessage")
    public Map<String, Object> modifyFileMessageById(@RequestBody FileMessage fileMessage){
        logger.debug("修改文件信息传入的参数为：" + fileMessage);
        Map<String, Object> result = new HashMap<>();
        if (null == fileMessage){
            result.put("code", -11);
            logger.error("传入参数为空，请传入FileMessage的属性参数");
            return result;
        }
        if (null == fileMessage.getId() || null == fileMessage.getPath()){
            result.put("code", -13);
            logger.error("FileMessage的属性参数Id或则path为空，请传入对应id,和path");
            return result;
        }
        result = fileService.modifyFileMessage(fileMessage);
        return result;
    }

    /**
     * @Description HTTP页面请求下载接口 0：下载成功，-1：下载失败，-2：下载的源文件不存在，-3：下载路径不存在
     * 文件默认下载地址：httpTempDownloads/  注意！下载路径要以“/ ”结尾
     * @Params [sourcePath, fileName, destPath, request, response]，destPath是页面下载路径，页面下载文件保存的默认路径必须存在！
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("/httpFileDownload")
    public  Map<String, Object> httpDownloadFile(String sourcePath, String fileName, String destPath, HttpServletRequest request, HttpServletResponse response) {
        // 默认下载路径
        String defaultDestPath = "httpTempDownloads/";
        if (destPath == null) {
            destPath = defaultDestPath;
        }
        Map<String, Object> result = fileService.filePathCheck(sourcePath, fileName, destPath);
        //文件路径检查不成功
        if (!result.get("code").equals(0)){
            return result;
        }
        // 设置强制下载不打开
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        //获取文件对象并删除这个对象
        File sourceFile = (File) result.remove("sourceFile");
        // 不需要目标文件对象
        result.remove("destFile");
        fileService.httpSingleFileDownload(sourceFile, response);
        return result;
    }

}
