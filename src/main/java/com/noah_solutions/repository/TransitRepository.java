package com.noah_solutions.repository;

import com.noah_solutions.entity.Brand;
import com.noah_solutions.entity.Transit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransitRepository extends JpaRepository<Transit,String> {
    List<Transit> findAllByUserId(String userId);
}
