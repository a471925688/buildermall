package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.entity.SysLog;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.ISysLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 2018 11 30 lijun
 * 權限相關控制器
 */
@RestController
@RequestMapping("sysLog")
public class SysLogController {
    @Resource
    private ISysLogService sysLogService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
//    //编辑日志页面
//    @GetMapping("/editSysLogView")
//    public ModelAndView editSysLogView(@RequestParam("sysLogId")Integer sysLogId){
//        return new ModelAndView("sysLog/sysLog/addOrUpdateSysLog","sysLogId",sysLogId);
//    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //根据条件分页获取日志信息
    @RequestMapping("/getSysLogPageByCont.do")
    public JSONObject getSysLogPageByCont(SysLog condition, @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit){
        Page<SysLog> sysLogs = sysLogService.selectSysLogPageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "logId")));
        return CodeAndMsg.SUCESS.getJSON(sysLogs.getContent()).fluentPut("count",sysLogs.getTotalElements());
    }

    //分页查询管理员登录日志(通过用户名)
    @RequestMapping("/getSysLogPageByUserName.do")
    public JSONObject getSysLogPageByUserName(@RequestParam("userName") String userName, @RequestParam("page") Integer page,
                                          @RequestParam("limit") Integer limit)throws Exception{
        Page<SysLog> sysLogs = sysLogService.selectSysLogPageByUserName(userName, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "logId")));
        return CodeAndMsg.SUCESS.getJSON(sysLogs.getContent()).fluentPut("count",sysLogs.getTotalElements());
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////



}
