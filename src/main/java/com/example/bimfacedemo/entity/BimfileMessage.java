package com.example.bimfacedemo.entity;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/30
 */
public class BimfileMessage {
    private Integer id;
    private String fileId;
    private String viewToken;

    public BimfileMessage() {
    }

    public BimfileMessage(String fileId) {
        this.fileId = fileId;
    }

    public BimfileMessage(Integer id, String fileId, String viewToken) {
        this.id = id;
        this.fileId = fileId;
        this.viewToken = viewToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getViewToken() {
        return viewToken;
    }

    public void setViewToken(String viewToken) {
        this.viewToken = viewToken;
    }
}
