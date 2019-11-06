package com.noah_solutions.repository;

import com.noah_solutions.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSize,String> {
    void deleteAllByProductId(String productId);
    Boolean existsByProductSizeId(String productSizeId);
    ProductSize findFirstByOrderByProductSizeIdDesc();
}
