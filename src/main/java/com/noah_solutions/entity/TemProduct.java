package com.noah_solutions.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class TemProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT   NOT NULL COMMENT '自增id'")
    private String temProductId;

    @Column(columnDefinition = "TEXT COMMENT '修改后的产品json'",nullable = false)
    private String temProductJson;

    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(修改的产品id)'")
    private String productId;

    @Column(columnDefinition = "INT(2) DEFAULT 1 NOT NULL COMMENT '1.待审核.2.已通过.3.被拒绝'",insertable = false)
    private Integer temProductState;

    @Column(columnDefinition = "INT(2) DEFAULT 1 NOT NULL COMMENT '1.待审核.2.已通过.3.被拒绝'",insertable = false)
    private String  temproductExplanation;

    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(操作者id)'")
    private String adminId;

    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '操作时间'",updatable = false,insertable=false)
    private String temProductCreateTime;

    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  temproductUpdateTime;

    @Transient
    private Product product;


    public TemProduct() {
    }

    public TemProduct(String temProductJson, String productId,String adminId) {
        this.temProductJson = temProductJson;
        this.productId = productId;
        this.adminId = adminId;
    }

    public String getTemProductId() {
        return temProductId;
    }

    public void setTemProductId(String temProductId) {
        this.temProductId = temProductId;
    }

    public String getTemProductJson() {
        return temProductJson;
    }

    public void setTemProductJson(String temProductJson) {
        this.temProductJson = temProductJson;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getTemProductState() {
        return temProductState;
    }

    public void setTemProductState(Integer temProductState) {
        this.temProductState = temProductState;
    }

    public String getTemproductExplanation() {
        return temproductExplanation;
    }

    public void setTemproductExplanation(String temproductExplanation) {
        this.temproductExplanation = temproductExplanation;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getTemProductCreateTime() {
        return temProductCreateTime;
    }

    public void setTemProductCreateTime(String temProductCreateTime) {
        this.temProductCreateTime = temProductCreateTime;
    }

    public String getTemproductUpdateTime() {
        return temproductUpdateTime;
    }

    public void setTemproductUpdateTime(String temproductUpdateTime) {
        this.temproductUpdateTime = temproductUpdateTime;
    }

    public Product getProduct() {
        if(!StringUtils.isEmpty(this.temProductJson)){
            product = JSONObject.parseObject(temProductJson, Product.class);
        }
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
