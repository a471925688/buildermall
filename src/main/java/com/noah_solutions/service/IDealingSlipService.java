package com.noah_solutions.service;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.entity.DealingSlip;


public interface  IDealingSlipService {
    DealingSlip selectDealingSlipByNo(String no);
    void  updateDeslAState(String deslPayRef,Integer deslAstate,String deslNo,String deslExplanation);
    void  updateDeslAState(String datetime);

    String getTotalSum();
}
