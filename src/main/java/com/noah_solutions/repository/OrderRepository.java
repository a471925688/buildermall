package com.noah_solutions.repository;

import com.noah_solutions.entity.DealingSlip;
import com.noah_solutions.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    Order findFirstByOrderByOrderIdDesc();
    Order findByOrderId(String orderId);
    Long countByOrderPaymentStatus(Integer paymentStatus);
    Long countByAdminId(String adminId);
    Long countByOrderState(Integer orderState);
    Long countByOrderStateAndAdminIdIs(Integer orderState,String adminId);
    List<Order> findAllByOrderIdIn(List<String> orderIds);
    List<Order> findAllByOrderSignedTimeBeforeAndOrderIsSignedAndOrderState(String dataTime, Integer isSigned, String state);
//    List<Order> findAllByOrderStateAndOrderIsSigned(String orderState,String orderIsSigned);

    Page<Order> findAll(Specification specification, Pageable pageable);
    Order findByOrderIdAndOrderStateAndUserId(String orderId,Integer orderState,String userId);
    //查詢所有退款處理訂單數量
    @Query("select count(o.orderId) from Order o where o.orderRefundState<>?1")
    Long countByOrderRefundStateIsNot(Integer state);
    @Query("select count(o.orderId) from Order o where o.orderRefundState<>?1 and o.adminId=?2")
    Long countByOrderRefundStateIsNot(Integer state,String adminId);
    //查詢指定退款狀態的數量
    @Query("select count(o.orderId) from Order o where o.orderRefundState=?1")
    Long countByOrderRefundStateIs(Integer state);
    @Query("select count(o.orderId) from Order o where o.orderRefundState=?1 and o.adminId = ?2")
    Long countByOrderRefundStateIs(Integer state,String adminId);

    //查詢總訂單價格
    @Query("select sum(orderTotalPrice) from Order  o where o.orderId in ?1 " )
    String findOrderTotleAmount(List<String> orderIds);

    //查詢退款申請中的訂單
    @Query("select distinct o from Order o  join o.orderRecords orc on o.orderId = orc.orderId where o.orderState<5 and orc.orderRecType = 6")
    List<Order> findUntreatedOrder();


    @Modifying
    @Query(value = "update tb_order o,order_dlsl od set  o.order_state=:orderstate,o.order_payment_status=:paymentStatus where o.order_id=od.order_id and od.desl_no=:deslNo", nativeQuery = true)
    void updateOrderByDealNo(@Param("orderstate") Integer orderstate, @Param("paymentStatus") Integer paymentStatus, @Param("deslNo") String deslNo);

    //通過快递单号查询订单id
    @Query("select o.orderId from Order o where o.orderExpressNo=?1")
    List<String> findOrderByOrderExpressNo( String orderExpressNo);


    //通過交易號,查詢訂單id
    @Query("select o from Order o join com.noah_solutions.entity.OrderDlsl od on od.orderId = o.orderId and od.deslNo =:deslNo")
    List<Order> findOrderByDeslNo(@Param("deslNo") String deslNo);

    //通過交易號,查詢訂單id
    @Query("select new com.noah_solutions.entity.Order(o.orderId,o.userId) from Order o join com.noah_solutions.entity.OrderDlsl od on od.orderId = o.orderId and od.deslNo =:deslNo")
    List<Order> findOrderIdByDeslNo(@Param("deslNo") String deslNo);

    @Modifying
    @Query("update Order o set o.orderState=:orderState,o.orderUpdateTime=current_time  where o.userId=:userId and o.orderId=:orderId")
    void updateOrderStateByUserId(@Param("userId")String userId,@Param("orderState") Integer orderState,@Param("orderId")String orderId);

    @Modifying
    @Query("update Order  o set o.orderRefundAmount = :orderRefundAmount, o.orderRefundState=1,  o.orderRefundNum = o.orderRefundNum+1 where o.orderId=:orderId")
    void updateOrderRefundNum(@Param("orderId") String orderId,@Param("orderRefundAmount")String orderRefundAmount);

    @Modifying
    @Query("update Order  o set o.orderRefundAmount = o.orderTotalPrice, o.orderRefundState=1,  o.orderRefundNum = o.orderRefundNum+1 where o.orderId=:orderId")
    void updateOrderRefundNum(@Param("orderId") String orderId);

    //更新訂單退款狀態
    @Modifying
    @Query("update Order  o set  o.orderRefundState=:orderRefundState where o.orderId=:orderId")
    void updateOrderRefundState(@Param("orderId") String orderId,@Param("orderRefundState")Integer orderRefundState);

    //更新訂單狀態
    @Modifying
    @Query("update Order  o set  o.orderState=:orderState,o.orderUpdateTime=current_time where o.orderId=:orderId")
    void updateOrderState(@Param("orderId") String orderId,@Param("orderState")Integer orderState);

    //更新订单签收状态
    @Modifying
    @Query("update Order  o set  o.orderIsSigned =1,o.orderSignedTime = ?1 where o.orderId in (?2)")
    void updateOrderSigned(String orderSignedTime,List<String> orderIds);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select ds from Order o join com.noah_solutions.entity.OrderDlsl od on od.orderId = o.orderId join com.noah_solutions.entity.DealingSlip ds on ds.deslNo = od.deslNo where o.orderId = ?1 and ds.deslAState =?2")
    List<DealingSlip> findByOrderIdAndDeslAState(String orderId,Integer deslAState);

    //查询所有已发货未签收的订单
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select o.orderExpressNo from Order o where o.orderState = 3 and o.orderIsSigned = 0")
    List<String> findAllByOrderIsNotSigned();


    //更新订单为签收状态
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.orderState = 4,o.orderUpdateTime=current_time where o.orderSignedTime < ?1 and o.orderIsSigned=1 and o.orderState=3")
    void updateOrderStateByDateTime(String dataTime);

    //取消超时未付款订单
    @Modifying(clearAutomatically = true)
    @Query("update Order o set o.orderState = 5,o.orderUpdateTime=current_time where o.orderCreateTime < ?1  and o.orderState=1")
    void updateOvertimeOrders(String dataTime);

    @Query("select o.orderId from Order o where o.orderSignedTime < ?1 and o.orderIsSigned=1 and o.orderState=3")
    List<String> findOrderIdByOrderSignedTime(String dataTime);

    @Query("select distinct o.orderId from Order  o left join o.orderProducts op where o.orderState=4 and o.orderUpdateTime<?1 and o.orderUpdateTime>?2  and op.isEvaluation=0")
    List<String> findOrderIdByNotEvaluate(String startTime,String endTime);


    @Query("select distinct o from Order o inner join com.noah_solutions.entity.OrderProduct op on o.orderId = op.orderId   where (op.productTitle  like  concat('%',?2,'%') or op.productTitleEng  like  concat('%',?2,'%')) and o.userId=?1 ")
    Page<Order> selectOrderUserTable(String userId,String title,Pageable pageable);


}
