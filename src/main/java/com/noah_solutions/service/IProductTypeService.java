package com.noah_solutions.service;

import com.noah_solutions.entity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductTypeService {
    //根据添加获取所以信息
    Page<ProductType> selectProductTypePageByCont(ProductType condition, Pageable pageable);
    //根据条件获取总数
    Long selectCountProductTypeByCont(ProductType condition);
    //通過條件查詢所有條件
    List<ProductType> selectProductTypeByCont(ProductType condition);

    //通過權限等級排序查詢所有條件
    List<ProductType> selectAllProductTypeOrderByLv();

    //通过id查询单个产品类型
    ProductType selectProductTypeByProductTypeId(String productTypeId);

    //保存产品类型
    void addProductType(ProductType productType)throws Exception;


    void editProductType(ProductType productType)throws Exception;
    //删除单个产品类型
    void delProductTypeByProductTypeId(String productTypeId);
    //批量删除产品类型
    void delAllProductTypeByProductTypeId(List<String> productTypeIds);

    List<ProductType> findFirst5OrderByProductSelloutNumDesc();

    void initProductTypePraentIds();
    void initProductTypeDetailsEng();
    //数据对接(把旧版本的商品类型数据对接过来)
//    void copyOldType();
}
