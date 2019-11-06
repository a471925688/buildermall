package com.noah_solutions.service;

import com.noah_solutions.entity.SysLog;
import com.noah_solutions.entity.System;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISystemService {
    System getSystem();
    void saveSystem(System system);

    String getSystemMaxAmount();
}
