package com.noah_solutions.service;

import com.noah_solutions.bean.RatioData;
import com.noah_solutions.entity.SupplierApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISupplierApplyService {

    //查询所有待审核产品
    List<SupplierApply> selectAllSupplierApply();

    Page<SupplierApply> selectSupplierApplyPageByCont(SupplierApply condition, Pageable pageable);

    //根据条件获取总数
    Long selectCountSupplierApplyByCont(SupplierApply condition);

    Long selectCountSupplierApplyProductByProductTypeId(String productType);
    //查詢單個待审核产品
    SupplierApply getSupplierApplyById(String supplierApplyId);


    //删除单个待审核产品
    void delSupplierApplyBySupplierApplyId(String supplierApplyId);

    // 獲取購物比例(類型)
    List<RatioData> selectShoppingRatioByProductType();
    // 獲取待审核产品數量(待审核产品狀態)
    List selectCountSupplierApplyPaymentStatus();
    // 獲取待审核产品數量(待审核产品狀態)
    List selectCountSupplierApplyState(String adminId);

    //根据审核类型分组获取数量
    List<Long> selectCountSupplierApplyByState(String chekckAdminType);

    void  add(SupplierApply supplierApply);

    void auditPass(List<String> supplierApplyIds);

    void auditReject(List<String> supplierApplyIds);
}
