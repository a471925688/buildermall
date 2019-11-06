package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah_solutions.common.ProjectConfig;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 11 26 lijun
 * 建材网权限表实体类
 */
@Entity
@Table(name = "brand")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Brand implements Serializable {
    private static final long serialVersionUID = 2196769746650287851L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '品牌id'")
    private String brandId;
    @Column(columnDefinition = "VARCHAR(100) DEFAULT 0 COMMENT '(中文名稱)'")
    private String brandName;
    @Column(columnDefinition = "VARCHAR(100) DEFAULT 0 COMMENT '(英文名稱)'")
    private String brandNameEng;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '(圖標)'")
    private String brandLogoPath;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '供應商id'")
    private String  adminId;
    @Column(columnDefinition = "VARCHAR(60) NOT NULL COMMENT '國家'")
    private String brandCountry;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '描述'")
    private String  brandDescribe;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'")
    private String  brandCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  brandUpdateTime;




    public Brand() {
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandNameEng() {
        return brandNameEng;
    }

    public void setBrandNameEng(String brandNameEng) {
        this.brandNameEng = brandNameEng;
    }

    public String getBrandLogoPath() {
        return brandLogoPath;
    }

    public void setBrandLogoPath(String brandLogoPath) {
        this.brandLogoPath = brandLogoPath;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getBrandCountry() {
        return brandCountry;
    }

    public void setBrandCountry(String brandCountry) {
        this.brandCountry = brandCountry;
    }

    public String getBrandDescribe() {
        return brandDescribe;
    }

    public void setBrandDescribe(String brandDescribe) {
        this.brandDescribe = brandDescribe;
    }

    public String getBrandCreateTime() {
        return brandCreateTime;
    }

    public void setBrandCreateTime(String brandCreateTime) {
        this.brandCreateTime = brandCreateTime;
    }

    public String getBrandUpdateTime() {
        return brandUpdateTime;
    }

    public void setBrandUpdateTime(String brandUpdateTime) {
        this.brandUpdateTime = brandUpdateTime;
    }
}
