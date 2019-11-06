package com.noah_solutions.service;

import com.noah_solutions.entity.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISysLogService {
    //保存日志
    void saveSysLog(SysLog productSize);
    //分页查询日志信息(带条件)
    Page<SysLog> selectSysLogPageByCont(SysLog condition, Pageable pageable);
    //分页查询管理员登录日志(用户名)
    Page<SysLog> selectSysLogPageByUserName(String userName, Pageable pageable)throws Exception;

}
