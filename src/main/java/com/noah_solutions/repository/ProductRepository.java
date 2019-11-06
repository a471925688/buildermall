package com.noah_solutions.repository;

import com.noah_solutions.bean.ProductTable;
import com.noah_solutions.bean.ProductUnit;
import com.noah_solutions.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {



    @Query("select new com.noah_solutions.bean.ProductUnit(p.productUnit,p.productUnitEng) from Product p group by p.productUnit,p.productUnitEng")
    List<ProductUnit> findAllGroupByUnit();

    @Modifying
    @Query("update Product p set p.productSelloutNum = p.productSelloutNum+?1 where p.productId = ?2")
    void addProductSelloutNum(String num,String productId);
    Product findFirstByOrderByProductIdDesc();
//    Page<Product> findAllByProductTypeProductTypeParentsIdStartsWithOrProductTypeId(String parentsId,String productTypeId, Pageable pageable);

    @Query(value = "select p.productId from Product  p  order by p.productId desc nulls first ")
    String getNextId();

    Product findByProductId(String productId);

    void deleteAllByProductIdIn(List<String> productId);

    @Query("select p.productIsFreight from  Product  p where p.productId=?1")
    Boolean findProductIsFreight(String  productId);

    @Modifying
    @Query("update Product p set p.productState = ?1 where p.productId = ?2")
    void updateProductState(Boolean productState, String productId);

    @Query("select sum(p.productSelloutNum) from Product p")
    Long selectTotalSold();

    @Query("select new com.noah_solutions.bean.ProductTable(p.productId,p.productTitle,p.productTitleEng,p.productMaterial,p.productMaterialEng, p.productFeatures, p.productFeaturesEng,p.productPriceStart,p.productPriceEnd,p.productDisPriceStart, p.productDisPriceEnd, p.productDescribe, p.productDescribeEng, p.productState,p.productSelloutNum, p.productCreateTime, p.productIsDiscount,p.productIsFreight,p.productUnit, p.productUnitEng,p.admin, p.brand,p.productTypeId) from Product p order by p.productSelloutNum desc ")
    Page<ProductTable> selectProductOrderBySelloutNum(Pageable pageable);


    @Query("select p.productId,p.productTypeId from Product p")
    List getProductTypeId();

    @Modifying
    @Query("update Product p  set p.productTypeId=?1 where p.productId=?2")
    void setProductTypeId(String productTypeId,String productId);

    @Query("select distinct new com.noah_solutions.bean.ProductTable(p.productId,p.productTitle,p.productTitleEng,p.productMaterial,p.productMaterialEng, p.productFeatures, p.productFeaturesEng,p.productPriceStart,p.productPriceEnd,p.productDisPriceStart, p.productDisPriceEnd, p.productDescribe, p.productDescribeEng, p.productState,p.productSelloutNum, p.productCreateTime, p.productIsDiscount,p.productIsFreight,p.productUnit, p.productUnitEng,p.admin, p.brand,p.productTypeId) from Product  p inner join com.noah_solutions.entity.ProductBrowseRecords as pb on p.productId = pb.productId where pb.userId = ?1 order by pb.id desc ")
    Page<ProductTable> findProductBrowse(String userId, Pageable pageable);

}
