package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.houbb.paradise.common.util.StringUtil;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.util.DateUtil;
import com.noah_solutions.util.ZJFConverterDemo;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Proxy;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.noah_solutions.common.ProjectConfig.*;

/**
 * 2018 12 19 lijun
 * 建材网商品类型表表实体类
 */
@Entity
@Table(name = "order_product")
@Data
@Proxy(lazy = false)
public class OrderProduct implements Serializable {
    private static final long serialVersionUID = 15425676189987267L;
    @Id
    @Column(unique = true, nullable = false,columnDefinition = "BIGINT  AUTO_INCREMENT   NOT NULL COMMENT '產品id'")
    private String orderProductId;
    @Column(columnDefinition = "INT   COMMENT '產品類型ids'")
    private String  productTypeIds;
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 0  NOT NULL COMMENT '產品單位'")
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
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '產品中文描述'")
    private String  productDescribe;
    @Column(columnDefinition = "VARCHAR(1000) COMMENT '產品英文描述'")
    private String  productDescribeEng;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '縮略圖'")
    private String productThumbnailPhonePath;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '產品圖片路徑'")
    private String  productPhonePath;
    @Column(columnDefinition = "INT   COMMENT '产品配置id'")
    private String  productConfigId;
    @Column(columnDefinition = "DOUBLE COMMENT '單價'")
    private Double  productPrice;
    @Column(columnDefinition = "DOUBLE COMMENT '產品重量'")
    private Double  productWeight;

    public String getProductConfigId() {
        return productConfigId;
    }

    public void setProductConfigId(String productConfigId) {
        this.productConfigId = productConfigId;
    }

    @Column(columnDefinition = "INT(10) COMMENT '數量'")
    private Integer  productQuantity;
    @Column(columnDefinition = "DOUBLE COMMENT '總運費'")
    private Double  productFreightCharges;
    @Column(columnDefinition = "DOUBLE COMMENT ' 發貨天數'")
    private Integer  deliveryTime;


//    @Column(columnDefinition = "DOUBLE COMMENT '總價'")
//    private Double  productTotalPrice;

    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(用戶id)'")
    private String userId;
    @Column(columnDefinition = "BIGINT  NOT NULL COMMENT '(訂單id)'")
    private String orderId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(供應商id)'")
    private String adminId;
    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '(品牌id)'")
    private String brandId;
    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '(產品id)'")
    private String productId;
    @Column(columnDefinition = "VARCHAR(255)  NOT NULL COMMENT '(類型id)'")
    private String productTypeId;
    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '(评价次数)'",insertable = false)
    private Integer isEvaluation;




    //品牌信息
    @OneToOne(fetch=FetchType.LAZY)
    @NotFound(action= NotFoundAction.IGNORE)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="brandId",insertable = false,updatable = false)
    private Brand brand;

    //產品類型信息
//    @OneToOne(fetch=FetchType.LAZY)
//    @NotFound(action= NotFoundAction.IGNORE)
//    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//    @JoinColumn(name="productTypeId",insertable = false,updatable = false)
//    private ProductType productType;

//    //供應商信息
//    @OneToOne(fetch=FetchType.LAZY)
//    @NotFound(action= NotFoundAction.IGNORE)
//    @JsonIgnoreProperties(value={"role","login","hibernateLazyInitializer","handler","fieldHandler"})
//    @JoinColumn(name="adminId",insertable = false,updatable = false)
//    private Admin admin;


    //產品參數信息
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="orderProductId")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @OrderBy("orderProductParamName ASC")
    private Set<OrderProductParam> orderProductParams = new HashSet<>();




    @Transient
    private String productTypeName = "";
    @Transient
    private String productTypeNameEng = "";

    @Transient
    private Double  productPriceCNY;
    @Transient
    private Double  productPriceUSD;
    @Transient
    private Double  productFreightChargesCNY;
    @Transient
    private Double  productFreightChargesUSD;
    @Transient
    private Double  productTotalPrice;//訂單產品總價（包含運費）
    @Transient
    private Double  productTotalPriceCNY;//訂單產品總價（包含運費）
    @Transient
    private Double  productTotalPriceUSD;//訂單產品總價（包含運費）
    @Transient
    private String  lang;
    @Transient
    private String  currentTile;
    @Transient
    private String  currentDescribe;

    @Transient
    private  String productPhonePathDetails;

    @Transient
    private  String brandLogoPathDetails;

    @Transient
    private String orderProductParamsHtml;

    @Transient
    private String deliveryTimeStr;


    public String getOrderProductParamsHtml() {
        if(orderProductParams.size()>0){
            orderProductParamsHtml="";
            orderProductParams.forEach(v->{
                String paramName = v.getOrderProductParamName();
                String paramContent = v.getOrderProductParamContent();
                if(!StringUtils.isEmpty(lang)){
                    if(lang.equals(ProjectConfig.Language.CN.getValue())|| StringUtil.isEmpty(lang)) {
                        paramName = ZJFConverterDemo.SimToTra(v.getOrderProductParamName());
                        paramContent = ZJFConverterDemo.SimToTra(v.getOrderProductParamContent());
                    }else if(lang.equals(ProjectConfig.Language.CNN.getValue())) {
                        paramName = ZJFConverterDemo.TraToSim(v.getOrderProductParamName());
                        paramContent = ZJFConverterDemo.TraToSim(v.getOrderProductParamContent());
                    }else {
                        paramName = ZJFConverterDemo.TraToSim(v.getOrderProductParamEng());
                        paramContent = ZJFConverterDemo.TraToSim(v.getOrderProductParamContentEng());
                    }

                }
                orderProductParamsHtml+="<span>"+paramName+"：</span>"+paramContent+"<span></span><br/>";
            });
        }
        return orderProductParamsHtml;
    }

    public String getDeliveryTimeStr() {
        if(!StringUtils.isEmpty(deliveryTime)){
            deliveryTimeStr = DateUtil.getNextDay(new Date(),deliveryTime+"");
        }
        return deliveryTimeStr;
    }

    public void setDeliveryTimeStr(String deliveryTimeStr) {
        this.deliveryTimeStr = deliveryTimeStr;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCurrentTile() {
        if(!StringUtils.isEmpty(lang)){
            if(lang.equals(ProjectConfig.Language.CN.getValue())|| StringUtil.isEmpty(lang)) {
                currentTile = ZJFConverterDemo.SimToTra(productTitle);
            }else if(lang.equals(ProjectConfig.Language.CNN.getValue())) {
                currentTile = ZJFConverterDemo.TraToSim(productTitle);
            }else {
                currentTile = ZJFConverterDemo.TraToSim(productTitleEng);
            }
        }
        return currentTile;
    }


    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getCurrentDescribe() {
        if(!StringUtils.isEmpty(lang)){
            if(lang.equals(ProjectConfig.Language.CN.getValue())|| StringUtil.isEmpty(lang)) {
                currentDescribe = ZJFConverterDemo.SimToTra(productDescribe);
            }else if(lang.equals(ProjectConfig.Language.CNN.getValue())) {
                currentDescribe = ZJFConverterDemo.TraToSim(productDescribe);
            }else {
                currentDescribe = ZJFConverterDemo.TraToSim(productDescribeEng);
            }
        }
        return currentDescribe;
    }

    public void setCurrentDescribe(String currentDescribe) {

        this.currentDescribe = currentDescribe;
    }

    public void setCurrentTile(String currentTile) {
        this.currentTile = currentTile;
    }

    public void setOrderProductParamsHtml(String orderProductParamsHtml) {
        this.orderProductParamsHtml = orderProductParamsHtml;
    }

    public Double getProductFreightCharges() {
        return productFreightCharges;
    }

    public String getProductThumbnailPhonePath() {
        return productThumbnailPhonePath;
    }

    public Double getProductTotalPriceUSD() {
        if(productTotalPrice!=null){
            return Double.valueOf(Math.round(productTotalPrice* ProjectConfig.TodayRateUSD));
        }
      return productTotalPriceUSD;
    }

    public String getProductPhonePathDetails() {
        if(!StringUtils.isEmpty(productPhonePath)){
            productPhonePathDetails =  ProjectConfig.HOST_NAME+"/image/"+productPhonePath;
        }
        return productPhonePathDetails;
    }

    public String getBrandLogoPathDetails() {
        if(brand!=null&&!StringUtils.isEmpty(brand.getBrandLogoPath())){
            brandLogoPathDetails =  ProjectConfig.HOST_NAME+"/image/"+brand.getBrandLogoPath();
        }
        return brandLogoPathDetails;
    }



    public void setBrandLogoPathDetails(String brandLogoPathDetails) {
        this.brandLogoPathDetails = brandLogoPathDetails;
    }

    public void setProductPhonePathDetails(String productPhonePathDetails) {
        this.productPhonePathDetails = productPhonePathDetails;
    }

    public void setProductTotalPriceUSD(Double productTotalPriceUSD) {
        this.productTotalPriceUSD = productTotalPriceUSD;
    }

    public Double getProductPriceUSD() {
        if(productTotalPrice==null&&productPrice!=null){
            productTotalPrice = Double.valueOf(Math.round(productQuantity*productPrice +(StringUtils.isEmpty(productFreightCharges)?0:productFreightCharges)));
        }
        if(productPrice!=null){
            return productPrice* ProjectConfig.TodayRateUSD;
        }
        return productPrice;
    }

    public void setProductPriceUSD(Double productPriceUSD) {
        this.productPriceUSD = productPriceUSD;
    }

    public Double getProductPriceCNY() {
        if(productTotalPrice==null&&productPrice!=null){
            productTotalPrice = Double.valueOf(Math.round(productQuantity*productPrice +(StringUtils.isEmpty(productFreightCharges)?0:productFreightCharges)));
        }
        if(productPrice!=null){
            return productPrice* ProjectConfig.TodayRate;
        }
        return productPrice;
    }

    public Double getProductFreightChargesUSD() {
        if(productFreightCharges!=null){
            return Double.valueOf(Math.round(productFreightCharges* ProjectConfig.TodayRateUSD));
        }else {
            return  null;
        }

    }

    public void setProductFreightChargesUSD(Double productFreightChargesUSD) {
        this.productFreightChargesUSD = productFreightChargesUSD;
    }

    public Double getProductFreightChargesCNY() {
        if(productFreightCharges!=null){
            return Double.valueOf(Math.round(productFreightCharges* ProjectConfig.TodayRate));
        }else {
            return  null;
        }

    }

    public Double getProductTotalPriceCNY() {
        if(productTotalPrice!=null){
            return Double.valueOf(Math.round(productTotalPrice* ProjectConfig.TodayRate));
        }
       return null;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
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

    public String getProductPhonePath() {
        return productPhonePath;
    }

    public void setProductPhonePath(String productPhonePath) {
        this.productPhonePath = productPhonePath;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductThumbnailPhonePath(String productThumbnailPhonePath) {
        this.productThumbnailPhonePath = productThumbnailPhonePath;
    }

    public Double getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(Double productWeight) {
        this.productWeight = productWeight;
    }

    public void setProductPriceCNY(Double productPriceCNY) {
        this.productPriceCNY = productPriceCNY;
    }

    public void setProductFreightChargesCNY(Double productFreightChargesCNY) {
        this.productFreightChargesCNY = productFreightChargesCNY;
    }

    public void setProductTotalPriceCNY(Double productTotalPriceCNY) {
        this.productTotalPriceCNY = productTotalPriceCNY;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setProductFreightCharges(Double productFreightCharges) {
        this.productFreightCharges = productFreightCharges;
    }

    public Double getProductTotalPrice() {
        if(productTotalPrice==null&&productPrice!=null){
            productTotalPrice = productQuantity*productPrice +productFreightCharges;
        }
        return productTotalPrice;
    }

    public void setProductTotalPrice(Double productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getProductUnitEng() {
        return productUnitEng;
    }

    public void setProductUnitEng(String productUnitEng) {
        this.productUnitEng = productUnitEng;
    }
//    public ProductType getProductType() {
//        return productType;
//    }
//
//    public void setProductType(ProductType productType) {
//        this.productType = productType;
//    }

    public Set<OrderProductParam> getOrderProductParams() {
        return orderProductParams;
    }

    public void setOrderProductParams(Set<OrderProductParam> orderProductParams) {
        this.orderProductParams = orderProductParams;
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
}
