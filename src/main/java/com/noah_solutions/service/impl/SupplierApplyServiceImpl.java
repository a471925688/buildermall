package com.noah_solutions.service.impl;

import com.noah_solutions.bean.RatioData;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.SupplierApply;
import com.noah_solutions.entity.SupplierApplyProduct;
import com.noah_solutions.repository.SupplierApplyProductRepository;
import com.noah_solutions.repository.SupplierApplyRepository;
import com.noah_solutions.service.ISupplierApplyService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("supplierApplyService")
public class SupplierApplyServiceImpl implements ISupplierApplyService {
    @Resource
    private SupplierApplyRepository supplierApplyRepository;

    @Resource
    private SupplierApplyProductRepository supplierApplyProductRepository;
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////new Sort////////////////////////

    //查询所有訂單
    @Override
    public List<SupplierApply> selectAllSupplierApply() {
        return supplierApplyRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    //分页查询所有訂單（带条件）
    @Override
    public Page<SupplierApply> selectSupplierApplyPageByCont(SupplierApply condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("createTime", ExampleMatcher.GenericPropertyMatchers.contains());
        return  supplierApplyRepository.findAll(Example.of(condition,matcher),pageable);
    }
    @Override
    public SupplierApply getSupplierApplyById(String supplierApplyId) {
        return supplierApplyRepository.getOne(supplierApplyId);
    }


    //查询訂單数量(带条件)
    @Override
    public Long selectCountSupplierApplyByCont(SupplierApply condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  supplierApplyRepository.count(Example.of(condition,matcher));
    }


    //查询訂單產品数量(带条件)
    @Override
    public Long selectCountSupplierApplyProductByProductTypeId(String productTypeId) {
      return null;
    }

    @Override
    public List<RatioData> selectShoppingRatioByProductType() {
       return null;
    }

    @Override
    public List selectCountSupplierApplyPaymentStatus() {
       return null;
    }

    @Override
    public List selectCountSupplierApplyState(String adminId) {
        return null;
    }

    @Override
    public List<Long> selectCountSupplierApplyByState(String chekckAdminType) {
        List<Long> countState = new ArrayList<>();
        countState.add(supplierApplyRepository.count());
        countState.add(supplierApplyRepository.countByState(ProjectConfig.SupplierApplyState.Unaudited.getValue()));
        countState.add(supplierApplyRepository.countByState(ProjectConfig.SupplierApplyState.PASS.getValue()));
        countState.add(supplierApplyRepository.countByState(ProjectConfig.SupplierApplyState.AUDITFAILURE.getValue()));
        return countState;

    }

    @Override
    @Transactional
    public void add(SupplierApply supplierApply) {
        Set<SupplierApplyProduct> supplierApplyProducts = supplierApply.getSupplierApplyProducts();
        supplierApply.setSupplierApplyProducts(null);
        supplierApply = supplierApplyRepository.saveAndFlush(supplierApply);
        for (SupplierApplyProduct product: supplierApplyProducts) {
            product.setSupplierapplyId(supplierApply.getId());
        }
        supplierApply.setSupplierApplyProducts(supplierApplyProducts);
        supplierApplyRepository.flush();
    }

    @Override
    @Transactional
    public void auditPass(List<String> supplierApplyIds) {
        supplierApplyRepository.updateState(supplierApplyIds,ProjectConfig.SupplierApplyState.PASS.getValue());
    }

    @Override
    @Transactional
    public void auditReject(List<String> supplierApplyIds) {
        supplierApplyRepository.updateState(supplierApplyIds,ProjectConfig.SupplierApplyState.AUDITFAILURE.getValue());
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////



    //删除訂單
    @Override
    public void delSupplierApplyBySupplierApplyId(String supplierApplyId) {
        supplierApplyRepository.deleteById(supplierApplyId);
    }




    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////







    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////


    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}
