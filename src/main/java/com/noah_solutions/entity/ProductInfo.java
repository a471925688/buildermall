//package com.noah_solutions.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * 2018 12 19 lijun
// * 建材网商品类型表表实体类
// */
//@Entity
//@Table(name = "product")
//@Data
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//public class ProductInfo extends Product implements Serializable {
//    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '(品牌id)'")
//    private String brandId;
//    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(類型id)'")
//    private String productTypeId;
//
//
//    public String getBrandId() {
//        return brandId;
//    }
//
//    public void setBrandId(String brandId) {
//        this.brandId = brandId;
//    }
//
//    public String getProductTypeId() {
//        return productTypeId;
//    }
//
//    public void setProductTypeId(String productTypeId) {
//        this.productTypeId = productTypeId;
//    }
//
////    public String getAdminId() {
////        return adminId;
////    }
////
////    public void setAdminId(String adminId) {
////        this.adminId = adminId;
////    }
//}
