package com.noah_solutions.repository;

import com.noah_solutions.entity.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType,String> {
    //根据id查询权限
    ProductType findByProductTypeId(String productTypeId);
    List<ProductType> findAllByProductTypeParentId(String peParentId);
    Set<ProductType> findAllByProductTypeIdIn(List<String> productTypeId);
    //根据类型名称获取具体信息
    ProductType findByProductTypeName(String productType);
    ProductType findByProductTypeNameAndProductTypeParentId(String productType,String productTypeParentId);
    //查询指定类型是否存在
    Boolean existsByProductTypeNameAndProductTypeParentId(String productType,String productTypeParentId);
    Boolean existsByProductTypeName(String productType);

    //通过描述查询类型
    ProductType findByProductTypeDescribeEndingWith(String describe);



//    @Query("select pt from ProductType pt left join com.noah_solutions.entity.Product p on pt.productTypeId = p.productTypeId group by pt.productTypeId  order by  sum(p.productSelloutNum) desc ")
//
@Query("select p.productTypeId from  com.noah_solutions.entity.Product p  group by p.productTypeId  order by  sum(p.productSelloutNum) desc ")
List<String> findFirst5OrderByProductSelloutNumDesc(Pageable pageable);

}
