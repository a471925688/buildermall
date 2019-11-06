package com.noah_solutions.service.impl;

import com.noah_solutions.entity.System;
import com.noah_solutions.repository.SystemRepository;
import com.noah_solutions.service.ISystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 2018 12 12 lijun
 */
@Service("sysTemService")
public class SysTemServiceImpl implements ISystemService {

    @Resource
    private SystemRepository systemRepository;

    @Override
    public System getSystem() {
        return systemRepository.findAll().get(0);
    }

    @Override
    public void saveSystem(System system) {
        systemRepository.save(system);
    }

    @Override
    public String getSystemMaxAmount() {
        return null;
    }


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////



}
