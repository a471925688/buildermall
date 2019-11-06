package com.noah_solutions.repository;

import com.noah_solutions.entity.ProductEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductEvaluationRepository extends JpaRepository<ProductEvaluation,String> {
    @Query("select new ProductEvaluation (u.userRealName,u.login.loginUserName,pe) from ProductEvaluation pe join com.noah_solutions.entity.User u on u.userId=pe.userId where pe.productId=?1 and pe.isReview=?2 order by pe.productEvaluationId desc ")
    Page<ProductEvaluation> findAllInfoByProductIdAndIsReview(String productId,Boolean isReview, Pageable pageable);

    Boolean existsByOrderProductIdAndUserId(String orderProductId,String userId);

    @Query("select new ProductEvaluation (u.userRealName,u.login.loginUserName,pe) from ProductEvaluation pe join com.noah_solutions.entity.User u on u.userId=pe.userId where pe.userId=?1 and pe.orderProductId=?2 and pe.isReview=?3 order by pe.productEvaluationId asc")
    List<ProductEvaluation> findAllInfoByUserIdAndOrderProductIdAndIsReview(String userId, String orderProductId,Boolean isReview);
}