package com.noah_solutions.repository;

import com.noah_solutions.entity.ProductConfigSpecifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductConfigSpecificationsRepository extends JpaRepository<ProductConfigSpecifications,String> {
}
