package com.noah_solutions.repository;

import com.noah_solutions.entity.SupplierApply;
import com.noah_solutions.entity.SupplierApplyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierApplyProductRepository extends JpaRepository<SupplierApplyProduct,String> {

}
