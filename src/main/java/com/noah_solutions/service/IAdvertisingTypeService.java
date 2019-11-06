package com.noah_solutions.service;

import com.noah_solutions.bean.RatioData;
import com.noah_solutions.entity.Order;
import com.noah_solutions.entity.OrderRecord;
import com.noah_solutions.entity.AdvertisingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAdvertisingTypeService {

    //根据條件分頁获取所有信息
    Page<AdvertisingType> selectAdvertisingTypePageByCont(AdvertisingType condition, Pageable pageable);
    //根據條件查詢所有廣告類型
    List<AdvertisingType> selectAllAdvertisingTypeByCont(AdvertisingType condition);

    //根据条件获取总数
    Long selectCountAdvertisingTypeByCont(AdvertisingType condition);
    //获取总数
    Long selectCountAdvertisingType();
    //通过id查询单个廣告類型
    AdvertisingType selectAdvertisingTypeByAdvertisingTypeId(String advertisingTypeId);
    //保存廣告類型
    void saveAdvertisingType(AdvertisingType advertisingType,List<String> perIds)throws Exception;

    //删除单个廣告類型
    void delAdvertisingTypeByAdvertisingTypeId(String advertisingTypeId);
    //批量删除廣告類型
    void delAllAdvertisingTypeByAdvertisingTypeId(List<String> advertisingTypeIds);

    List<AdvertisingType> selectAllAdvertisingType();
}
