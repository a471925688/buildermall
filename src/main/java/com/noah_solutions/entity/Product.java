package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.common.ProjectConfig;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 2018 12 19 lijun
 * 建材网商品类型表表实体类
 */
@Entity
@Table(name = "product")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Product implements Serializable {
    private static final long serialVersionUID = -4008109807672582508L;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT   NOT NULL COMMENT '產品id'")
    private String productId;
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 0  NOT NULL COMMENT '產品單位(中文)'")
    private String  productUnit;
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 0  NOT NULL COMMENT '產品單位(英文)'")
    private String  productUnitEng;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '產品中文名称'")
    private String  productTitle;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '產品英文名称'")
    private String  productTitleEng;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '產品材質中文'")
    private String  productMaterial;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '產品材質英文'")
    private String  productMaterialEng;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '產品特點中文'")
    private String  productFeatures;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '產品特點英文'")
    private String  productFeaturesEng;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '发货地区'")
    private String productDeliveryArea;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '收货地区'")
    private String productReceivingArea;
    @Column(columnDefinition = "DOUBLE COMMENT '範圍最低價格'")
    private Double  productPriceStart;
    @Column(columnDefinition = "DOUBLE COMMENT '範圍最高價格'")
    private Double  productPriceEnd;
    @Column(columnDefinition = "DOUBLE COMMENT '範圍最低折扣價格'")
    private Double  productDisPriceStart;
    @Column(columnDefinition = "DOUBLE COMMENT '範圍最高折扣價格'")
    private Double  productDisPriceEnd;
    @Column(columnDefinition = "INT COMMENT '最低訂購數'")
    private String   productMinNumber;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '產品中文描述'")
    private String  productDescribe;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '產品英文描述'")
    private String  productDescribeEng;
    @Column(columnDefinition = "TEXT COMMENT '產品中文描述'")
    private String  productContent;
    @Column(columnDefinition = "TEXT COMMENT '產品英文描述'")
    private String  productContentEng;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '促銷方式'")
    private String discountTypes;
    @Column(columnDefinition = "INT(2) DEFAULT 1 NOT NULL COMMENT '1.上架.0.下架.'")
    private Boolean productState;
    @Column(columnDefinition = "INT(10) DEFAULT 1 NOT NULL COMMENT '售出總數'",insertable = false)
    private String productSelloutNum;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  productCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  productUpdateTime;

    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '(是否打折1,是,2.否)'")
    private Boolean productIsDiscount;

    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '(是否包郵 1,是,0.否)'")
    private Boolean productIsFreight;

    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(產品預計送達天數)'")
    private Integer   productServiceDate;


    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(供應商id)'")
    private String adminId;
    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '(品牌id)'")
    private String brandId;
    @Column(columnDefinition = "VARCHAR(100)  NOT NULL COMMENT '(類型id)'")
    private String productTypeId;

    //品牌信息
    @OneToOne(fetch=FetchType.LAZY)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="brandId",insertable = false,updatable = false)
    private Brand brand;
//    //產品類型信息
//    @OneToOne(fetch=FetchType.LAZY)
//    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//    @JoinColumn(name="productTypeId",insertable = false,updatable = false)
//    private ProductType productType;
    //供應商信息
    @OneToOne(fetch=FetchType.LAZY)
    @NotFound(action= NotFoundAction.IGNORE)
    @JsonIgnoreProperties(value={"role","login","hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="adminId",insertable = false,updatable = false)
//    @Where(clause = "admin_is_prohibit=1")
    private Admin admin;
    //產品大小信息
    @NotFound(action=NotFoundAction.IGNORE)
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="productId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @OrderBy("productSizeId asc ")
    private Set<ProductSize> productSizes = new HashSet<>();

    //其他選擇信息
    @NotFound
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="productId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @OrderBy("productSpecificationsName DESC,productSpecificationsId ASC ")
    private Set<ProductSpecifications> productSpecificationss = new HashSet<>();

    //產品配置信息
    @NotFound
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="productId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @OrderBy("productConfigDiscountPrice asc ")
    private Set<ProductConfig> productConfigs = new HashSet<>();

    //封面圖片
    @Where(clause="image_type = 1")
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="imageGroupId")
    private Set<TbImage> coverImgs = new HashSet<>();
    //詳情圖片
    @Where(clause="image_group_type = 1")
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="imageGroupId")
    @OrderBy("imageOrder ASC")
    private Set<TbImage> detailsImgNames = new HashSet<>();

    //影片
    @Where(clause="file_group_type = 1 and file_type = 2")
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="fileGroupId")
    private Set<TbFile> productVideo = new HashSet<>();

    //目录
    @Where(clause="file_group_type = 1 and file_type = 3")
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="fileGroupId")
    private Set<TbFile> productContentsFile = new HashSet<>();

    //规格
    @Where(clause="file_group_type = 1 and file_type = 4")
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="fileGroupId")
    private Set<TbFile> productSpecificationsFile = new HashSet<>();
//    //產品顏色信息
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "product_color",
//            joinColumns = {@JoinColumn(name = "productId")},
//            inverseJoinColumns = {@JoinColumn(name = "colorId")}
//    )
//    private Set<Color> colors;

    @Transient
    private Double productDisPriceStartCNY;
    @Transient
    private Double productDisPriceStartUSD;
    @Transient
    private Double productDisPriceENDCNY;
    @Transient
    private Double productDisPriceENDUSD;
    @Transient
    private List<ProductType> productTypes;
    @Transient
    private String productTypeName="";
    @Transient
    private String productTypeNameEng="";

    public Product() {
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    public String getProductMinNumber() {
        return productMinNumber;
    }

    public void setProductMinNumber(String productMinNumber) {
        this.productMinNumber = productMinNumber;
    }

    public String getDiscountTypes() {
        return discountTypes;
    }

    public void setDiscountTypes(String discountTypes) {
        this.discountTypes = discountTypes;
    }

    public Double getProductDisPriceStartCNY() {
        return Double.valueOf(Math.round(productDisPriceStart* ProjectConfig.TodayRate));
    }

    public void setProductDisPriceStartCNY(Double productDisPriceStartCNY) {
        this.productDisPriceStartCNY = productDisPriceStartCNY;
    }

    public Double getProductDisPriceENDUSD() {
        if(productDisPriceEnd!=null){
            productDisPriceENDUSD = Double.valueOf(Math.round(productDisPriceEnd* ProjectConfig.TodayRateUSD));
        }
        return productDisPriceENDUSD;
    }

    public void setProductDisPriceENDUSD(Double productDisPriceENDUSD) {
        this.productDisPriceENDUSD = productDisPriceENDUSD;
    }

    public Double getProductDisPriceENDCNY() {
        if(productDisPriceEnd!=null){
            productDisPriceENDCNY = Double.valueOf(Math.round(productDisPriceEnd* ProjectConfig.TodayRate));
        }
        return productDisPriceENDCNY;
    }

    public Double getProductDisPriceStartUSD() {
        if(productDisPriceStart!=null){
            productDisPriceStartUSD = Double.valueOf(Math.round(productDisPriceStart* ProjectConfig.TodayRateUSD));
        }
        return productDisPriceStartUSD;
    }

    public void setProductDisPriceStartUSD(Double productDisPriceStartUSD) {

        this.productDisPriceStartUSD = productDisPriceStartUSD;
    }

    public void setProductDisPriceENDCNY(Double productDisPriceENDCNY) {
        this.productDisPriceENDCNY = productDisPriceENDCNY;
    }

    public Integer getProductServiceDate() {
        return productServiceDate;
    }

    public void setProductServiceDate(Integer productServiceDate) {
        this.productServiceDate = productServiceDate;
    }

    public String getProductContent() {
        return productContent;
    }

    public void setProductContent(String productContent) {
        this.productContent = productContent;
    }

    public String getProductContentEng() {
        return productContentEng;
    }

    public void setProductContentEng(String productContentEng) {
        this.productContentEng = productContentEng;
    }

    public Boolean getProductIsDiscount() {
        return productIsDiscount;
    }

    public void setProductIsDiscount(Boolean productIsDiscount) {
        this.productIsDiscount = productIsDiscount;
    }

    public Boolean getProductIsFreight() {
        return productIsFreight;
    }

    public void setProductIsFreight(Boolean productIsFreight) {
        this.productIsFreight = productIsFreight;
    }

    public String getProductDeliveryArea() {
        return productDeliveryArea;
    }

    public void setProductDeliveryArea(String productDeliveryArea) {
        this.productDeliveryArea = productDeliveryArea;
    }

    public String getProductReceivingArea() {
        return productReceivingArea;
    }

    public void setProductReceivingArea(String productReceivingArea) {
        this.productReceivingArea = productReceivingArea;
    }

    public Set<TbFile> getProductVideo() {
        return productVideo;
    }

    public void setProductVideo(Set<TbFile> productVideo) {
        this.productVideo = productVideo;
    }

    public Set<ProductConfig> getProductConfigs() {
        return productConfigs;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductUnitEng() {
        return productUnitEng;
    }

    public void setProductUnitEng(String productUnitEng) {
        this.productUnitEng = productUnitEng;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductTitleEng() {
        return productTitleEng;
    }

    public void setProductTitleEng(String productTitleEng) {
        this.productTitleEng = productTitleEng;
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = productMaterial;
    }

    public String getProductMaterialEng() {
        return productMaterialEng;
    }

    public void setProductMaterialEng(String productMaterialEng) {
        this.productMaterialEng = productMaterialEng;
    }

    public String getProductFeatures() {
        return productFeatures;
    }

    public void setProductFeatures(String productFeatures) {
        this.productFeatures = productFeatures;
    }

    public String getProductFeaturesEng() {
        return productFeaturesEng;
    }

    public void setProductFeaturesEng(String productFeaturesEng) {
        this.productFeaturesEng = productFeaturesEng;
    }

    public Double getProductPriceStart() {
        return productPriceStart;
    }

    public void setProductPriceStart(Double productPriceStart) {
        this.productPriceStart = productPriceStart;
    }

    public Double getProductPriceEnd() {
        return productPriceEnd;
    }

    public void setProductPriceEnd(Double productPriceEnd) {
        this.productPriceEnd = productPriceEnd;
    }

    public Double getProductDisPriceStart() {
        return productDisPriceStart;
    }

    public void setProductDisPriceStart(Double productDisPriceStart) {
        this.productDisPriceStart = productDisPriceStart;
    }

    public Double getProductDisPriceEnd() {
        return productDisPriceEnd;
    }

    public void setProductDisPriceEnd(Double productDisPriceEnd) {
        this.productDisPriceEnd = productDisPriceEnd;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public String getProductDescribeEng() {
        return productDescribeEng;
    }

    public void setProductDescribeEng(String productDescribeEng) {
        this.productDescribeEng = productDescribeEng;
    }

    public Boolean getProductState() {
        return productState;
    }

    public void setProductState(Boolean productState) {
        this.productState = productState;
    }

    public String getProductSelloutNum() {
        return productSelloutNum;
    }

    public void setProductSelloutNum(String productSelloutNum) {
        this.productSelloutNum = productSelloutNum;
    }

    public String getProductCreateTime() {
        return productCreateTime;
    }

    public void setProductCreateTime(String productCreateTime) {
        this.productCreateTime = productCreateTime;
    }

    public String getProductUpdateTime() {
        return productUpdateTime;
    }

    public void setProductUpdateTime(String productUpdateTime) {
        this.productUpdateTime = productUpdateTime;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public void setProductConfigs(Set<ProductConfig> productConfigs) {
        this.productConfigs = productConfigs;
    }

    public Set<TbImage> getCoverImgs() {
        return coverImgs;
    }

    public void setCoverImgs(Set<TbImage> coverImgs) {
        this.coverImgs = coverImgs;
    }

    public Set<TbImage> getDetailsImgNames() {
        return detailsImgNames;
    }

    public void setDetailsImgNames(Set<TbImage> detailsImgNames) {
        this.detailsImgNames = detailsImgNames;
    }

    public Set<TbFile> getProductContentsFile() {
        return productContentsFile;
    }

    public void setProductContentsFile(Set<TbFile> productContentsFile) {
        this.productContentsFile = productContentsFile;
    }

    public Set<TbFile> getProductSpecificationsFile() {
        return productSpecificationsFile;
    }

    public void setProductSpecificationsFile(Set<TbFile> productSpecificationsFile) {
        this.productSpecificationsFile = productSpecificationsFile;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

//    public ProductType getProductType() {
//        return productType;
//    }
//
//    public void setProductType(ProductType productType) {
//        this.productType = productType;
//    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

//    public Set<ProductConfigDetails> getProductConfigDetails() {
//        return productConfigDetails;
//    }
//
//    public void setProductConfigDetails(Set<ProductConfigDetails> productConfigDetails) {
//        this.productConfigDetails = productConfigDetails;
//    }

    public Set<ProductSize> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(Set<ProductSize> productSizes) {
        this.productSizes = productSizes;
    }

    public Set<ProductSpecifications> getProductSpecificationss() {
        return productSpecificationss;
    }

    public void setProductSpecificationss(Set<ProductSpecifications> productSpecificationss) {
        this.productSpecificationss = productSpecificationss;
    }


    public String getProductTypeName() {
        if(StringUtils.isEmpty(productTypeName)&&!StringUtils.isEmpty(productTypeId)){
            String[]  typeIds = productTypeId.split("\\|");
            for (int i=0;i<typeIds.length;i++){
                ProductType productType = ProductTypeCache.getProductTypesById(typeIds[i]);
                if(productType.getProductTypeIsParent().equals(0)){
                    productTypeName+= productType.getProductTypeName()+"|";
                    productTypeNameEng += productType.getProductTypeNameEng()+"|";
                }
            }
            productTypeName = productTypeName.substring(0,productTypeName.length()-1);
            productTypeNameEng = productTypeNameEng.substring(0,productTypeNameEng.length()-1);
        }
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductTypeNameEng() {
        if(StringUtils.isEmpty(productTypeNameEng)&&!StringUtils.isEmpty(productTypeId)){
            String[]  typeIds = productTypeId.split("\\|");
            for (int i=0;i<typeIds.length;i++){
                ProductType productType = ProductTypeCache.getProductTypesById(typeIds[i]);
                if(productType.getProductTypeIsParent().equals(0)){
                    productTypeName+= productType.getProductTypeName()+"|";
                    productTypeNameEng += productType.getProductTypeNameEng()+"|";
                }
            }
            productTypeName = productTypeName.substring(0,productTypeName.length()-1);
            productTypeNameEng = productTypeNameEng.substring(0,productTypeNameEng.length()-1);
        }
        return productTypeNameEng;
    }

    public void setProductTypeNameEng(String productTypeNameEng) {
        this.productTypeNameEng = productTypeNameEng;
    }
//
//    public Set<Color> getColors() {
//        return colors;
//    }
//
//    public void setColors(Set<Color> colors) {
//        this.colors = colors;
//    }
}
