package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2018 12 19 lijun
 * 建材网商品类型表表实体类
 */
@Entity
@Table(name = "productType")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ProductType implements Serializable {
    private static final long serialVersionUID = 1166874999877058362L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '权限id'")
    private String productTypeId;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '产品类型中文名称'")
    private String  productTypeName;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '产品类型英文名称'")
    private String  productTypeNameEng;
    @Column(columnDefinition = "INT(10) DEFAULT 0 NOT NULL COMMENT '(父产品类型id)'")
    private String productTypeParentId;
    @Column(columnDefinition = "VARCHAR(50) DEFAULT '' COMMENT '(祖級類型id)'")
    private String productTypeParentsId;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '图片名称'")
    private String  productTypeIconName;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '产品类型描述'")
    private String  productTypeDescribe;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '产品类型描述'")
    private String  productTypeDescribeEng;
    @Column(columnDefinition = "INT(2) DEFAULT 0  NOT NULL COMMENT '产品类型等级'")
    private Integer  productTypeLevel;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '排序'")
    private Integer  productTypeOrder;
    @Column(columnDefinition = "INT(2) DEFAULT 0 NOT NULL COMMENT '是否父节点(1.是;0.否)'")
    private Integer  productTypeIsParent;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  productTypeCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  productTypeUpdateTime;

//
//    @OneToMany(mappedBy = "productTypemission",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
////    @JoinTable(
////            name = "role_productTypemission",
////            joinColumns = {@JoinColumn(name = "roleId")},
////            inverseJoinColumns = {@JoinColumn(name = "productTypeId")}
////    )
    @Transient
    private List<ProductType> childProductType=new ArrayList<>();
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    public String getProductTypeDescribeEng() {
        return productTypeDescribeEng;
    }

    public void setProductTypeDescribeEng(String productTypeDescribeEng) {
        this.productTypeDescribeEng = productTypeDescribeEng;
    }

    public List<ProductType> getChildProductType() {
        return childProductType;
    }

    public void setChildProductType(List<ProductType> childProductType) {
        this.childProductType = childProductType;
    }

    public String getProductTypeNameEng() {
        return productTypeNameEng;
    }

    public void setProductTypeNameEng(String productTypeNameEng) {
        this.productTypeNameEng = productTypeNameEng;
    }

    public Integer getProductTypeOrder() {
        return productTypeOrder;
    }

    public void setProductTypeOrder(Integer productTypeOrder) {
        this.productTypeOrder = productTypeOrder;
    }

    public void setProductTypeIsParent(Integer productTypeIsParent) {
        this.productTypeIsParent = productTypeIsParent;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public void setProductTypeParentId(String productTypeParentId) {
        this.productTypeParentId = productTypeParentId;
    }

    public String getProductTypeDescribe() {
        return productTypeDescribe;
    }

    public void setProductTypeDescribe(String productTypeDescribe) {
        this.productTypeDescribe = productTypeDescribe;
    }

    public Integer getProductTypeIsParent() {
        return productTypeIsParent;
    }

    public String getProductTypeCreateTime() {
        return productTypeCreateTime;
    }

    public void setProductTypeCreateTime(String productTypeCreateTime) {
        this.productTypeCreateTime = productTypeCreateTime;
    }

    public String getProductTypeUpdateTime() {
        return productTypeUpdateTime;
    }

    public void setProductTypeUpdateTime(String productTypeUpdateTime) {
        this.productTypeUpdateTime = productTypeUpdateTime;
    }

    public String getProductTypeIconName() {
        return productTypeIconName;
    }

    public String getProductTypeParentId() {
        return productTypeParentId;
    }

    public void setProductTypeLevel(Integer productTypeLevel) {
        this.productTypeLevel = productTypeLevel;
    }

    public void setProductTypeIconName(String productTypeIconName) {
        this.productTypeIconName = productTypeIconName;
    }

    public String getProductTypeParentsId() {
        return productTypeParentsId;
    }

    public void setProductTypeParentsId(String productTypeParentsId) {
        this.productTypeParentsId = productTypeParentsId;
    }

    public Integer getProductTypeLevel() {
        return productTypeLevel;
    }

}
