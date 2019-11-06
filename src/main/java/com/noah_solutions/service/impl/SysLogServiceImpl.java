package com.noah_solutions.service.impl;

import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.SysLog;
import com.noah_solutions.repository.SysLogRepository;
import com.noah_solutions.service.ISysLogService;
import com.noah_solutions.util.AddressUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2018 12 12 lijun
 */
@Service("sysLogService")
public class SysLogServiceImpl implements ISysLogService {

    @Resource
    private SysLogRepository sysLogRepository;




    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //分页查询日志信息(带条件)
    @Override
    public Page<SysLog> selectSysLogPageByCont(SysLog condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  sysLogRepository.findAll(Example.of(condition,matcher),pageable);
    }
    //分页查询管理员登录日志(用户名)
    @Override
    public Page<SysLog> selectSysLogPageByUserName(String userName, Pageable pageable)throws Exception {
        Map<String,String> addrMap = new HashMap();
        SysLog sysLog = new SysLog();
        //设置请求控制器名
        sysLog.setLogController("IndexController");
        //设置请求方法名
        sysLog.setLogMethod("login");
        //设置管理员类型
        sysLog.setLogUserType(ProjectConfig.AdminType.ADMIN.getValue());
        //设置操作人id
        sysLog.setLogUserName(userName);
        ExampleMatcher matcher = ExampleMatcher.matching();
        Page<SysLog> page = sysLogRepository.findAll(Example.of(sysLog,matcher),pageable);
        List<SysLog> logs = page.getContent();
        //查询ip对应的地区
        for (int i =0;i<logs.size();i++){
            if(logs.get(i).getLogIp().equals("localhost")){
                logs.get(i).setLogAddr("本机");
            }else {
                String addrData = "";
                if(addrMap.get(logs.get(i).getLogIp())!=null){
                    addrData=addrMap.get(logs.get(i).getLogIp());
                }else {
                    addrData = AddressUtils.getAddresses(logs.get(i).getLogIp());
                    addrMap.put(logs.get(i).getLogIp(),addrData);
                }
                logs.get(i).setLogAddr(addrData);
            }

        }

        return  page;
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////



    //保存日志
    @Override
    public void saveSysLog(SysLog sysLog) {
        sysLogRepository.save(sysLog);
    }
    //分页查询所有管理员（带条件）

}
