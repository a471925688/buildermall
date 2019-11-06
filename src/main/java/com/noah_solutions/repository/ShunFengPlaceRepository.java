package com.noah_solutions.repository;

import com.noah_solutions.entity.ShunFengPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShunFengPlaceRepository extends JpaRepository<ShunFengPlace,String> {
    // 通過本地庫id查詢對應的順豐地址信息
    ShunFengPlace findByPlaceId(String placeId);
    //通過父id查詢所有
    List<ShunFengPlace> findAllBySfParentId(String sfParentId);
    //通過父code查詢地址
    List<ShunFengPlace> findAllBySfParentCode(String parentCode);
    //更加地址类型查找所有顺风地址
    List<ShunFengPlace> findAllByPlaceTypeAndSfLevel(Integer type,String lv);
}
