package com.noah_solutions.service;

import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.Transit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITransitService {
    List<Transit> listByUserId(String userId);
    void add(Transit transit);
    void del(String id,String userId);
}
