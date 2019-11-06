package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2019 2 20 lijun
 * 訂單商品參數信息
 */
@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class OrderProductParam implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "BIGINT  AUTO_INCREMENT NOT NULL COMMENT '其他選擇id'")
    private String orderProductParamId;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(內容(中文))'")
    private String orderProductParamContent;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(內容(英文))'")
    private String orderProductParamContentEng;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '(名稱（中文）)'")
    private String orderProductParamName;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '(名稱（英文）)'")
    private String orderProductParamEng;
    @Column(columnDefinition = "BIGINT  COMMENT '產品id(編號)'")
    private String orderProductId;

    public OrderProductParam() {
    }

    public OrderProductParam(String orderProductParamContent, String orderProductParamContentEng, String orderProductParamName, String orderProductParamEng, String orderProductId) {
        this.orderProductParamContent = orderProductParamContent;
        this.orderProductParamContentEng = orderProductParamContentEng;
        this.orderProductParamName = orderProductParamName;
        this.orderProductParamEng = orderProductParamEng;
        this.orderProductId = orderProductId;
    }



    public String getOrderProductParamId() {
        return orderProductParamId;
    }

    public void setOrderProductParamId(String orderProductParamId) {
        this.orderProductParamId = orderProductParamId;
    }

    public String getOrderProductParamContent() {
        return orderProductParamContent;
    }

    public void setOrderProductParamContent(String orderProductParamContent) {
        this.orderProductParamContent = orderProductParamContent;
    }

    public String getOrderProductParamContentEng() {
        return orderProductParamContentEng;
    }

    public void setOrderProductParamContentEng(String orderProductParamContentEng) {
        this.orderProductParamContentEng = orderProductParamContentEng;
    }

    public String getOrderProductParamName() {
        return orderProductParamName;
    }

    public void setOrderProductParamName(String orderProductParamName) {
        this.orderProductParamName = orderProductParamName;
    }

    public String getOrderProductParamEng() {
        return orderProductParamEng;
    }

    public void setOrderProductParamEng(String orderProductParamEng) {
        this.orderProductParamEng = orderProductParamEng;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }
}
