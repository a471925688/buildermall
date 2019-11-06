package com.noah_solutions.entity;

import com.noah_solutions.common.ProjectConfig;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
//@Table(name = "product_config")
@Data
public class ProductConfig implements Serializable {
    private static final long serialVersionUID = -3779540136123933697L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '配置id'")
    private String productConfigId;
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '(價格)'")
    private Double productConfigPrice;
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '(重量(kg))'")
    private String productConfigWeight;
    @Column(columnDefinition = "DOUBLE DEFAULT 0 COMMENT '(折扣價格)'")
    private Double productConfigDiscountPrice;
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '(庫存)'")
    private String productConfigStock;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '图片地址'")
    private String productConfigUrl;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '產品id(編號)'")
    private String productId;
    @Column(columnDefinition = "INT(10)  COMMENT '(大小id)'")
    private String productSizeId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_config_specifications",
            joinColumns = {@JoinColumn(name = "productConfigId")},
            inverseJoinColumns = {@JoinColumn(name = "productSpecificationsId")}
    )
    @OrderBy("productSpecificationsId DESC ")
    private Set<ProductSpecifications> productSpecificationsSet;

    //大小信息
    @OneToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="productSizeId",insertable = false,updatable = false)
    private ProductSize productSize;


    @Transient
    private OrderProduct orderProduct;
    @Transient
    private Double productConfigDiscountPriceCNY;
    @Transient
    private Double productConfigDiscountPriceUSD;

    public ProductConfig() {
    }

    public ProductConfig(Double productConfigPrice, Double productConfigDiscountPrice, String productConfigStock, String productId, String productSizeId, Set<ProductSpecifications> productSpecificationsSet) {
        this.productConfigId =productConfigId;
        this.productConfigPrice = productConfigPrice;
        this.productConfigDiscountPrice = productConfigDiscountPrice;
        this.productConfigStock = productConfigStock;
        this.productId = productId;
        this.productSizeId = productSizeId;
        this.productSpecificationsSet = productSpecificationsSet;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public String getProductConfigWeight() {
        return productConfigWeight;
    }

    public void setProductConfigWeight(String productConfigWeight) {
        this.productConfigWeight = productConfigWeight;
    }

    public Set<ProductSpecifications> getProductSpecificationsSet() {
        return productSpecificationsSet;
    }

    public void setProductSpecificationsSet(Set<ProductSpecifications> productSpecificationsSet) {
        this.productSpecificationsSet = productSpecificationsSet;
    }

    public String getProductConfigUrl() {
        return productConfigUrl;
    }

    public void setProductConfigUrl(String productConfigUrl) {
        this.productConfigUrl = productConfigUrl;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }


    public Double getProductConfigDiscountPriceUSD() {
        return Double.valueOf(Math.round(productConfigDiscountPrice* ProjectConfig.TodayRateUSD));
    }

    public void setProductConfigDiscountPriceUSD(Double productConfigDiscountPriceUSD) {
        this.productConfigDiscountPriceUSD = productConfigDiscountPriceUSD;
    }

    public Double getProductConfigDiscountPriceCNY() {
        return Double.valueOf(Math.round(productConfigDiscountPrice* ProjectConfig.TodayRate));
    }

    public void setProductConfigDiscountPriceCNY(Double productConfigDiscountPriceCNY) {
        this.productConfigDiscountPriceCNY = productConfigDiscountPriceCNY;
    }

//    //顏色信息
//    @OneToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name="colorId",insertable = false,updatable = false)
//    private Color color;

//    //選擇1信息
//    @OneToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name="productSpecificationsId1",insertable = false,updatable = false)
//    private ProductSpecifications ProductSpecifications1;
//    //選擇2信息
//    @OneToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name="productSpecificationsId2",insertable = false,updatable = false)
//    private ProductSpecifications ProductSpecifications2;
//    //選擇3信息
//    @OneToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name="productSpecificationsId3",insertable = false,updatable = false)
//    private ProductSpecifications ProductSpecifications3;
//    public String getColorId() {
//        return colorId;
//    }
//
//    public void setColorId(String colorId) {
//        this.colorId = colorId;
//    }

    public String getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(String productSizeId) {
        this.productSizeId = productSizeId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductConfigId() {
        return productConfigId;
    }

    public void setProductConfigId(String productConfigId) {
        this.productConfigId = productConfigId;
    }

    public Double getProductConfigPrice() {
        return productConfigPrice;
    }

    public void setProductConfigPrice(Double productConfigPrice) {
        this.productConfigPrice = productConfigPrice;
    }

    public Double getProductConfigDiscountPrice() {
        return productConfigDiscountPrice;
    }

    public void setProductConfigDiscountPrice(Double productConfigDiscountPrice) {
        this.productConfigDiscountPrice = productConfigDiscountPrice;
    }

    public String getProductConfigStock() {
        return productConfigStock;
    }

    public void setProductConfigStock(String productConfigStock) {
        this.productConfigStock = productConfigStock;
    }

    public String getProductId() {
        return productId;
    }


//    public String getProductConfigCreateTime() {
//        return productConfigCreateTime;
//    }

//    public void setProductConfigCreateTime(String productConfigCreateTime) {
//        this.productConfigCreateTime = productConfigCreateTime;
//    }
//
//    public String getProductConfigUpdateTime() {
//        return productConfigUpdateTime;
//    }
//
//    public void setProductConfigUpdateTime(String productConfigUpdateTime) {
//        this.productConfigUpdateTime = productConfigUpdateTime;
//    }
}
