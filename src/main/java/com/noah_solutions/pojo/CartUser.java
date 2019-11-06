package com.noah_solutions.pojo;


import com.noah_solutions.common.ProjectConfig;

/**
 * Created by pz on 16/11/23.
 * "username": "纸飞机"
 ,"id": "100000"
 ,"status": "online"
 ,"sign": "在深邃的编码世界，做一枚轻盈的纸飞机"
 ,"avatar": "http://cdn.firstlinkapp.com/upload/2016_6/1465575923433_33812.jpg"
 */
public class CartUser {

    private String id;

    public int getFgid() {
        return fgid;
    }

    public void setFgid(int fgid) {
        this.fgid = fgid;
    }

    private int fgid;
    private String sign;
    private String avatar;
    private String username;
    private String status;
    private Integer typeId;

    public CartUser(){

    }

    public CartUser(String id, String username, String sign, String avatar,String status){
        this.id = id;
        this.username = username;
        this.sign = sign;
        this.avatar = ProjectConfig.HOST_NAME+"image/"+avatar;
        this.status = ProjectConfig.CartUserStatus.queryValue(Integer.valueOf(status));
    }


    public CartUser(String id, String username, String sign, String avatar,String status,Integer typeId){
        this.id = id;
        this.username = username;
        this.sign = sign;
        this.avatar = avatar;
        this.status = ProjectConfig.CartUserStatus.queryValue(Integer.valueOf(status));
        this.typeId = typeId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
