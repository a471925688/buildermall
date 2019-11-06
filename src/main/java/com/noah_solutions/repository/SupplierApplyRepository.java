package com.noah_solutions.repository;

import com.noah_solutions.entity.Brand;
import com.noah_solutions.entity.SupplierApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierApplyRepository extends JpaRepository<SupplierApply,String> {
    @Query("select count(sa.state) from SupplierApply sa where sa.state=?1")
    Long countByState(Integer state);

    @Modifying
    @Query("update SupplierApply sa set sa.state = ?2 where sa.id in (?1)")
    void updateState(List<String> ids,Integer state);
}
