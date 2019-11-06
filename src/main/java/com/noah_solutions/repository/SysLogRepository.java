package com.noah_solutions.repository;

import com.noah_solutions.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogRepository extends JpaRepository<SysLog,String> {
//    SysLog findByLogId(Integer logId);
}
