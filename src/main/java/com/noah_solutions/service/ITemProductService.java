package com.noah_solutions.service;

import com.noah_solutions.bean.RatioData;
import com.noah_solutions.entity.TemProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITemProductService {

    //查询所有待审核产品
    List<TemProduct> selectAllTemProduct();

    Page<TemProduct> selectTemProductPageByCont(TemProduct condition, Pageable pageable);

    //根据条件获取总数
    Long selectCountTemProductByCont(TemProduct condition);

    Long selectCountTemProductProductByProductTypeId(String productType);
    //查詢單個待审核产品
    TemProduct getTemProductById(String temProductId);
    //添加待审核产品
    void saveTemProduct(TemProduct temProduct)throws Exception;

    //删除单个待审核产品
    void delTemProductByTemProductId(String temProductId);
    //批量删除待审核产品
    void delAllTemProductByTemProductId(List<String> temProductIds);
    // 獲取購物比例(類型)
    List<RatioData> selectShoppingRatioByProductType();
    // 獲取待审核产品數量(待审核产品狀態)
    List selectCountTemProductPaymentStatus();
    // 獲取待审核产品數量(待审核产品狀態)
    List selectCountTemProductState(String adminId);

    //根据审核类型分组获取数量
    List<Long> selectCountTemProductByState(String chekckAdminType);

}
