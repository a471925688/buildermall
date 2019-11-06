package com.noah_solutions.service;

import com.noah_solutions.bean.ProductTable;
import com.noah_solutions.bean.ProductUnit;
import com.noah_solutions.common.TablePage;
import com.noah_solutions.entity.Product;
import com.noah_solutions.entity.ProductEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    //查詢所有產品基本信息
    List<Product> selectAllProduct();
    //分頁查詢所有產品基本信息(帶條件)
    Page<Product> selectProductPageByCont(Product condition, Pageable pageable);
    Product getProductById(String id);
    //查询产品数量
    Long selectCountProductByCont(Product product);
    //售出总数
    Long selectTotalSold();

    List<ProductUnit> selectAllProductUnit();
    //添加產品
    void addProduct(Product product)throws Exception;
    //更新產品
    void updateProduct(Product product)throws Exception;

    Product selectProductById(String productId,String userId);
    //刪除Size
    void delProductSizeByProductSizeId(String productSizeId);
    //刪除specifications
    void delProductSpecificationsByProductSpecificationsId(String productSpecificationsId);
    //刪除config
    void delProductConfigByProductConfigId(String productConfigId);

    void delAllProductConfigByProductConfigId(List productConfigIds);
    //通過產品類型id查詢所有產品
    TablePage<ProductTable> selectProductPageByProductTypeId(Product product, PageRequest pageable);
    TablePage<ProductTable> selectProductPageByProductCont(Product product, PageRequest pageable);
    //改變產品上下架張貼
    void changeProductState(Boolean productState, String productId);
//    List<ProductTableProper> productText();
    ////////////////////////////////前端部分  start////////////////////////////////////////////
    ////////////////////////////////前端部分  start////////////////////////////////////////////
    TablePage<ProductTable> selectProductPageByContent(String adminId,String productTypeId,String brandId, String searchVal, PageRequest pageable,Integer order);

    void delProductByProductId(String productId);

    void auditPass(List<String> temProductIds);

    void auditReject(List<String> temProductIds);

    void initProductType();

    void productEvaluation(ProductEvaluation productEvaluation)throws Exception;

    //分页获取产品评价
    Page<ProductEvaluation> selectProductEvaluationPageByProductId(String productId, Pageable of);

    Page<ProductTable> getProductBrowse(String loginUserId,Integer page,Integer limit);

    Product getCopyProduct(String productId);

    void delAllProductByProductId(List<String> productIds);


    ////////////////////////////////前端部分  end////////////////////////////////////////////
    ////////////////////////////////前端部分  end////////////////////////////////////////////
}
