package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah_solutions.common.PlaceCache;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 12 17
 * 建材收货信息实体类
 */
@Entity
@Table(name = "receiving_info")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ReceivingInfo implements Serializable {
    private static final long serialVersionUID = 3980513848211802661L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '收货信息id'")
    private String recId;
    @Column(columnDefinition = "VARCHAR(40)  COMMENT '(公司)'")
    private String recCompany;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(收货人姓名)'")
    private String recName;
    @Column(columnDefinition = "VARCHAR(30) NOT NULL COMMENT '(收货人电话)'")
    private String recPhome;
//    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(国家)'")
//    private String recRecCountry;
//    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(区域)'")
//    private String recRegion;
//    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(城市)'")
//    private String recCity;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '地址地區'")
    private String  recAddr;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '详细地址'")
    private String  recDetailedAddr;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(备注)'")
    private String recRemarks;
    @Column(columnDefinition = "INT(10)  COMMENT '关联的用户id'")
    private String userId;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  recCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  recUpdateTime;


    @Transient
    private String addrStr;


    public String getAddrStr() {
        return PlaceCache.getPlaceById(recAddr.substring(recAddr.lastIndexOf("/")+1)).getDetails()+"<br>"+recDetailedAddr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public String getRecCompany() {
        return recCompany;
    }

    public void setRecCompany(String recCompany) {
        this.recCompany = recCompany;
    }

    public String getRecDetailedAddr() {
        return recDetailedAddr;
    }

    public void setRecDetailedAddr(String recDetailedAddr) {
        this.recDetailedAddr = recDetailedAddr;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecPhome() {
        return recPhome;
    }

    public void setRecPhome(String recPhome) {
        this.recPhome = recPhome;
    }

    public String getRecAddr() {
        return recAddr;
    }

    public void setRecAddr(String recAddr) {
        this.recAddr = recAddr;
    }

    public String getRecRemarks() {
        return recRemarks;
    }

    public void setRecRemarks(String recRemarks) {
        this.recRemarks = recRemarks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecCreateTime() {
        return recCreateTime;
    }

    public void setRecCreateTime(String recCreateTime) {
        this.recCreateTime = recCreateTime;
    }

    public String getRecUpdateTime() {
        return recUpdateTime;
    }

    public void setRecUpdateTime(String recUpdateTime) {
        this.recUpdateTime = recUpdateTime;
    }
}
