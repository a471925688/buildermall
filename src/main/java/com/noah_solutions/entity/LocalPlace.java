//package com.noah_solutions.entity;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * 2019 1 9  lijun
// * 地點信息(本地)
// */
//@Entity
//public class LocalPlace implements Serializable {
//    private static final long serialVersionUID = 1166874999877058362L;
//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false,columnDefinition = "BIGINT(10)  AUTO_INCREMENT  COMMENT '权限id'")
//    private String placeId;
//    @Column(columnDefinition = "VARCHAR(50) COMMENT '地名(中文)'")
//    private String  placeName;
//    @Column(columnDefinition = "VARCHAR(50) COMMENT '地名(英文)'")
//    private String  placeNameEng;
//    @Column(columnDefinition = "BIGINT(10) DEFAULT 0 NOT NULL COMMENT '(父級id)'")
//    private String parentId;
//    @Column(columnDefinition = "INT(2) DEFAULT 0  NOT NULL COMMENT '等级'")
//    private Integer  placeLevel;
//    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '排序'")
//    private Integer  placeOrder;
//    @Column(columnDefinition = "INT(2) DEFAULT 0 NOT NULL COMMENT '是否父节点(1.是;0.否)'")
//    private Integer  placeIsParent;
//    @Column(columnDefinition = "COMMENT '地址詳情'")
//    private String  placeDetails;
//    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
//    private String  placeCreateTime;
//    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
//    private String  placeUpdateTime;
//
//    public LocalPlace() {
//    }
//
//    public LocalPlace(String placeId, String placeName, String placeNameEng, String parentId, Integer placeLevel, Integer placeOrder, Integer placeIsParent, String placeDetails) {
//        this.placeId = placeId;
//        this.placeName = placeName;
//        this.placeNameEng = placeNameEng;
//        this.parentId = parentId;
//        this.placeLevel = placeLevel;
//        this.placeOrder = placeOrder;
//        this.placeIsParent = placeIsParent;
//        this.placeDetails = placeDetails;
//    }
//
//    public String getPlaceCreateTime() {
//        return placeCreateTime;
//    }
//
//    public void setPlaceCreateTime(String placeCreateTime) {
//        this.placeCreateTime = placeCreateTime;
//    }
//
//    public String getPlaceUpdateTime() {
//        return placeUpdateTime;
//    }
//
//    public void setPlaceUpdateTime(String placeUpdateTime) {
//        this.placeUpdateTime = placeUpdateTime;
//    }
//
//    public String getPlaceId() {
//        return placeId;
//    }
//
//    public void setPlaceId(String placeId) {
//        this.placeId = placeId;
//    }
//
//    public String getPlaceName() {
//        return placeName;
//    }
//
//    public void setPlaceName(String placeName) {
//        this.placeName = placeName;
//    }
//
//    public String getPlaceNameEng() {
//        return placeNameEng;
//    }
//
//    public void setPlaceNameEng(String placeNameEng) {
//        this.placeNameEng = placeNameEng;
//    }
//
//    public String getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(String parentId) {
//        this.parentId = parentId;
//    }
//
//    public Integer getPlaceLevel() {
//        return placeLevel;
//    }
//
//    public void setPlaceLevel(Integer placeLevel) {
//        this.placeLevel = placeLevel;
//    }
//
//    public Integer getPlaceOrder() {
//        return placeOrder;
//    }
//
//    public void setPlaceOrder(Integer placeOrder) {
//        this.placeOrder = placeOrder;
//    }
//
//    public Integer getPlaceIsParent() {
//        return placeIsParent;
//    }
//
//    public void setPlaceIsParent(Integer placeIsParent) {
//        this.placeIsParent = placeIsParent;
//    }
//
//    public String getPlaceDetails() {
//        return placeDetails;
//    }
//
//    public void setPlaceDetails(String placeDetails) {
//        this.placeDetails = placeDetails;
//    }
//}
