package com.noah_solutions.repository;

import com.noah_solutions.entity.OrderProduct;
import com.noah_solutions.entity.ProductEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,String> {

    @Query("select count(op) from OrderProduct op where op.productTypeId like  concat('%',?1,'%')")
    Long countByProductId(String productTypeId);

    OrderProduct findFirstByOrderByOrderProductIdDesc();


    @Query("select new ProductEvaluation(op.productId,op.orderId,op.userId) from OrderProduct op  where op.orderProductId = ?1")
    ProductEvaluation getProductEvaluationInfo(String orderProductId);

    //设置产品评价为已评价
    @Modifying
    @Query("update OrderProduct op set op.isEvaluation = (op.isEvaluation+1) where op.orderProductId=?1")
    void  updateOrderProductEvaluationState(String orderProductId);
}
