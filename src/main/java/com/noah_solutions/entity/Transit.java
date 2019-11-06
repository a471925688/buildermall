package com.noah_solutions.entity;

import javax.persistence.*;

@Entity
public class Transit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '自增id'")
    private String id;
    @Column(columnDefinition = "INT(10)   COMMENT '(用戶id)'")
    private String userId;
    @Column(columnDefinition = "VARCHAR(50) NOT NULL COMMENT '名稱'")
    private String  name;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '地址地區'")
    private String  addrId;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '详细地址'")
    private String  detailedAddr;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(收货人姓名)'")
    private String recName;
    @Column(columnDefinition = "VARCHAR(30) NOT NULL COMMENT '(收货人电话)'")
    private String recPhone;
    @Column(columnDefinition = "INT(10)  COMMENT '類型1.官方集運.2.用戶集運'")
    private Integer type;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  updateTime;






    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }

    public String getDetailedAddr() {
        return detailedAddr;
    }

    public void setDetailedAddr(String detailedAddr) {
        this.detailedAddr = detailedAddr;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecPhone() {
        return recPhone;
    }

    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
