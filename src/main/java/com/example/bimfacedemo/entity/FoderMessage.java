package com.example.bimfacedemo.entity;

/**
 * @author by cb
 * @description TODO
 * @date 2019/7/24
 */
public class FoderMessage {
    private Integer id;
    private String path;
    private String userName;
    private String creatTime;

    public FoderMessage() {
    }

    public FoderMessage(Integer id, String path, String userName, String creatTime) {
        this.id = id;
        this.path = path;
        this.userName = userName;
        this.creatTime = creatTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "FoderMessage{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", userName='" + userName + '\'' +
                ", creatTime='" + creatTime + '\'' +
                '}';
    }
}
