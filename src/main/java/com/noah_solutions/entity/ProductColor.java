//package com.noah_solutions.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.io.Serializable;
//
///**
// * 2018 12 24
// * 商品與顏色關係表
// */
//@Entity
//@Table(name = "product_color")
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//public class ProductColor implements Serializable {
//    private static final long serialVersionUID = -1709055372208820909L;
//    @Id
//    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '產品id'")
//    private String productId;
//    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '顏色id'")
//    private String colorId;
//    public String getProductId() {
//        return productId;
//    }
//
//    public void setProductId(String productId) {
//        this.productId = productId;
//    }
//
//    public String getColorId() {
//        return colorId;
//    }
//
//    public void setColorId(String colorId) {
//        this.colorId = colorId;
//    }
//}
