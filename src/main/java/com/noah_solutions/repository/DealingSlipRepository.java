package com.noah_solutions.repository;

import com.noah_solutions.entity.DealingSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface DealingSlipRepository extends JpaRepository<DealingSlip,String> {
    DealingSlip findByDeslNo(String deslNo);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update DealingSlip d set d.deslAState=:deslAstate, d.deslExplanation=:deslExplanation  where d.deslNo=:deslNo")
    void updateDeslAState(@Param("deslAstate") Integer deslAstate, @Param("deslNo") String deslNo, @Param("deslExplanation") String deslExplanation);



    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update DealingSlip d set d.deslPayRef =:deslPayRef, d.deslAState=:deslAstate, d.deslExplanation=:deslExplanation  where d.deslNo=:deslNo")
    void updateDeslAState(@Param("deslPayRef")String deslPayRef,@Param("deslAstate") Integer deslAstate, @Param("deslNo") String deslNo, @Param("deslExplanation") String deslExplanation);



//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("update DealingSlip d set  d.deslAState=?1, d.deslExplanation=:deslExplanation  where d.deslCreatTime<deslState and d.deslAState = 1")
//    void updateDeslState(String deslState,String deslExplanation);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update DealingSlip d set  d.deslAState=?2, d.deslExplanation=?3  where d.deslNo=?1 and d.deslAState=1")
    void updateDeslStateByDeslNo(String deslNo,Integer deslState,String deslExplanation);



    @Query("select d from DealingSlip d where d.deslCreatTime<?1 and d.deslAState = 1")
    List<DealingSlip> findDealingSlipByDateTime(String dateTime);

    @Query("select sum(d.deslAmount) from DealingSlip d where d.deslAState=2")
    String getTotalSum();
}
