package com.noah_solutions.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    private Integer id;//發送人id
    private String avatar;//頭像
    private String content;//內容
    private Integer from;//發送人id
    private Boolean mine=false;//是否自己
    private String username;//發送人名字
    private String type;//消息類型
    private String groupId;//分組id
    private String to;//發送對象id
    private Boolean read;//是否已讀
    private Boolean status;//處理狀態
    private String orderId;

    List<CartUser> cartUsers;


    public Message() {
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Boolean getMine() {
        return mine;
    }

    public void setMine(Boolean mine) {
        this.mine = mine;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<CartUser> getCartUsers() {
        return cartUsers;
    }

    public void setCartUsers(List<CartUser> cartUsers) {
        this.cartUsers = cartUsers;
    }
}
