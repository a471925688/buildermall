package com.noah_solutions.repository;

import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.TemProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface TemProductRepository extends JpaRepository<TemProduct,String> {

    void deleteAllByTemProductIdIn(List<String> temProductId);

    TemProduct findByTemProductId(String temProductId);


    @Query("select count(tp.temProductId) from TemProduct tp group by tp.temProductState order by tp.temProductState")
    List<Long> countGroupByState();

    @Query("select count(tp.temProductId) from TemProduct tp where tp.temProductState=?1")
    Long countByState(Integer temProductState);

    TemProduct findByProductIdAndTemProductState(String productId,Integer productState);

    @Modifying
    @Query("update TemProduct tp set  tp.temProductState=?1 where tp.temProductId = ?2")
    void updateTemProductState(Integer temProductState,String temProductId);
}