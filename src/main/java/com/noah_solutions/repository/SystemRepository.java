package com.noah_solutions.repository;

import com.noah_solutions.entity.SysLog;
import com.noah_solutions.entity.System;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends JpaRepository<System,String> {
    @Query("select sys.systemMaxAmount  from System  sys")
    String selectSystemMaxAmount();
}
