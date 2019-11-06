package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 11 26 lijun
 * 建材网優惠碼实体类
 */
@Entity
@Table(name = "discountCode")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class DiscountCode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '優惠碼id'")
    private String discountCodeId;
    @Column(columnDefinition = "VARCHAR(40)  COMMENT '優惠碼'")
    private String  discountCodeNo;
    @Column(columnDefinition = "INT(4)  COMMENT '優惠方式(1.折扣方式,2.減免劵)'",nullable = false)
    private Integer  discountCodeMode;
    @Column(columnDefinition = "INT  COMMENT '有效次數(-1永久有效)'",nullable = false)
    private Integer  discountCodeFiniteNum;
    @Column(columnDefinition = "VARCHAR(40)  COMMENT '優惠內容'")
    private String  discountCodeContent;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(創建者id)'")
    private String adminId;
    @Column(columnDefinition = "INT(2)  DEFAULT 0 COMMENT '是否禁用(1,是.0，否)'",insertable = false)
    private Boolean  discountCodeIsProhibit;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  discountCodeCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  discountCodeUpdateTime;


    ////////////////////////////////////不映射到数据库中的属性 start ///////////////////////////////////////////////////////

    ////////////////////////////////////不映射到数据库中的属性 end///////////////////////////////////////////////////////


    public String getDiscountCodeId() {
        return discountCodeId;
    }

    public void setDiscountCodeId(String discountCodeId) {
        this.discountCodeId = discountCodeId;
    }

    public String getDiscountCodeNo() {
        return discountCodeNo;
    }

    public void setDiscountCodeNo(String discountCodeNo) {
        this.discountCodeNo = discountCodeNo;
    }

    public Integer getDiscountCodeMode() {
        return discountCodeMode;
    }

    public void setDiscountCodeMode(Integer discountCodeMode) {
        this.discountCodeMode = discountCodeMode;
    }

    public Integer getDiscountCodeFiniteNum() {
        return discountCodeFiniteNum;
    }

    public void setDiscountCodeFiniteNum(Integer discountCodeFiniteNum) {
        this.discountCodeFiniteNum = discountCodeFiniteNum;
    }

    public String getDiscountCodeContent() {
        return discountCodeContent;
    }

    public void setDiscountCodeContent(String discountCodeContent) {
        this.discountCodeContent = discountCodeContent;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Boolean getDiscountCodeIsProhibit() {
        return discountCodeIsProhibit;
    }

    public void setDiscountCodeIsProhibit(Boolean discountCodeIsProhibit) {
        this.discountCodeIsProhibit = discountCodeIsProhibit;
    }

    public String getDiscountCodeCreateTime() {
        return discountCodeCreateTime;
    }

    public void setDiscountCodeCreateTime(String discountCodeCreateTime) {
        this.discountCodeCreateTime = discountCodeCreateTime;
    }

    public String getDiscountCodeUpdateTime() {
        return discountCodeUpdateTime;
    }

    public void setDiscountCodeUpdateTime(String discountCodeUpdateTime) {
        this.discountCodeUpdateTime = discountCodeUpdateTime;
    }
}
