package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.thirdPartyInterface.BaiduTranslation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dataDocking")
public class DataDockingController {
//    @Resource
//    private IDataDockingService dataDockingService;
//
//
//    @RequestMapping("/dockingSuplierUser")
//    public JSONObject dockingSuplierUser()throws Exception{
//        dataDockingService.dockingSuppliers();
//        return CodeAndMsg.SUCESS.getJSON();
//    }


    @RequestMapping("/text")
    public JSONObject text(String query)throws  Exception{
        return  CodeAndMsg.SUCESS.getJSON(BaiduTranslation.query(query));
    }
}
