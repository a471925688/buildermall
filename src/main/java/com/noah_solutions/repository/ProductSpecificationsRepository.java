package com.noah_solutions.repository;

import com.noah_solutions.entity.ProductSpecifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSpecificationsRepository extends JpaRepository<ProductSpecifications,String> {
    void deleteAllByProductId(String productId);
    ProductSpecifications findFirstByOrderByProductSpecificationsIdDesc();
    Boolean existsByProductId(String productId);
    Boolean existsByProductSpecificationsId(String specificationsId);

}
