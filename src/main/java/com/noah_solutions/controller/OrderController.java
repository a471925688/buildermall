package com.noah_solutions.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.Admin;
import com.noah_solutions.entity.Order;
import com.noah_solutions.entity.OrderRecord;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.noah_solutions.common.ProjectConfig.LOGIN_ADMIN;
import static com.noah_solutions.ex.CodeAndMsg.ORDER_REFUND;

/**
 * 2018 11 30 lijun
 * 管理員相關控制器
 */
@RestController
@RequestMapping("order")
public class OrderController {
    @Resource
    private IOrderService orderService;




    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //查看訂單詳情
    @GetMapping("/orderDetailView")
    public ModelAndView orderDetailView(@RequestParam("orderId")String orderId){
        return new ModelAndView("transaction/order/orderDetailView","orderId",orderId);
    }
    //编辑訂單页面
    @GetMapping("/editOrderView")
    public ModelAndView editOrderView(@RequestParam("orderId")String orderId){
        return new ModelAndView("transaction/order/addOrUpdateOrder","orderId",orderId);
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //查询所有訂單
    @RequestMapping("/getAllOrder.do")
    public JSONObject getAllOrder(){
        return CodeAndMsg.SUCESS.getJSON(orderService.selectAllOrder());
    }
    //根据条件分页获取訂單信息
    @RequestMapping("/getOrderPageByCont.do")
    public JSONObject getOrderPageByCont(Order condition, @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                         HttpSession session){
        condition.setAdminId(chekckAdminType(session));
        Page<Order> orders = orderService.selectOrderPageByCont(condition, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC, "orderId")));
        return CodeAndMsg.SUCESS.getJSON(orders.getContent()).fluentPut("count",orders.getTotalElements());
    }

    //查询訂單数量
    @RequestMapping("/getCountOrder.do")
    public JSONObject getCountOrder(){
        return CodeAndMsg.SUCESS.getJSON(orderService.selectCountOrderByCont(new Order()));
    }
    //查询訂單数量
    @RequestMapping("/getCountOrderByCont.do")
    public JSONObject getCountOrderByCont(Order order){
        return CodeAndMsg.SUCESS.getJSON(orderService.selectCountOrderByCont(order));
    }
    //查询單個訂單
    @RequestMapping("/getOrderById.do")
    public JSONObject getOrderById(@RequestParam("orderId") String orderId){
        return CodeAndMsg.SUCESS.getJSON(orderService.getOrderById(orderId));
    }
    //根據類型查询售出產品数量
    @RequestMapping("/getCountOrderProductByProductTypeId.do")
    public JSONObject getCountOrderProductByProductTypeId(@RequestParam("productType") String productType){
        return CodeAndMsg.SUCESS.getJSON(orderService.selectCountOrderProductByProductTypeId(productType));
    }
    //獲取購物比例（產品類型）
    @RequestMapping("/getShoppingRatioByProductType.do")
    public JSONObject getShoppingRatioByProductType(){
        return CodeAndMsg.SUCESS.getJSON(orderService.selectShoppingRatioByProductType());
    }

    //分組獲取訂單數量（付款狀態）
    @RequestMapping("/getCountOrderPaymentStatus.do")
    public JSONObject getCountOrderPaymentStatus(){
        return CodeAndMsg.SUCESS.getJSON(orderService.selectCountOrderPaymentStatus());
    }

    //分組獲取訂單數量（訂單狀態）
    @RequestMapping("/getCountOrderState.do")
    public JSONObject getCountOrderState(HttpSession session){
        return CodeAndMsg.SUCESS.getJSON(orderService.selectCountOrderState(chekckAdminType(session)));
    }


    //分頁查詢所有退款訂單
    @GetMapping("/selectRefundOrder.do")
    public JSONObject selectRefundOrder(@RequestParam(value = "orderNo",required = false) String orderNo,
                                        @RequestParam(value = "orderCreateTime",required = false) String orderCreateTime,
                                        @RequestParam(value = "orderRefundState",required = false) String orderRefundState,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                        HttpSession session){
        Page<Order>  orders = orderService.findRefundOrder(chekckAdminType(session),orderNo,orderCreateTime,orderRefundState,PageRequest.of(page-1,limit));
        return CodeAndMsg.SUCESS.getJSON(orders.getContent()).fluentPut("count",orders.getTotalElements());
    }
    //分組獲取訂單數量（訂單狀態）
    @RequestMapping("/getCountOrderRefundState.do")
    public JSONObject getCountOrderRefundState(HttpSession session){
        return CodeAndMsg.SUCESS.getJSON(orderService.selectCountOrderRefundState(chekckAdminType(session)));
    }
    //查询快递轨迹
    @PostMapping("/getNewTraceQuery.do")
    public JSONObject getNewTraceQuery(@RequestParam("orderExpressNo") String orderExpressNo)throws Exception{
        return CodeAndMsg.SUCESS.getJSON(orderService.getNewTraceQuery(orderExpressNo));
    }
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////









    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //添加訂單
    @RequestMapping("/addOrder.do")
    public JSONObject addOrder(Order order)throws Exception {
        orderService.addOrder(order);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }

    //退款處理
    @RequestMapping("/refundHandleOrder.do")
    public JSONObject refundHandleOrder(OrderRecord orderRecord,HttpSession session)throws Exception {
        if(-1==orderService.refundHandleOrder(((Admin)session.getAttribute(ProjectConfig.LOGIN_ADMIN)).getAdminId(),orderRecord))
            throw new CustormException(ORDER_REFUND);
        return CodeAndMsg.HANDLESUCESS.getJSON();
    }
    //設置運費
    @RequestMapping("/quotation.do")
    public JSONObject quotation(String orderId,String freight,HttpSession session)throws Exception {
        orderService.quotation(orderId,freight,((Admin)session.getAttribute(ProjectConfig.LOGIN_ADMIN)).getAdminId());
        return CodeAndMsg.EDITSUCESS.getJSON();
    }

    //訂單發貨
    @RequestMapping("/editOrdeShipment.do")
    public JSONObject editOrdeShipment(Order order,HttpSession session)throws Exception {
        orderService.ordeShipment(order,((Admin)session.getAttribute(ProjectConfig.LOGIN_ADMIN)).getAdminId());
        return CodeAndMsg.EDITSUCESS.getJSON();
    }

    //修改当前訂單信息
    @RequestMapping("/editCurrentOrderInfo.do")
    public JSONObject editCurrentOrderInfo(Order order,HttpSession session)throws Exception {
        orderService.editCurrentOrderInfo(order,((Admin)session.getAttribute(ProjectConfig.LOGIN_ADMIN)).getAdminId());
        return CodeAndMsg.EDITSUCESS.getJSON();
    }
    //修改密码
//    @PostMapping("/editLoginPassWord.do")
//    public JSONObject editLoginPassWord(@RequestParam("oldPassWord") String oldPassWord, @RequestParam("newPassWord") String newPassWord, HttpSession session)throws Exception {
//        orderService.updateLoginPassWord(oldPassWord,newPassWord, (Order) session.getAttribute(ProjectConfig.LOGIN_ADMIN));
//        return CodeAndMsg.HANDLESUCESS.getJSON();
//    }
    //删除单个訂單
    @PostMapping("/delOrderById.do")
    public JSONObject delOrderById(@RequestParam("orderId") String orderId)throws Exception {
        orderService.delOrderByOrderId(orderId);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

    //删除多个訂單
    @PostMapping("/delAllOrderById.do")
    public JSONObject delAllOrderById(@RequestParam("orderIds[]") List<String> orderIds)throws Exception {
        orderService.delAllOrderByOrderId(orderIds);
        return CodeAndMsg.DELSUCESS.getJSON();
    }

//    @PostMapping("/updateOrder.do")
//    public JSONObject updateOrder(@RequestParam("orderstate") Integer orderstate,@RequestParam("paymentStatus") Integer paymentStatus,@RequestParam("deslNo") String deslNo)throws Exception {
//        orderService.updateOrderByDealNo(orderstate,paymentStatus,deslNo);
//        return CodeAndMsg.DELSUCESS.getJSON();
//    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////

    //檢測管理員類型,并返回供應商id
    private String chekckAdminType(HttpSession session){
        String adminId = null;
        Admin admin = (Admin)session.getAttribute(LOGIN_ADMIN);
        if(admin.getAdminType()== ProjectConfig.AdminType.SUPPLIER.getValue())
            adminId = admin.getAdminId();
        return adminId;
    }



}
