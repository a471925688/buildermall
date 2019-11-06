package com.noah_solutions.repository;

import com.noah_solutions.entity.AdvertisingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisingTypeRepository extends JpaRepository<AdvertisingType,String> {
    AdvertisingType findByAdvertisingTypeId(String advertisingTypeId);
    List<AdvertisingType> findAllByAdvertisingTypeIdIn(List<String> advertisingTypeId);
    AdvertisingType findByAdvertisingTypeName(String advertisingTypeName);

    @Query("select a.advertisingTypeNumber from AdvertisingType a where a.advertisingTypeId=?1")
    Integer getMaxNumberById(String id);
}
