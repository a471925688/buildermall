package com.noah_solutions.repository;

import com.noah_solutions.entity.ProductConfig;
import com.noah_solutions.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductConfigRepository extends JpaRepository<ProductConfig,String> {
    void deleteAllByProductId(String productId);
    Boolean existsByProductConfigId(String productConfig);
    ProductConfig findFirstByOrderByProductConfigIdDesc();
    void deleteAllByProductConfigIdIn(List productConfigIds);
    ProductConfig findByProductConfigId(String productConfigId);

    //減少庫存
    @Modifying
    @Query("update ProductConfig pc set pc.productConfigStock = pc.productConfigStock-?1 where pc.productConfigId=?2")
    void subtractProductConfig(String num,String productConfigId);


}
