package com.noah_solutions.webcontroller;

import com.noah_solutions.bean.PayDollarDataFeedBean;
import com.noah_solutions.entity.DealingSlip;
import com.noah_solutions.entity.OrderRecord;
import com.noah_solutions.entity.System;
import com.noah_solutions.service.IDealingSlipService;
import com.noah_solutions.service.IEmailService;
import com.noah_solutions.service.IOrderService;
import com.noah_solutions.service.ISystemService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.noah_solutions.common.ProjectConfig.DealingSlipState.*;
import static com.noah_solutions.common.ProjectConfig.OrderPaymentStatus.PAID;
import static com.noah_solutions.common.ProjectConfig.OrderRecordType.PAYED;
import static com.noah_solutions.common.ProjectConfig.OrderState.SHIP;
import static com.noah_solutions.common.ProjectConfig.PayDollarSuccessCode.SUCCESS;


@RestController
@RequestMapping("/pay")
public class WebPayDollarContoller {

    @Resource
    private IDealingSlipService dealingSlipService;
    @Resource
    private IOrderService orderService;
    @Resource
    private IEmailService emailService;

    @Resource
    private ISystemService systemService;
//    @GetMapping(value = "/paymentDataFeed_Hi1joi83ks71uLID")
//    public String paymentDataFeed_Hi1joi83ks71uLID(@ModelAttribute("payDollarDataFeedBean") PayDollarDataFeedBean bean,HttpSession session) {
//        System.out.println("insert data feed(GET)");
//        return "OK";
//    }


    @PostMapping(value = "/paymentDataFeed_Hi1joi83ks71uLID")
    public String paymentDataFeed(@ModelAttribute("payDollarDataFeedBean") PayDollarDataFeedBean bean,
                                  HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session)throws Exception {
        DealingSlip dealingSlip = dealingSlipService.selectDealingSlipByNo(bean.getRef());
        if(bean.getSuccesscode() == SUCCESS.getValue()&&dealingSlip!=null){
            if((dealingSlip.getDeslAmount()+"").equals(bean.getAmt()+"")&&dealingSlip.getDeslCur().equals(bean.getCur())){
                System system = systemService.getSystem();

                dealingSlipService.updateDeslAState(bean.getPayRef(),PAYSUCCESS.getValue(),bean.getRef(),PAYSUCCESS.queryValue());
                orderService.updateOrderByDealNo(SHIP.getValue(),PAID.getValue(),bean.getRef());
                orderService.findOrderIdByDeslNo(bean.getRef()).forEach(v->orderService.saveOrderRecord(new OrderRecord(PAYED.getValue(),null,v.getUserId(),v.getOrderId(),PAYED.queryValue())));
                emailService.sendReceipt(dealingSlip);
                if(dealingSlip.getDeslAmount()>Double.valueOf(system.getSystemMaxAmount())) {
                    emailService.excessReminder(dealingSlip,system);
                }
            }else {
//                System.out.println(Paydollar.cancellationOrder(Integer.valueOf(bean.getCur()),bean.getPayRef()).toString());//撤銷支付
                dealingSlipService.updateDeslAState(null,MISMATCH.getValue(),bean.getRef(),MISMATCH.queryValue());
            }
        }else{
//            if(dealingSlip==null){
//                System.out.println(Paydollar.cancellationOrder(Integer.valueOf(bean.getCur()),bean.getPayRef()).toString());//撤銷支付
//            }
            dealingSlipService.updateDeslAState(null,PAYFAILURE.getValue(),bean.getRef(),"");
        }
        return "OK";
    }
}
