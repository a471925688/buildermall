package com.noah_solutions.service;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.bean.RatioData;
import com.noah_solutions.entity.Order;
import com.noah_solutions.entity.OrderRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {

    //查询所有订单
    List<Order> selectAllOrder();

    Page<Order> selectOrderPageByCont(Order condition, Pageable pageable);

    //根据条件获取总数
    Long selectCountOrderByCont(Order condition);

    Long selectCountOrderProductByProductTypeId(String productType);
    //查詢單個訂單
    Order getOrderById(String orderId);
    //添加订单
    void addOrder(Order order)throws Exception;
    //更新订单
    void updateOrder(Order order)throws Exception;

    //检查未签收的订单
    void checkOrderSignedStatus();

    //更新订单为收货状态
    void updateOrderStateByDateTime(String dateTime);
    //检查更新超时未付款订单
    void checkOvertimeOrders(String dataTime);

    //更新当前订单信息
    void editCurrentOrderInfo(Order orderInfo, String adminId)throws Exception;

    //删除单个订单
    void delOrderByOrderId(String orderId);
    //批量删除订单
    void delAllOrderByOrderId(List<String> orderIds);
    //訂單發貨
    void ordeShipment(Order order,String adminId)throws Exception;
    // 獲取購物比例(類型)
    List<RatioData> selectShoppingRatioByProductType();
    // 獲取訂單數量(訂單狀態)
    List selectCountOrderPaymentStatus();
    // 獲取訂單數量(訂單狀態)
    List selectCountOrderState(String adminId);
    List selectCountOrderRefundState(String adminId);
    //
    List<Order> findOrderIdByDeslNo(String deslNo);
    //查詢退款申請中的訂單
    List<Order> findUntreatedOrder();
    //查詢所有退款訂單
    Page<Order> findRefundOrder(String adminId,String orderNo, String orderCreateTime, String orderRefundState, Pageable pageable);
//    Long countAllByOrderTypeNotMember();

    void updateOrderByDealNo(Integer orderstate,Integer paymentStatus,String deslNo);

    void saveOrderRecord(OrderRecord orderRecord);

    Integer refundHandleOrder(String adminId,OrderRecord orderRecord);

    List getNewTraceQuery(String orderExpressNo)throws Exception;

    List<String> findOrderIdByNotEvaluate();
    //設置運費
    void quotation(String orderId, String freight, String adminId)throws Exception;


}
