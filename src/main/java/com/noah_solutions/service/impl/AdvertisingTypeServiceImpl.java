package com.noah_solutions.service.impl;

import com.noah_solutions.entity.AdvertisingType;
import com.noah_solutions.repository.AdvertisingTypeRepository;
import com.noah_solutions.service.IAdvertisingTypeService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.DateUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("advertisingTypeService")
public class AdvertisingTypeServiceImpl implements IAdvertisingTypeService {
    @Resource
    private AdvertisingTypeRepository advertisingTypeRepository;





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //根据条件分页获取所有廣告類型
    @Override
    public Page<AdvertisingType> selectAdvertisingTypePageByCont(AdvertisingType condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        advertisingTypeRepository.count(Example.of(condition,matcher));
        return advertisingTypeRepository.findAll(Example.of(condition,matcher),pageable);
    }
    //根据条件查询所有廣告類型
    @Override
    public List<AdvertisingType> selectAllAdvertisingTypeByCont(AdvertisingType condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return advertisingTypeRepository.findAll(Example.of(condition,matcher));
    }

    //查询单个廣告類型
    @Override
    public AdvertisingType selectAdvertisingTypeByAdvertisingTypeId(String advertisingTypeId) {
        return advertisingTypeRepository.findByAdvertisingTypeId(advertisingTypeId);
    }

    //根據條件查询总数
    @Override
    public Long selectCountAdvertisingTypeByCont(AdvertisingType condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  advertisingTypeRepository.count(Example.of(condition,matcher));
    }
    //查询总数
    @Override
    public Long selectCountAdvertisingType() {
        return  advertisingTypeRepository.count();
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //保存廣告類型
    @Override
    public void saveAdvertisingType(AdvertisingType advertisingType,List<String> perIds)throws Exception {
        advertisingType.setAdvertisingTypeUpdateTime(DateUtil.getStringDate());
        advertisingTypeRepository.save(advertisingType);
    }

    //删除廣告類型
    @Override
    public void delAdvertisingTypeByAdvertisingTypeId(String advertisingTypeId) {
        advertisingTypeRepository.deleteById(advertisingTypeId);
    }
    //批量删除廣告類型
    @Override
    public void delAllAdvertisingTypeByAdvertisingTypeId(List<String> advertisingTypeIds) {
        List<AdvertisingType> advertisingTypes = advertisingTypeRepository.findAllByAdvertisingTypeIdIn(advertisingTypeIds);
        //删除所有
        advertisingTypeRepository.deleteAll(advertisingTypes);
    }

    @Override
    public List<AdvertisingType> selectAllAdvertisingType() {
        return advertisingTypeRepository.findAll();
    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //处理更新单个廣告類型
    private void updateAdvertisingType(AdvertisingType updateAdvertisingType){
        AdvertisingType advertisingType = advertisingTypeRepository.getOne(updateAdvertisingType.getAdvertisingTypeId());
        BeanUtils.copyNotNullProperties(updateAdvertisingType,advertisingType);
        advertisingTypeRepository.save(advertisingType);
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}
