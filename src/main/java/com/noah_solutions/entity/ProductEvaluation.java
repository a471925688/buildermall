package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 2018 11 26 lijun
 * 建材网产品评价表实体类
 */
@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ProductEvaluation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT 'id'")
    private String productEvaluationId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '產品id(編號)'")
    private String productId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '訂單id'")
    private String orderId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '用戶id'")
    private String userId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '訂單產品id'")
    private String orderProductId;
    @Column(columnDefinition = "INT(10) DEFAULT 0  COMMENT '參評評分'")
    private String productLevel;
    @Column(columnDefinition = "INT(10) DEFAULT 0  COMMENT '賣家評分'")
    private String supplierLevel;
    @Column(columnDefinition = "INT(10) DEFAULT 0  COMMENT '物流評分'")
    private String logisticsLevel;
    @Column(columnDefinition = "INT(4) DEFAULT 0  COMMENT '是否追评'")
    private Boolean isReview;
//    @Column(columnDefinition = "TEXT COMMENT '產品配置描述'")
//    private String  productConfigDescribe;
//    @Column(columnDefinition = "TEXT COMMENT '產品配置描述(英文)'")
//    private String  productConfigDescribeEng;
    @Column(columnDefinition = "TEXT COMMENT '產品評價描述'")
    private String  productEvaluationDescribe;

    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '評價时间'",insertable=false)
    private String  productEvaluationCreateTime;

    //評價圖片
    @Where(clause="image_group_type = 3")
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="imageGroupId",insertable = false,updatable = false)
    @OrderBy("imageOrder ASC")
    private Set<TbImage> detailsImgNames = new HashSet<>();

    @Transient
    private String userRealName;
    @Transient
    private String userLoginName;

    @Transient
    private List<ProductEvaluation> productEvaluation;//追评

    public ProductEvaluation(String userRealName,String userLoginName,ProductEvaluation productEvaluation) {
        this.userRealName = userRealName;
        this.userLoginName = userLoginName;
        this.userId = productEvaluation.getUserId();
        this.orderProductId = productEvaluation.getOrderProductId();
        this.productLevel = productEvaluation.productLevel;
        this.supplierLevel = productEvaluation.productLevel;
        this.logisticsLevel = productEvaluation.logisticsLevel;
        this.productEvaluationDescribe = productEvaluation.productEvaluationDescribe;
        this.detailsImgNames = productEvaluation.detailsImgNames;
        this.productEvaluationCreateTime = productEvaluation.productEvaluationCreateTime;
    }

    public ProductEvaluation() {

    }

    public ProductEvaluation(String productId, String orderId, String userId) {
        this.productId = productId;
        this.orderId = orderId;
        this.userId = userId;
    }


    public Boolean getIsReview() {
        return isReview;
    }

    public void setIsReview(Boolean isReview) {
        this.isReview = isReview;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public List<ProductEvaluation> getProductEvaluation() {
        return productEvaluation;
    }

    public void setProductEvaluation(List<ProductEvaluation> productEvaluation) {
        this.productEvaluation = productEvaluation;
    }

    public String getProductEvaluationId() {
        return productEvaluationId;
    }

    public void setProductEvaluationId(String productEvaluationId) {
        this.productEvaluationId = productEvaluationId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(String productLevel) {
        this.productLevel = productLevel;
    }

    public String getSupplierLevel() {
        return supplierLevel;
    }

    public void setSupplierLevel(String supplierLevel) {
        this.supplierLevel = supplierLevel;
    }

    public String getLogisticsLevel() {
        return logisticsLevel;
    }

    public void setLogisticsLevel(String logisticsLevel) {
        this.logisticsLevel = logisticsLevel;
    }

    public String getProductEvaluationDescribe() {
        return productEvaluationDescribe;
    }

    public void setProductEvaluationDescribe(String productEvaluationDescribe) {
        this.productEvaluationDescribe = productEvaluationDescribe;
    }

    public String getProductEvaluationCreateTime() {
        return productEvaluationCreateTime;
    }

    public void setProductEvaluationCreateTime(String productEvaluationCreateTime) {
        this.productEvaluationCreateTime = productEvaluationCreateTime;
    }

    public Set<TbImage> getDetailsImgNames() {
        return detailsImgNames;
    }

    public void setDetailsImgNames(Set<TbImage> detailsImgNames) {
        this.detailsImgNames = detailsImgNames;
    }
}
