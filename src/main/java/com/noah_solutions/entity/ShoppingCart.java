package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah_solutions.common.ProjectConfig;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = -8743255344871088829L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '购物车id'")
    private String shopCartId;
    @Column(columnDefinition = "VARCHAR(100) DEFAULT '购物车' NULL COMMENT '(购物车名称)'")
    private String shopCartName;
    @Column(columnDefinition = "INT(4) DEFAULT 1  COMMENT '购物车类型（1.购物车,2.项目）'")
    private Integer  shopCartType;
    @Column(columnDefinition = "varchar(80)  COMMENT '供应商名称'")
    private String  adminName;
    @Column(columnDefinition = "VARCHAR(300) COMMENT '产品名称'")
    private String  productName;
    @Column(columnDefinition = "VARCHAR(300) COMMENT '产品名称'")
    private String  productNameEng;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '縮略圖'")
    private String productThumbnailPhonePath;
    @Column(columnDefinition = "VARCHAR(250) COMMENT '产品图片'")
    private String  productPhonePath;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '发货地区'")
    private String productDeliveryArea;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '收货地区'")
    private String productReceivingArea;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '(是否包郵 1,是,0.否)'")
    private Boolean productIsFreight;
    @Column(columnDefinition = "INT   COMMENT '产品配置id'")
    private String  productConfigId;
    @Column(columnDefinition = "VARCHAR(255)   COMMENT '產品類型id'")
    private String  productTypeId;
    @Column(columnDefinition = "INT   COMMENT '产品数量'")
    private String  productNum;
    @Column(columnDefinition = "VARCHAR(20) COMMENT '产品單位'")
    private String  productUnit;
    @Column(columnDefinition = "VARCHAR(20) COMMENT '产品單位(英文)'")
    private String  productUnitEng;
    @Column(nullable = false,columnDefinition = "INT   COMMENT '產品id'")
    private String  productId;
    @Column(nullable = false,columnDefinition = "INT   COMMENT '用户id'")
    private String  userId;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  shopCartCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  shopCartUpdateTime;


    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name="productConfigId",insertable = false,updatable = false)
    private ProductConfig productConfigs;

    @Transient
    private Map<String,Integer> freightCharges = new HashMap<>();//保存所有運費信息
    @Transient
    private String freight;//保存當前運費的key值
    @Transient
    private String freightCNY;//保存當前運費的key值
    @Transient
    private String freightUSD;//保存當前運費的key值



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingCart)) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(shopCartId, that.shopCartId) &&
                Objects.equals(shopCartName, that.shopCartName) &&
                Objects.equals(shopCartType, that.shopCartType) &&
                Objects.equals(adminName, that.adminName) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(productNameEng, that.productNameEng) &&
                Objects.equals(productPhonePath, that.productPhonePath) &&
                Objects.equals(productConfigId, that.productConfigId) &&
                Objects.equals(productNum, that.productNum) &&
                Objects.equals(productUnit, that.productUnit) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(shopCartCreateTime, that.shopCartCreateTime) &&
                Objects.equals(shopCartUpdateTime, that.shopCartUpdateTime) &&
                Objects.equals(productConfigs, that.productConfigs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopCartId, shopCartName, shopCartType, adminName, productName, productNameEng, productPhonePath, productConfigId, productNum, productUnit, userId, shopCartCreateTime, shopCartUpdateTime, productConfigs);
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Boolean getProductIsFreight() {
        return productIsFreight;
    }

    public String getProductUnitEng() {
        return productUnitEng;
    }

    public void setProductUnitEng(String productUnitEng) {
        this.productUnitEng = productUnitEng;
    }

    public void setProductIsFreight(Boolean productIsFreight) {
        this.productIsFreight = productIsFreight;
    }

    public String getProductThumbnailPhonePath() {
        return productThumbnailPhonePath;
    }

    public void setProductThumbnailPhonePath(String productThumbnailPhonePath) {
        this.productThumbnailPhonePath = productThumbnailPhonePath;
    }

    public String getFreightUSD() {
        if(!org.apache.commons.lang.StringUtils.isNumeric(freight)){
            return freight;
        }
        return StringUtils.isEmpty(freight)?"0":Double.valueOf(Math.round(Double.valueOf(freight) * ProjectConfig.TodayRateUSD))+"";
    }

    public void setFreightUSD(String freightUSD) {
        this.freightUSD = freightUSD;
    }

    public String getFreightCNY() {
        if(!org.apache.commons.lang.StringUtils.isNumeric(freight)){
            return freight;
        }
        return StringUtils.isEmpty(freight)?"0":Double.valueOf(Math.round(Double.valueOf(freight) * ProjectConfig.TodayRate))+"";
    }

    public void setFreightCNY(String freightCNY) {
        this.freightCNY = freightCNY;
    }

    public String getProductDeliveryArea() {
        return productDeliveryArea;
    }

    public void setProductDeliveryArea(String productDeliveryArea) {
        this.productDeliveryArea = productDeliveryArea;
    }

    public Map<String, Integer> getFreightCharges() {
        return freightCharges;
    }

    public void setFreightCharges(Map<String, Integer> freightCharges) {
        this.freightCharges = freightCharges;
    }

    public String getProductReceivingArea() {
        return productReceivingArea;
    }

    public void setProductReceivingArea(String productReceivingArea) {
        this.productReceivingArea = productReceivingArea;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getShopCartName() {
        return shopCartName;
    }

    public void setShopCartName(String shopCartName) {
        this.shopCartName = shopCartName;
    }

    public Integer getShopCartType() {
        return shopCartType;
    }

    public void setShopCartType(Integer shopCartType) {
        this.shopCartType = shopCartType;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNameEng() {
        return productNameEng;
    }

    public void setProductNameEng(String productNameEng) {
        this.productNameEng = productNameEng;
    }

    public String getProductPhonePath() {
        return productPhonePath;
    }

    public void setProductPhonePath(String productPhonePath) {
        this.productPhonePath = productPhonePath;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getShopCartCreateTime() {
        return shopCartCreateTime;
    }

    public void setShopCartCreateTime(String shopCartCreateTime) {
        this.shopCartCreateTime = shopCartCreateTime;
    }

    public String getShopCartUpdateTime() {
        return shopCartUpdateTime;
    }

    public void setShopCartUpdateTime(String shopCartUpdateTime) {
        this.shopCartUpdateTime = shopCartUpdateTime;
    }

    public String getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(String shopCartId) {
        this.shopCartId = shopCartId;
    }

    public ProductConfig getProductConfigs() {
        return productConfigs;
    }

    public void setProductConfigs(ProductConfig productConfigs) {
        this.productConfigs = productConfigs;
    }

    public String getProductConfigId() {
        return productConfigId;
    }

    public void setProductConfigId(String productConfigId) {
        this.productConfigId = productConfigId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
