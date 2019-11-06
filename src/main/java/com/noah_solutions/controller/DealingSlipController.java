package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.service.*;
import com.noah_solutions.thirdPartyInterface.DeBangExpress;
import com.noah_solutions.thirdPartyInterface.Paydollar;
import com.noah_solutions.util.DateUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

import static com.noah_solutions.common.ProjectConfig.PayDollarCurType.HKD;
import static com.noah_solutions.ex.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/desl")
public class DealingSlipController {

    @Resource
    private IDealingSlipService dealingSlipService;
    //获取交易总额
    @PostMapping("/getTotalSum.do")
    public JSONObject getTotalSum(){
        return CodeAndMsg.SUCESS.getJSON(dealingSlipService.getTotalSum());
    }


}
