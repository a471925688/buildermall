package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class SupplierApply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT 'id'")
    private String id;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '填表人'")
    private String name;	//
    @Column(columnDefinition = "VARCHAR(60) COMMENT '填表人职务'")
    private String job;	//
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '填表日期'",updatable = false,insertable=false)
    private String  date;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '公司名称'")
    private String companyName;	//
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '成立日期'")
    private String  companyDate;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '签名处'")
    private String signature;	//
    @Column(columnDefinition = "VARCHAR(60) COMMENT '企业性质'")
    private String enterpriseNature;	//
    @Column(columnDefinition = "VARCHAR(60)   COMMENT '注册资本'")
    private String registeredCapital;		//
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '电话号码'")
    private String phone;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '邮政编码'")
    private String postalCode;
    @Column(columnDefinition = "VARCHAR(200)  COMMENT '公司网址'")
    private String companyWeb;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '传真号码'")
    private String fax;
    @Column(columnDefinition = "VARCHAR(255)  COMMENT '公司地址'")
    private String companyAddress;
    @Column(columnDefinition = "VARCHAR(255)  COMMENT '工厂地址'")
    private String factoryAddress;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '公司规模'")
    private String companySize;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '厂房面积'")
    private String plantArea;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '占地面积'")
    private String floorArea;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '年销售额'")
    private String annualSales;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '年出口额'")
    private String annualExportVolume;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '质量方针'")
    private String qualityPolicy;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '质量体系'")
    private String qualitySystem;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '质量描述'")
    private String qualityDescription;
    @Column(columnDefinition = "VARCHAR(500)  COMMENT '行业定位'")
    private String industryOrientation;
    @Column(columnDefinition = "VARCHAR(500)  COMMENT '企业优势'")
    private String companyAdvantages;
    @Column(columnDefinition = "VARCHAR(500)  COMMENT '特别要求'")
    private String specialRequirements;
    @Column(columnDefinition = "INT(2) DEFAULT 1 NOT NULL COMMENT '1.待审核.2.已通过.3.被拒绝'",insertable = false)
    private Integer state;
    @Column(columnDefinition = "INT(10)  COMMENT '(操作者id)'")
    private String adminId;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '操作时间'",updatable = false,insertable=false)
    private String createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  updateTime;
    //封面圖片
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="supplierapplyId",insertable = false,updatable = false)
    private Set<SupplierApplyProduct> supplierApplyProducts = new HashSet<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDate() {
        return companyDate;
    }

    public void setCompanyDate(String companyDate) {
        this.companyDate = companyDate;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEnterpriseNature() {
        return enterpriseNature;
    }

    public void setEnterpriseNature(String enterpriseNature) {
        this.enterpriseNature = enterpriseNature;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCompanyWeb() {
        return companyWeb;
    }

    public void setCompanyWeb(String companyWeb) {
        this.companyWeb = companyWeb;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getFactoryAddress() {
        return factoryAddress;
    }

    public void setFactoryAddress(String factoryAddress) {
        this.factoryAddress = factoryAddress;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getPlantArea() {
        return plantArea;
    }

    public void setPlantArea(String plantArea) {
        this.plantArea = plantArea;
    }

    public String getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    public String getAnnualSales() {
        return annualSales;
    }

    public void setAnnualSales(String annualSales) {
        this.annualSales = annualSales;
    }

    public String getAnnualExportVolume() {
        return annualExportVolume;
    }

    public void setAnnualExportVolume(String annualExportVolume) {
        this.annualExportVolume = annualExportVolume;
    }

    public String getQualityPolicy() {
        return qualityPolicy;
    }

    public void setQualityPolicy(String qualityPolicy) {
        this.qualityPolicy = qualityPolicy;
    }

    public String getQualitySystem() {
        return qualitySystem;
    }

    public void setQualitySystem(String qualitySystem) {
        this.qualitySystem = qualitySystem;
    }

    public String getQualityDescription() {
        return qualityDescription;
    }

    public void setQualityDescription(String qualityDescription) {
        this.qualityDescription = qualityDescription;
    }

    public String getIndustryOrientation() {
        return industryOrientation;
    }

    public void setIndustryOrientation(String industryOrientation) {
        this.industryOrientation = industryOrientation;
    }

    public String getCompanyAdvantages() {
        return companyAdvantages;
    }

    public void setCompanyAdvantages(String companyAdvantages) {
        this.companyAdvantages = companyAdvantages;
    }

    public String getSpecialRequirements() {
        return specialRequirements;
    }

    public void setSpecialRequirements(String specialRequirements) {
        this.specialRequirements = specialRequirements;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Set<SupplierApplyProduct> getSupplierApplyProducts() {
        return supplierApplyProducts;
    }

    public void setSupplierApplyProducts(Set<SupplierApplyProduct> supplierApplyProducts) {
        this.supplierApplyProducts = supplierApplyProducts;
    }
}
