package com.noah_solutions.service.impl;

import com.noah_solutions.bean.RatioData;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.TemProduct;
import com.noah_solutions.repository.TemProductRepository;
import com.noah_solutions.service.ITemProductService;
import com.noah_solutions.util.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("temProductService")
public class TemProductServiceImpl implements ITemProductService {
    @Resource
    private TemProductRepository temProductRepository;
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有訂單
    @Override
    public List<TemProduct> selectAllTemProduct() {
        return temProductRepository.findAll();
    }

    //分页查询所有訂單（带条件）
    @Override
    public Page<TemProduct> selectTemProductPageByCont(TemProduct condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("temProductCreateTime", ExampleMatcher.GenericPropertyMatchers.contains());
        return  temProductRepository.findAll(Example.of(condition,matcher),pageable);
    }
    @Override
    public TemProduct getTemProductById(String temProductId) {
        return temProductRepository.findByTemProductId(temProductId);
    }


    //查询訂單数量(带条件)
    @Override
    public Long selectCountTemProductByCont(TemProduct condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  temProductRepository.count(Example.of(condition,matcher));
    }


    //查询訂單產品数量(带条件)
    @Override
    public Long selectCountTemProductProductByProductTypeId(String productTypeId) {
      return null;
    }

    @Override
    public List<RatioData> selectShoppingRatioByProductType() {
       return null;
    }

    @Override
    public List selectCountTemProductPaymentStatus() {
       return null;
    }

    @Override
    public List selectCountTemProductState(String adminId) {
        return null;
    }

    @Override
    public List<Long> selectCountTemProductByState(String chekckAdminType) {
        List<Long> countState = new ArrayList<>();
        countState.add(temProductRepository.count());
        countState.add(temProductRepository.countByState(ProjectConfig.TemProductState.Unaudited.getValue()));
        countState.add(temProductRepository.countByState(ProjectConfig.TemProductState.PASS.getValue()));
        countState.add(temProductRepository.countByState(ProjectConfig.TemProductState.AUDITFAILURE.getValue()));
        return countState;

    }



    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //添加訂單
    @Override
    @Transactional
    public void saveTemProduct(TemProduct temProduct)throws Exception {
        TemProduct oldTemProduct = temProductRepository.findByProductIdAndTemProductState(temProduct.getProductId(),ProjectConfig.TemProductState.Unaudited.getValue());
        if(oldTemProduct!=null){
            BeanUtils.copyNotNullProperties(temProduct,oldTemProduct);
        }else {
            oldTemProduct=temProduct;
        }
        temProductRepository.save(oldTemProduct);
    }



    //删除訂單
    @Override
    public void delTemProductByTemProductId(String temProductId) {
        temProductRepository.deleteById(temProductId);
    }
    //批量删除訂單
    @Override
    public void delAllTemProductByTemProductId(List<String> temProductIds) {
        temProductRepository.deleteAllByTemProductIdIn(temProductIds);
    }






    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////







    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////


    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}
