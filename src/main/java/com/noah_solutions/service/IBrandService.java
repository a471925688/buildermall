package com.noah_solutions.service;

import com.noah_solutions.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBrandService {

    //查询所有品牌
    List<Brand> selectAllBrand();

    //通過管理員id查詢所有品牌
    List<Brand> selectAllBrandByAdmin(String  adminId);

    Page<Brand> selectBrandPageByCont(Brand condition, Pageable pageable);


    //通过id查询单个品牌
    Brand selectBrandByBrandId(String brandId);
    //根据条件获取总数
    Long selectCountBrandByCont(Brand condition);
    //查詢國內的品牌數量
    Long selectCountBrandIsCN();
    //查詢國外品牌數量
    Long selectCountBrandIsNotCN();

    //添加品牌
    void addBrand(Brand brand)throws Exception;
    //更新品牌
    void updateBrand(Brand brand)throws Exception;

    //删除单个品牌
    void delBrandByBrandId(String brandId);
    //批量删除品牌
    void delAllBrandByBrandId(List<String> brandIds);

}
