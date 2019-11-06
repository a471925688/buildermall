package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.IdManage;
import com.noah_solutions.ex.CodeAndMsg;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * id獲取控制器
 */
@RestController
public class IdManageController {
    @Resource
    private IdManage idManage;
    @PostMapping("/getProductNextId.do")
    public JSONObject getProductNextId(){
        return CodeAndMsg.SUCESS.getJSON(idManage.getProductNextId());
    }
    @PostMapping("/getProductSizeNextId.do")
    public JSONObject getProductSizeNextId(){
        return CodeAndMsg.SUCESS.getJSON(idManage.getProductSizeNextId());
    }
    @PostMapping("/getProductSpecificationsNextId.do")
    public JSONObject getProductSpecificationsNextId(){
        return CodeAndMsg.SUCESS.getJSON(idManage.getProductSpecificationsNextId());
    }
    @PostMapping("/getProductConfigNextId.do")
    public JSONObject getProductConfigNextId(){
        return CodeAndMsg.SUCESS.getJSON(idManage.getProductConfigNextId());
    }
}
