package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.pojo.CartUser;
import com.noah_solutions.service.*;
import com.noah_solutions.service.impl.AdminServiceImpl;
import com.noah_solutions.thirdPartyInterface.DeBangExpress;
import com.noah_solutions.thirdPartyInterface.Paydollar;
import com.noah_solutions.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

import static com.noah_solutions.common.ProjectConfig.PayDollarCurType.HKD;
import static com.noah_solutions.ex.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/text")
public class TextController {
    @Resource
    private IOrderService orderService;
    @Resource
    private IProductTypeService productTypeService;
    @Resource
    private IDealingSlipService dealingSlipService;
    @Resource
    private IEmailService emailService;
    @Resource
    private IPlaceService placeService;
    @Resource
    private IProductService productService;

    @Resource
    private ICartService cartService;

    @Resource
    private AdminServiceImpl adminService;



    @PostMapping("/findOrderIdByNotEvaluate.do")
    public JSONObject findOrderIdByNotEvaluate(){
        return SUCESS.getJSON(orderService.findOrderIdByNotEvaluate());
}


    @PostMapping("/queryOrder.do")
    public JSONObject queryOrder(@RequestParam("orderNo") String orderNo){
        return Paydollar.queryOrder(HKD.getValue(),orderNo);
    }
    @PostMapping("/getInitCartData.do")
    public JSONObject getInitCartData(@RequestParam("userId") String userId){
        com.noah_solutions.entity.Admin admin = adminService.selectAdminByAdminId(userId);
        return SUCESS.getJSON(cartService.getInitCartData(new CartUser(admin.getAdminId(),admin.getAdminRealName(),admin.getCartUser().getSign(),admin.getAdminHeadPortraitUrl(),admin.getCartUser().getStatus())));
    }



    @PostMapping("/refundOrder.do")
    public JSONObject refundOrder(@RequestParam("payRef") String payRef, @RequestParam(value = "amount",required = false) Double amount){
        return Paydollar.refundOrder(HKD.getValue(),payRef,amount);
    }

    @PostMapping("/cancellationOrder.do")
    public JSONObject cancellationOrder(@RequestParam("payRef") String payRef){
        return Paydollar.cancellationOrder(HKD.getValue(),payRef);
    }

    @PostMapping("/selectUntreatedOrder.do")
    public JSONObject selectUntreatedOrder(){
        return SUCESS.getJSON(orderService.findUntreatedOrder());
    }

    @PostMapping("/findFirst5OrderByProductSelloutNumDesc.do")
    public JSONObject findFirst5OrderByProductSelloutNumDesc(){
        return SUCESS.getJSON(productTypeService.findFirst5OrderByProductSelloutNumDesc());
    }

    @PostMapping("/sendReceipt.do")
    public JSONObject sendReceipt(String deslNo)throws Exception{
        emailService.sendReceipt(dealingSlipService.selectDealingSlipByNo(deslNo));
        return SUCESS.getJSON();
    }
    @PostMapping("/shipmentOrder.do")
    public JSONObject shipmentOrder(String orderId)throws Exception{
        emailService.shipmentOrder(orderId);
        return SUCESS.getJSON();
    }

    @PostMapping("/getNewTraceQuery.do")
    public JSONObject getNewTraceQuery(String mailNo)throws Exception{
        return SUCESS.getJSON(DeBangExpress.getNewTraceQuery(mailNo));
    }
    @PostMapping("/translationPlace.do")
    public JSONObject translationPlace()throws Exception{
        placeService.translationPlace();
        return SUCESS.getJSON();
    }

    @PostMapping("/initPlaceDetailsEng.do")
    public JSONObject initPlaceDetailsEng()throws Exception{
        placeService.initPlaceDetailsEng();
        return SUCESS.getJSON();
    }

    @PostMapping("/initProductTypeDetailsEng.do")
    public JSONObject initProductTypeDetailsEng()throws Exception{
        productTypeService.initProductTypeDetailsEng();
        return SUCESS.getJSON();
    }

    @PostMapping("/updateOrderStateByDateTime.do")
    public JSONObject updateOrderStateByDateTime(String date){
        String dataTime = DateUtil.getNextDay(new Date(),date);
        orderService.updateOrderStateByDateTime(dataTime);
        return SUCESS.getJSON();
    }

    @PostMapping("/updateDeslAState")
    public JSONObject updateDeslAState(String date){
        String dataTime = DateUtil.getNextDay(new Date(),date);
        dealingSlipService.updateDeslAState(dataTime);
        return SUCESS.getJSON();
    }

    //获取交易总额
    @PostMapping("/getTotalSum.do")
    public JSONObject getTotalSum(){
        return CodeAndMsg.SUCESS.getJSON(dealingSlipService.getTotalSum());
    }


    //获取交易总额
    @PostMapping("/initProductType.do")
    public JSONObject initProductType(){
        productService.initProductType();
        return CodeAndMsg.SUCESS.getJSON();
    }

    //查询所有產品單位
    @GetMapping("/getAllProductUnit.do")
    public JSONObject getAllProductUnit()throws Exception {
        return CodeAndMsg.SUCESS.getJSON(productService.selectAllProductUnit());
    }
    //初始化聊天用戶
    @GetMapping("/initCartUser.do")
    public JSONObject initCartUser(){
        cartService.initCartUser();
        return SUCESS.getJSON();
    }

    //查询所有產品單位父ids
    @GetMapping("/initProductTypePraentIds.do")
    public JSONObject initProductTypePraentIds()throws Exception {
        productTypeService.initProductTypePraentIds();
        return CodeAndMsg.SUCESS.getJSON();
    }



//    @PostMapping("/selectRefundOrder.do")
//    public JSONObject selectRefundOrder(Integer page,Integer limit){
//        return CodeAndMsg.SUCESS.getJSON(orderService.findRefundOrder(PageRequest.of(page-1,limit)));
//    }

}
