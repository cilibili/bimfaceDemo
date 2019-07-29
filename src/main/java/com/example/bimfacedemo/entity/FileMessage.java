package com.example.bimfacedemo.entity;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/18
 */
public class FileMessage {
    private Integer id;
    private String path;
    private String fileName;
    private String userName;

    public FileMessage() {
    }

    public FileMessage(String path, String fileName, String userName) {
        this.path = path;
        this.fileName = fileName;
        this.userName = userName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FileMessage{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
