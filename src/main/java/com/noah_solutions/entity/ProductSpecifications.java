package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 12 26 lijun
 * 其他選擇表
 */
@Entity
@Table(name = "product_specifications")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ProductSpecifications implements Serializable {
    private static final long serialVersionUID = 6263503166473913254L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '其他選擇id'")
    private String productSpecificationsId;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(內容(中文))'")
    private String productSpecificationsContent;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(內容(英文))'")
    private String productSpecificationsContentEng;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '(名稱（中文）)'")
    private String productSpecificationsName;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '(名稱（英文）)'")
    private String productSpecificationsNameEng;
    @Column(columnDefinition = "INT(10)  COMMENT '產品id(編號)'")
    private String productId;
//    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
//    private String  productSpecificationsCreateTime;
//    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
//    private String  productSpecificationsUpdateTime;


    public ProductSpecifications(String productSpecificationsId, String productSpecificationsContent, String productSpecificationsContentEng, String productSpecificationsName, String productSpecificationsNameEng, String productId) {
        this.productSpecificationsId = productSpecificationsId;
        this.productSpecificationsContent = productSpecificationsContent;
        this.productSpecificationsContentEng = productSpecificationsContentEng;
        this.productSpecificationsName = productSpecificationsName;
        this.productSpecificationsNameEng = productSpecificationsNameEng;
        this.productId = productId;
    }

    public ProductSpecifications() {
    }

    public String getProductSpecificationsContent() {
        return productSpecificationsContent;
    }

    public void setProductSpecificationsContent(String productSpecificationsContent) {
        this.productSpecificationsContent = productSpecificationsContent;
    }

    public String getProductSpecificationsContentEng() {
        return productSpecificationsContentEng;
    }

    public void setProductSpecificationsContentEng(String productSpecificationsContentEng) {
        this.productSpecificationsContentEng = productSpecificationsContentEng;
    }

    public String getProductSpecificationsName() {
        return productSpecificationsName;
    }

    public void setProductSpecificationsName(String productSpecificationsName) {
        this.productSpecificationsName = productSpecificationsName;
    }

    public String getProductSpecificationsNameEng() {
        return productSpecificationsNameEng;
    }

    public void setProductSpecificationsNameEng(String productSpecificationsNameEng) {
        this.productSpecificationsNameEng = productSpecificationsNameEng;
    }

    public String getProductSpecificationsId() {
        return productSpecificationsId;
    }

    public void setProductSpecificationsId(String productSpecificationsId) {
        this.productSpecificationsId = productSpecificationsId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
