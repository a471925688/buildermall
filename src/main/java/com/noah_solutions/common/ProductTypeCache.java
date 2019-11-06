package com.noah_solutions.common;

import com.noah_solutions.entity.Product;
import com.noah_solutions.entity.ProductType;
import com.noah_solutions.service.IProductTypeService;
import com.noah_solutions.util.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductTypeCache {
    @Resource
    private IProductTypeService productTypeService;

    private static List<ProductType> productTypesZtree ;//樹級產品類型

    private static List<ProductType> productTypesAllLst ;//所有產品類型 list

    private static Map<String,ProductType> productTypesAllMap;//所有產品類型 map

    private static Map<String,List<ProductType>> productTypesChildMap;//子類產品類型 map(僅一級子類)

    private static Map<Integer,List<ProductType>> productTypesLevelMap;//產品等级 map

    private static List<ProductType> Lv1AndLv2ProductTypes;//1.2级产品类型(树形)

    private static  List<ProductType> first5OrderByProductSelloutNumDesc;//熱門產品類型

    public void init(){
        productTypesAllLst = productTypeService.selectAllProductTypeOrderByLv();
        productTypesAllMap = new HashMap<>();
        productTypesChildMap = new HashMap<>();
        productTypesZtree = new ArrayList<>();
        productTypesLevelMap = new HashMap<>();
        Lv1AndLv2ProductTypes= new ArrayList<>();
        for (ProductType p:productTypesAllLst) {
            if(p.getProductTypeLevel()==1){
                ProductType productType = new ProductType();
                BeanUtils.copyProperties(p,productType);
                productTypesZtree.add(productType);
                Lv1AndLv2ProductTypes.add(productType);
            }
            productTypesAllMap.put(p.getProductTypeId(),p);
            if(!productTypesChildMap.containsKey(p.getProductTypeParentId())){
                productTypesChildMap.put(p.getProductTypeParentId(),new ArrayList<>());
            }
            productTypesChildMap.get(p.getProductTypeParentId()).add(p);
            if(!productTypesLevelMap.containsKey(p.getProductTypeLevel())){
                productTypesLevelMap.put(p.getProductTypeLevel(),new ArrayList<>());
            }
            productTypesLevelMap.get(p.getProductTypeLevel()).add(p);
        }
        for (ProductType p:productTypesZtree
             ) {
            p.setChildProductType(getChild(p.getProductTypeId()));
        }
        for (ProductType p:Lv1AndLv2ProductTypes
        ) {
            p.setChildProductType(productTypesChildMap.get(p.getProductTypeId()));
        }
        first5OrderByProductSelloutNumDesc = productTypeService.findFirst5OrderByProductSelloutNumDesc();
    }

    public static List<ProductType> getFirst5OrderByProductSelloutNumDesc(){
        return first5OrderByProductSelloutNumDesc;
    }

    public static List<ProductType> getProductTypesZtree(){
        return productTypesZtree;
    }

    //獲取所有类型
    public static List<ProductType> getAllProductTypes(){
        return productTypesAllLst;
    }

    //獲取指定等級類型
    public static List<ProductType> getProductTypesByLevel(Integer level){
        return productTypesLevelMap.get(level);
    }
    //獲取1.2级产品类型
    public static List<ProductType> getLv1AndLv2ProductTypes(){
        return Lv1AndLv2ProductTypes;
    }
    //通過id獲取類型信息
    public static ProductType getProductTypesById(String id){
        return productTypesAllMap.get(id);
    }

    //通過id獲取子集類型信息
    public static List<ProductType> getProductTypesChildById(String parentId){
        return productTypesChildMap.get(parentId);
    }


    private List<ProductType> getChild(String productTypeId){
        List<ProductType> productTypes = new ArrayList<>();
        if(productTypesChildMap.get(productTypeId)!=null){
            for (ProductType p:productTypesChildMap.get(productTypeId)
            ) {
                p.setChildProductType(getChild(p.getProductTypeId()));
                ProductType productType = new ProductType();
                BeanUtils.copyProperties(p,productType);
                productTypes.add(productType);
            }
        }
        return productTypes;
    }
//    private List<Product>  recursionTree(Product p){
//        if(productTypesLevelMap.get(p.getProductTypeId())!=null){
//
//        }
//    }
}
