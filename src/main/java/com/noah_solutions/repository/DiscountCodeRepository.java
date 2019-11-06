package com.noah_solutions.repository;

import com.noah_solutions.entity.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode,String> {
    //根据id查询权限
    DiscountCode findByDiscountCodeId(String discountCodeId);
    //通過多個id查詢
    List<DiscountCode> findAllByDiscountCodeIdIn(List<String> discountCodeId);
    //通过優惠碼查询優惠碼信息
    DiscountCode findByDiscountCodeNo(String discountCodeNo);
    //检测優惠碼是否存在
    Boolean existsByDiscountCodeNo(String discountCodeNo);

    //改变優惠碼状态
    @Modifying
    @Query("update DiscountCode a set a.discountCodeIsProhibit =?1 where a.discountCodeId = ?2")
    void updateDiscountCodeIsProhibit(Boolean discountCodeIsProhibit, String discountCodeId);




}
