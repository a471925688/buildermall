package com.noah_solutions.service.impl;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Brand;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.BrandRepository;
import com.noah_solutions.service.IBrandService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.FileUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service("brandService")
public class BrandServiceImpl implements IBrandService {
    @Resource
    private BrandRepository brandRepository;




    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有品牌
    @Override
    public List<Brand> selectAllBrand() {
        return brandRepository.findAll();
    }
    //通過管理員id查詢所有品牌
    @Override
    public List<Brand> selectAllBrandByAdmin(String adminId) {
        return brandRepository.findAllByAdminId(adminId);
    }

    //分页查询所有品牌（带条件）
    @Override
    public Page<Brand> selectBrandPageByCont(Brand condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("brandCreateTime", ExampleMatcher.GenericPropertyMatchers.contains());
        return  brandRepository.findAll(Example.of(condition,matcher),pageable);
    }


    //查询品牌(通过品牌id)
    @Override
    public Brand selectBrandByBrandId(String brandId) {
        return brandRepository.findByBrandId(brandId);
    }



    //查询品牌数量(带条件)
    @Override
    public Long selectCountBrandByCont(Brand condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  brandRepository.count(Example.of(condition,matcher));
    }
    //查詢國內的品牌數量
    @Override
    public Long selectCountBrandIsCN() {
        return brandRepository.countByBrandCountry(ProjectConfig.Country.CHINA.getValue());
    }
    //查詢國外品牌數量
    @Override
    public Long selectCountBrandIsNotCN() {
        return brandRepository.countByBrandCountryIsNot(ProjectConfig.Country.CHINA.getValue());
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////

    //添加品牌
    @Override
    @Transactional
    public void addBrand(Brand brand)throws Exception {
        //判断品牌名稱是否重复
        if(brandRepository.existsByBrandName(brand.getBrandName()))
            throw new CustormException(CodeAndMsg.BRAND_ADDERROR);
        //如果图标不为空则将临时文件拷贝到目标文件并删除临时文件返回文件路径
        saveBrand(brand);
    }
    //更新品牌
    @Override
    @Transactional
    public void updateBrand(Brand brand)throws Exception {
        Brand BrandEntity = brandRepository.getOne(brand.getBrandId());
        BeanUtils.copyNotNullProperties(brand,BrandEntity);
        saveBrand(BrandEntity);
    }
    
    //删除品牌
    @Override
    public void delBrandByBrandId(String brandId) {
        brandRepository.deleteById(brandId);
    }
    //批量删除品牌
    @Override
    public void delAllBrandByBrandId(List<String> brandIds) {
        List<Brand> brands = brandRepository.findAllByBrandIdIn(brandIds);
        //删除所有
        brandRepository.deleteAll(brands);
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    private void saveBrand(Brand brand)throws Exception{
        if(!StringUtils.isEmpty(brand.getBrandLogoPath()))
            FileUtils.copyFile(ProjectConfig.FILE_TEMPORARY_DIRECTORY+brand.getBrandLogoPath(),ProjectConfig.FILE_IMAGE_DIRECTORY+brand.getBrandLogoPath());
        //保存品牌
        brandRepository.save(brand);
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}
