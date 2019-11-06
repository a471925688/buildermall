package com.noah_solutions.bean;

import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Brand;
import com.noah_solutions.entity.ProductType;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class ProductTable {
    private String productId;
    private String  productTitle;
    private String  productTitleEng;
    private String  productPhonePath;
    private String  productMaterial;
    private String  productMaterialEng;
    private String  productFeatures;
    private String  productFeaturesEng;
    private Double  productPriceStart;
    private Double  productPriceEnd;
    private Double  productDisPriceStart;
    private Double  productDisPriceEnd;
    private String  productDescribe;
    private String  productDescribeEng;
    private Boolean productState;
    private String productSelloutNum;
    private String  productCreateTime;
    private Boolean productIsDiscount;
    private Boolean productIsFreight;

    private String adminName;
    private String brandLogoPath;
    private String productTypeId;
    private String productTypeName="";
    private String productTypeNameEng="";
    private String productUnit;
    private String productUnitEng;
    private Double productDisPriceStartCNY;
    private Double productDisPriceStartUSD;
    private Double productDisPriceENDCNY;

    public ProductTable() {
    }

    public ProductTable(String productId, String productTitle, String productTitleEng, String productMaterial, String productMaterialEng, String productFeatures, String productFeaturesEng, Double productPriceStart, Double productPriceEnd, Double productDisPriceStart, Double productDisPriceEnd, String productDescribe, String productDescribeEng, Boolean productState, String productSelloutNum, String productCreateTime, Boolean productIsDiscount,Boolean productIsFreight,String productUnit,String productUnitEng,Admin admin, Brand brand,String productTypeId) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.productTitleEng = productTitleEng;
        this.productMaterial = productMaterial;
        this.productMaterialEng = productMaterialEng;
        this.productFeatures = productFeatures;
        this.productFeaturesEng = productFeaturesEng;
        this.productPriceStart = productPriceStart;
        this.productPriceEnd = productPriceEnd;
        this.productDisPriceStart = productDisPriceStart;
        this.productDisPriceEnd = productDisPriceEnd;
        this.productDescribe = productDescribe;
        this.productDescribeEng = productDescribeEng;
        this.productState = productState;
        this.productSelloutNum = productSelloutNum;
        this.productCreateTime = productCreateTime;
        this.productIsDiscount = productIsDiscount;
        this.adminName = admin.getAdminRealName();
        this.brandLogoPath = brand.getBrandLogoPath();
//        this.productTypeName = productType.getProductTypeName();
//        this.productTypeNameEng = productType.getProductTypeNameEng();
        this.productTypeId = productTypeId;
        this.productUnit = productUnit;
        this.productUnitEng = productUnitEng;
        this.productIsFreight = productIsFreight;
    }

    public Double getProductDisPriceStartCNY() {
        return Double.valueOf(Math.round(productDisPriceStart* ProjectConfig.TodayRate));
    }

    public void setProductDisPriceStartCNY(Double productDisPriceStartCNY) {
        this.productDisPriceStartCNY = productDisPriceStartCNY;
    }

    public Double getProductDisPriceENDCNY() {
        if(productDisPriceEnd!=null){
            productDisPriceENDCNY = productDisPriceEnd * ProjectConfig.TodayRate;
        }
        return productDisPriceENDCNY;
    }

    public Double getProductDisPriceStartUSD() {
        if(productDisPriceStart!=null){
            productDisPriceStartUSD = productDisPriceStart * ProjectConfig.TodayRateUSD;
        }
        return productDisPriceStartUSD;
    }

    public void setProductDisPriceStartUSD(Double productDisPriceStartUSD) {
        this.productDisPriceStartUSD = productDisPriceStartUSD;
    }

    public void setProductDisPriceENDCNY(Double productDisPriceENDCNY) {
        this.productDisPriceENDCNY = productDisPriceENDCNY;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPhonePath() {
        return productPhonePath;
    }

    public void setProductPhonePath(String productPhonePath) {
        this.productPhonePath = productPhonePath;
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
