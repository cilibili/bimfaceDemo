package com.example.bimfacedemo.service.serviceImpl;

import com.example.bimfacedemo.entity.FileMessage;
import com.example.bimfacedemo.mapper.FileMapper;
import com.example.bimfacedemo.service.FileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/18
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    private static Log logger = LogFactory.getLog(FileServiceImpl.class);

    @Override
    @Transactional
    public Integer singleFileUpload(MultipartFile file, String destPath) {
        // 0代表成功，-1代表上传失败，-2代表文件为空
        if (file.isEmpty()) {
            logger.error("文件为空");
            return -2;
        }
        if (null == destPath || "".equals(destPath.trim())) {
            destPath = "files/";
            logger.debug("文件上传路径为空，已设置默认路径:" + destPath);
        }
        String fileName = file.getOriginalFilename();
        File destFile = new File(destPath + fileName);
        if (destFile.exists()) {
            logger.error("文件已存在");
            return -3;
        }
        try {
            // 这里只是简单例子，文件直接输出到项目路径下。
            // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。
            logger.debug("传入文件：" + fileName);
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(destFile));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.debug("error", e);
            return -1;
        }
        logger.debug("文件" + fileName + "上传成功，上传路径为：" + destPath + fileName );
        return 0;
    }

    @Override
    public Integer fileMessageSave(FileMessage fileMessage) {
        //fileMessageSave方法返回的是修改的行数
        fileMapper.fileMessageSave(fileMessage);
        //保存后的主键在对象属性里
        return fileMessage.getId();
    }

    @Override
    public FileMessage getFileMessageById(Integer id) {
        return fileMapper.getFileMessageById(id);
    }

    @Override
    public List<FileMessage> getAllFileMessage() {
        return fileMapper.getAllFileMessage();
    }

    @Override
    @Transactional
    public Map<String, Object> httpSingleFileDownload(File sourceFile, HttpServletResponse response) {
        HashMap<String, Object> result = new HashMap<>();
        byte[] buffer = new byte[1024];
        //关闭io流只关闭外层的就行
        BufferedInputStream bis = null;
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            result.put("code", 0);
            return result;
        } catch (Exception e) {
            logger.debug("error", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.debug("error", e);
                }
            }
        }
        result.put("code", -1);
        return result;
    }


    @Override
    public Map<String, Object> filePathCheck(String sourcePath, String fileName, String destPath) {
        logger.debug("检查文件路径传入参数：" + "sourcePath:" + sourcePath+ " ,fileName:" + sourcePath+ " ,destPath:" + sourcePath);
        HashMap<String, Object> result = new HashMap<>();
        sourcePath = addFileSeparator(result, sourcePath);
        destPath = addFileSeparator(result, destPath);
        if (null != result.get("code") ){
            // code 不为空，即-12
            return result;
        }
        File sourceFile = new File(sourcePath + fileName);
        if (!sourceFile.exists()) {
            logger.error("要下载的文件不存在");
            result.put("code", -2);
            return result;
        }
        File destFile = new File(destPath + fileName);
        // 检测是否存在目录
        if (!destFile.getParentFile().exists()) {
            logger.debug("下载路径不存在");
            result.put("code", -12);
            return result;
            //destFile.getParentFile().mkdirs();// 新建文件夹
        }
        // 判断文件是否存在，不存在则更名文件
        for (int subfix = 1; destFile.exists(); subfix++) {
            int index = fileName.lastIndexOf(".");
            String newFileName = fileName.substring(0, index) + subfix + fileName.substring(index);
            destFile = new File(destPath + newFileName);
            logger.error("下载目标文件已存在,文件自动更名为:" + destPath + newFileName);
        }
        result.put("code", 0);
        result.put("sourceFile", sourceFile);
        result.put("destFile", destFile);
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> singleFileDownload(File sourceFile, File destFile) {
        HashMap<String, Object> result = new HashMap<>();
        byte[] buffer = new byte[1024];
        //关闭io流只关闭外层的就行
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            bis = new BufferedInputStream(fis);
            FileOutputStream fos = new FileOutputStream(destFile);
            bos = new BufferedOutputStream(fos);
            int i = bis.read(buffer);
            while (i != -1) {
                bos.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            logger.debug("文件转移成功，转移路径为：" + destFile.getPath() );
            result.put("code", 0);
            return result;
        } catch (Exception e) {
            logger.debug("error", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.debug("error", e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.debug("error", e);
                }
            }
        }
        result.put("code", -1);
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteFileMessageById(Integer id) {
        Map<String, Object> result = new HashMap<>();
        FileMessage fileMessageById = fileMapper.getFileMessageById(id);
        if (null == fileMessageById){
            result.put("code", -10);
            return result;
        }
        String path = fileMessageById.getPath();
        // 检查文件路径
        path = addFileSeparator(result, path);
        if (null != result.get("code") ){
            // code 不为空，即-12
            return result;
        }
        String fileName = fileMessageById.getFileName();
        File deleteFile = new File(path + fileName);
        result = moveOrDeleteFile(deleteFile, null);
        if (result.get("code").equals(0)){
            fileMapper.deleteFileMessageById(id);
        }
        return result;
    }

    /***
     * @Description destFile传入为null,时为删除源文件，非空为转移源文件
     * @Params [sourceFile, destFile]
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    @Transactional
    public Map<String, Object> moveOrDeleteFile(File sourceFile, File destFile) {
        Map<String, Object> result = new HashMap<>();
        if (null == sourceFile || !sourceFile.exists() || !sourceFile.isFile()){
            // 源文件不存在，返回错误码-2
            result.put("code", -2);
            logger.debug("源文件不存在非文件，返回错误码-2");
            return result;
        }
        // 目标地址非空，转移文件
        if (null != destFile){
            result = singleFileDownload(sourceFile, destFile);
            // 文件转移失败，错误码返回-1
            if (!result.get("code").equals(0)){
                return result;
            }
        }
        // 删除源文件
        if (!sourceFile.delete()){
            // 删除源文件失败，返回错误码-3
            result.put("code", -3);
            logger.debug("文件删除失败，返回错误码-3");
            return result;
        }
        result.put("code", 0);
        logger.debug("文件删除成功");
        return result;
    }

    @Override
    @Transactional
    public Map<String, Object> modifyFileMessage(FileMessage fileMessage) {
        HashMap<String, Object> result = new HashMap<>();
        FileMessage fileMessageById = getFileMessageById(fileMessage.getId());
        if (null == fileMessageById){
            result.put("code", -10);
            return result;
        }
        String sourcePath = fileMessageById.getPath();
        String sourceFileName = fileMessageById.getFileName();
        File sourceFile = new File(sourcePath + sourceFileName);
        logger.debug("修改前的路径和文件名为："+ sourcePath + sourceFileName);
        String destPath = fileMessage.getPath();
        String destFileName = fileMessage.getFileName();
        File destFile = new File(destPath + destFileName);
        logger.debug("修改后的路径和文件名为："+ destPath + destFileName);
        result = (HashMap<String, Object>) moveOrDeleteFile(sourceFile, destFile);
        // 文件转移成功，才修改数据库中的值
        if (result.get("code").equals(0)){
            fileMapper.updateFileMessage(fileMessage );
            logger.debug("修改后的fileMessage参数为：" + fileMapper.getFileMessageById(fileMessage.getId()));
        }
        return result;
    }

    @Override
    public String addFileSeparator(Map<String, Object> result, String path) {
        if (!path.endsWith(File.separator)) {
            logger.debug("路径缺少后缀，自动增加后缀");
            path =  path + File.separator;
            logger.debug("自动增加后缀后的路径为：" + path);
        }
        if (!new File(path).exists()){
            // 传入文件路径不存在，返回错误码-12
            result.put("code", -12);
            logger.error("传入文件路径不存在，返回错误码-12");
        }
        return path;
    }


    /**
     * @Description 多文件上传 主要是使用了MultipartHttpServletRequest和MultipartFile
     * @Params []
     * @Return java.lang.Integer
     */
    public Integer batchFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (MultipartFile file1 : files) {
            file = file1;
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    //return "You failed to upload " + i + " => " + e.getMessage();
                    return -1;
                }
            } else {
                //return "You failed to upload " + i + " because the file was empty.";
                return -1;
            }
        }
        //return "upload successful";
        return 0;
    }
}

