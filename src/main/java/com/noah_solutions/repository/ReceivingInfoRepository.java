package com.noah_solutions.repository;

import com.noah_solutions.entity.ReceivingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivingInfoRepository extends JpaRepository<ReceivingInfo,String> {
//    SysLog findByLogId(Integer logId);
    //根据用户id批量删除
    void deleteAllByUserIdIn(List<String> userIds);
    //根据用户id删除
    void deleteAllByUserId(String userId);
}
