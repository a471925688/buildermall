package com.noah_solutions.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.DealingSlip;
import com.noah_solutions.repository.DealingSlipRepository;
import com.noah_solutions.service.IDealingSlipService;
import com.noah_solutions.thirdPartyInterface.Paydollar;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import static com.noah_solutions.common.ProjectConfig.DealingSlipState.ABNORMAL;
import static com.noah_solutions.common.ProjectConfig.PayDollarSuccessCode.SUCCESS;


@Service
public class DealingSlipServiceImpl implements IDealingSlipService {

    @Resource
    private DealingSlipRepository dealingSlipRepository;

    @Override
    public DealingSlip selectDealingSlipByNo(String no) {
        return dealingSlipRepository.findByDeslNo(no);
    }

    @Override
    public void updateDeslAState(String deslPayRef,Integer deslAstate, String deslNo,String deslExplanation) {
        dealingSlipRepository.updateDeslAState(deslPayRef,deslAstate,deslNo,deslExplanation);
    }

    @Override
    public void updateDeslAState(String datetime) {
        dealingSlipRepository.findDealingSlipByDateTime(datetime).forEach(v->{
            JSONObject data = Paydollar.queryOrder(Integer.valueOf(v.getDeslCur()),v.getDeslNo());
            System.out.println("交易查询结果:"+data);
            if(!StringUtils.isEmpty(data)&&data.getInteger("prc") == SUCCESS.getValue()){
                dealingSlipRepository.updateDeslStateByDeslNo(v.getDeslNo(), ABNORMAL.getValue(), ABNORMAL.queryValue());
            }else if(StringUtils.isEmpty(data)||data.getInteger("error")==null){
                dealingSlipRepository.updateDeslStateByDeslNo(v.getDeslNo(), ProjectConfig.DealingSlipState.TIMEOUT.getValue(), ProjectConfig.DealingSlipState.TIMEOUT.queryValue());
            }
       });

    }

    @Override
    public String getTotalSum() {
        String data = dealingSlipRepository.getTotalSum();
        if(!StringUtils.isEmpty(data)){
            data = String.format("%.2f", Double.valueOf(data));
        }
        return data;
    }

//    public void dsad(){
//        List<DealingSlip> dealingSlips =orderRepository.findByOrderIdAndDeslAState(orderRecord.getOrderId(), ProjectConfig.DealingSlipState.PAYSUCCESS.getValue());
//        if(dealingSlips.size()>0){
//            DealingSlip dealingSlip = dealingSlips.get(0);
//            JSONObject jsonObject = Paydollar.refundOrder(Integer.valueOf(dealingSlip.getDeslCur()),dealingSlip.getDeslPayRef(),null);
//            if(jsonObject.getInteger("resultCode").intValue()==-1){
//                throw new CustormException(CodeAndMsg.ERROR);
//            }else {
//                orderRepository. updateOrderState(orderRecord.getOrderId(),ProjectConfig.OrderState.CANCELLED.getValue());//更新訂單狀態
//            }
//        }
//    }
}
