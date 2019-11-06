package com.noah_solutions.repository;

import com.noah_solutions.entity.Place;
import com.noah_solutions.entity.Product;
import com.noah_solutions.entity.ProductBrowseRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 2019 1 9 lijun
 */
@Repository
public interface ProductBrowseRecordsRepository extends JpaRepository<ProductBrowseRecords,String> {

}
