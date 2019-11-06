package com.noah_solutions.service.impl;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Login;
import com.noah_solutions.entity.Transit;
import com.noah_solutions.entity.chat.CartUser;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.AdminRepository;
import com.noah_solutions.repository.LoginRepository;
import com.noah_solutions.repository.RoleRepository;
import com.noah_solutions.repository.TransitRepository;
import com.noah_solutions.repository.cart.CartUserRepository;
import com.noah_solutions.service.IAdminService;
import com.noah_solutions.service.ITransitService;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.MD5Util;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service("transitService")
public class TransitServiceImpl implements ITransitService {

    @Resource
    private TransitRepository transitRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////



    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    @Override
    public List<Transit> listByUserId(String userId){
        return transitRepository.findAllByUserId(userId);
    }



    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////

    @Override
    @Transactional
    public void add(Transit transit) {
        transitRepository.save(transit);
    }

    @Override
    @Transactional
    public void del(String id,String userId) {
        transitRepository.deleteById(id);
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}
