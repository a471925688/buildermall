package com.noah_solutions.repository;

import com.noah_solutions.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,String> {
    //通過管理員id查詢對應的所有品牌
    List<Brand> findAllByAdminId(String adminId);
    //根据id查询权限
    Brand findByBrandId(String brandId);
    //通過多個id查詢
    List<Brand> findAllByBrandIdIn(List<String> brandId);
    //查詢品牌名稱是否存在
    Boolean existsByBrandName(String brandName);
    //查詢指定國家品牌數量
    Long countByBrandCountry(String brandCountry);
    //查詢指定國家商品以外的品牌數量
    Long countByBrandCountryIsNot(String brandCountry);


//    List<Brand> findAll(Specification specification);
}
