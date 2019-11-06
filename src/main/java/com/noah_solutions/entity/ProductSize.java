package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 12 24 lijun
 * 產品大小表
 */
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "productSizeType")
@Table(name = "product_size")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ProductSize implements Serializable {
    private static final long serialVersionUID = -6174166046229731517L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '大小id(編號)'")
    private String productSizeId;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(大小內容(中文))'")
    private String productSizeContent;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(大小內容(英文))'")
    private String productSizeContentEng;
//
//    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(寬（單位:mm）)'")
//    private String productSizeWidth;
//    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(高（單位:mm）)'")
//    private String productSizeHeight;
//    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(深（單位:mm）)'")
//    private String productSizeLong;

//    @Column(columnDefinition = "DOUBLE   COMMENT '(重量)'")
//    private Double productSizeWeight;
    @Column(columnDefinition = "DOUBLE   COMMENT '(體積m3)'")
    private Double productSizeVolume;
    @Column(columnDefinition = "INT(10)    COMMENT '產品id(編號)'")
    private String productId;
//    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
//    private String  productSizeCreateTime;
//    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
//    private String  productSizeUpdateTime;


    public ProductSize(String productSizeId,String productSizeContent, String productSizeContentEng,String productId) {
        this.productSizeId = productSizeId;
        this.productSizeContent = productSizeContent;
        this.productSizeContentEng = productSizeContentEng;
        this.productId = productId;
    }

    public ProductSize() {
    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getProductSizeContentEng() {
        return productSizeContentEng;
    }

    public void setProductSizeContentEng(String productSizeContentEng) {
        this.productSizeContentEng = productSizeContentEng;
    }

    public Double getProductSizeVolume() {
        return productSizeVolume;
    }

    public void setProductSizeVolume(Double productSizeVolume) {
        this.productSizeVolume = productSizeVolume;
    }

    public String getProductSizeContent() {
        return productSizeContent;
    }

    public void setProductSizeContent(String productSizeContent) {
        this.productSizeContent = productSizeContent;
    }


    public String getProductId() {
        return productId;
    }
}
