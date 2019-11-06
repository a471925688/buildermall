package com.noah_solutions.repository;

import com.noah_solutions.entity.OrderDlsl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDlslRepository extends JpaRepository<OrderDlsl,String> {
    @Modifying
    @Query(value = "insert into order_dlsl values(?1,?2) ",nativeQuery = true)
    void inserOrderDlsl(String orderId,String deslId);
}
