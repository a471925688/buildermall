package com.noah_solutions.repository;

import com.noah_solutions.entity.OrderAddr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAddrRepository extends JpaRepository<OrderAddr,String> {

}
