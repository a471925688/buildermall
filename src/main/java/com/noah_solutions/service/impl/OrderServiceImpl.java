package com.noah_solutions.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.noah_solutions.bean.RatioData;
import com.noah_solutions.common.ProductTypeCache;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.entity.DealingSlip;
import com.noah_solutions.entity.Order;
import com.noah_solutions.entity.OrderRecord;
import com.noah_solutions.entity.ProductType;
import com.noah_solutions.ex.CodeAndMsg;
import com.noah_solutions.ex.CustormException;
import com.noah_solutions.repository.OrderProductRepository;
import com.noah_solutions.repository.OrderRecordRepository;
import com.noah_solutions.repository.OrderRepository;
import com.noah_solutions.service.IEmailService;
import com.noah_solutions.service.IOrderService;
import com.noah_solutions.thirdPartyInterface.DeBangExpress;
import com.noah_solutions.thirdPartyInterface.Paydollar;
import com.noah_solutions.util.BeanUtils;
import com.noah_solutions.util.DaoUtil.Criteria;
import com.noah_solutions.util.DaoUtil.Restrictions;
import com.noah_solutions.util.DateUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.noah_solutions.common.ProjectConfig.OrderPaymentStatus.*;
import static com.noah_solutions.common.ProjectConfig.OrderRecordType.ADMIN_UPDATE;
import static com.noah_solutions.common.ProjectConfig.OrderRecordType.Shipment;
import static com.noah_solutions.common.ProjectConfig.OrderRefundState.NOAPPLICATION;
import static com.noah_solutions.thirdPartyInterface.DeBangExpress.RESPONSEPARAM;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
    @Resource
    private OrderRepository orderRepository;
    @Resource
    private OrderProductRepository orderProductRepository;
    @Resource
    private OrderRecordRepository orderRecordRepository;

    @Resource
    private IEmailService emailService;
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    //查询所有訂單
    @Override
    public List<Order> selectAllOrder() {
        return orderRepository.findAll();
    }

    //分页查询所有訂單（带条件）
    @Override
    public Page<Order> selectOrderPageByCont(Order condition, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("orderCreateTime", ExampleMatcher.GenericPropertyMatchers.contains());
        return  orderRepository.findAll(Example.of(condition,matcher),pageable);
    }
    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }


    //查询訂單数量(带条件)
    @Override
    public Long selectCountOrderByCont(Order condition) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return  orderRepository.count(Example.of(condition,matcher));
    }


    //查询訂單產品数量(带条件)
    @Override
    public Long selectCountOrderProductByProductTypeId(String productTypeId) {
        if(StringUtils.isEmpty(productTypeId)){
            productTypeId = "";
        }else {
            productTypeId = productTypeId+"|";
        }
        return  orderProductRepository.countByProductId(productTypeId);
    }

    @Override
    public List<RatioData> selectShoppingRatioByProductType() {
        List<RatioData> shoppingRatios = new ArrayList<>();
        Long shopingNum = orderProductRepository.count();
        for (ProductType p : ProductTypeCache.getProductTypesByLevel(1)) {
            String parentsId;
            if(StringUtils.isEmpty(p.getProductTypeId())){
                parentsId = "";
            }else {
                parentsId =p.getProductTypeId()+"|";
            }
            Long num  = orderProductRepository.countByProductId(parentsId);
            Double proportion = Double.valueOf(0);
            if(shopingNum>0){
                proportion =Double.valueOf(new DecimalFormat("#.00").format(num.doubleValue()/shopingNum*100));
            }
            RatioData shoppingRatio = new RatioData(p.getProductTypeName(),p.getProductTypeNameEng(),num,proportion);
            shoppingRatios.add(shoppingRatio);
        }
        return shoppingRatios;
    }

    @Override
    public List selectCountOrderPaymentStatus() {
        List<Long> ratioData = new ArrayList<>();
        ratioData.add(orderRepository.count());
        ratioData.add(orderRepository.countByOrderPaymentStatus(UNPAID.getValue()));
        ratioData.add(orderRepository.countByOrderPaymentStatus(PAID.getValue()));
        ratioData.add(orderRepository.countByOrderPaymentStatus(REFUNDED.getValue()));
        return ratioData;
    }

    @Override
    public List selectCountOrderState(String adminId) {
        List<Long> ratioData = new ArrayList<>();
        if(StringUtils.isEmpty(adminId)) {
            ratioData.add(orderRepository.count());
            for (ProjectConfig.OrderState e : ProjectConfig.OrderState.values()) {
                ratioData.add(orderRepository.countByOrderState(e.getValue()));
            }
        }else {
            ratioData.add(orderRepository.countByAdminId(adminId));
            for (ProjectConfig.OrderState e : ProjectConfig.OrderState.values()) {
                ratioData.add(orderRepository.countByOrderStateAndAdminIdIs(e.getValue(),adminId));
            }
        }
        return ratioData;
    }

    @Override
    public List selectCountOrderRefundState(String adminId) {
        List<Long> ratioData = new ArrayList<>();
        if(StringUtils.isEmpty(adminId)){
            ratioData.add(orderRepository.countByOrderRefundStateIsNot(NOAPPLICATION.getValue()));
            for (ProjectConfig.OrderRefundState e : ProjectConfig.OrderRefundState.values()) {
                if(!e.getValue().equals(NOAPPLICATION.getValue()))
                    ratioData.add(orderRepository.countByOrderRefundStateIs(e.getValue()));
            }
        }else {
            ratioData.add(orderRepository.countByOrderRefundStateIsNot(NOAPPLICATION.getValue(),adminId));
            for (ProjectConfig.OrderRefundState e : ProjectConfig.OrderRefundState.values()) {
                if(!e.getValue().equals(NOAPPLICATION.getValue()))
                    ratioData.add(orderRepository.countByOrderRefundStateIs(e.getValue(),adminId));
            }
        }


        return ratioData;
    }

    @Override
    public List<Order> findOrderIdByDeslNo(String deslNo) {
        return orderRepository.findOrderIdByDeslNo(deslNo);
    }
    @Override
    public List<Order> findUntreatedOrder() {
       return orderRepository.findUntreatedOrder();
    }
    @Override
    public Page<Order> findRefundOrder(String adminId,String orderNo, String orderCreateTime, String orderRefundState,Pageable pageable) {
        Criteria criteria =   new Criteria().add(Restrictions.eq("orderNo",orderNo,false))
                .add(Restrictions.like("orderCreateTime",orderCreateTime,false));
        if(StringUtils.isEmpty(orderRefundState)){
            criteria.add(Restrictions.ne("orderRefundState", NOAPPLICATION.getValue(),false));
        }else {
            criteria.add(Restrictions.eq("orderRefundState",orderRefundState,false));
        }
        return orderRepository.findAll(criteria,pageable);

    }
    //查询快递轨迹
    @Override
    @Transactional
    public List getNewTraceQuery(String orderExpressNo)throws Exception {
        JSONObject jsonObject = DeBangExpress.getNewTraceQuery(orderExpressNo);
        List<String> orderIds = orderRepository.findOrderByOrderExpressNo(orderExpressNo);
        if(orderIds.size()==0)
            throw new CustormException(CodeAndMsg.ERROR);
        List<DeBangExpress.TraceQuery> traceQueryStatuses = null;
        if(jsonObject.getString(DeBangExpress.RESULT).equals("true")){
            traceQueryStatuses = jsonObject.getJSONObject(RESPONSEPARAM).getJSONArray(DeBangExpress.TRACE_LIST).toJavaList(DeBangExpress.TraceQuery.class);
        }
        traceQueryStatuses.forEach(v->{
            if(v.getStatus().equals(DeBangExpress.TraceQueryStatus.SIGNED.toString()))
                orderRepository.updateOrderSigned(v.getTime(),orderIds);
        });
        return traceQueryStatuses;
    }

    @Override
    public List<String> findOrderIdByNotEvaluate() {
        return orderRepository.findOrderIdByNotEvaluate(  DateUtil.getNextDay(new Date(),-3+""),  DateUtil.getNextDay(new Date(),-4+""));
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //添加訂單
    @Override
    @Transactional
    public void addOrder(Order order)throws Exception {
        order.setOrderState(ProjectConfig.OrderState.UNPAID.getValue());
        orderRepository.save(order);
    }
    //更新訂單
    @Override
    @Transactional
    public void updateOrder(Order order)throws Exception {
        orderRepository.save(order);
    }
    //訂單運費報價
    @Override
    @Transactional
    public void quotation(String orderId, String freight, String adminId)throws Exception {
        Order order = orderRepository.findByOrderId(orderId);
        order.setTotalFreightCharges(freight);
        order.setOrderTotalPrice(Double.valueOf(order.getOrderTotalPrice())+Double.valueOf(freight)+"");
        order.setOrderState(ProjectConfig.OrderState.UNPAID.getValue());
        emailService.newOrder(order.getLang(),order);
        orderRepository.save(order);
    }
    //检查并更新订单签收状态
    @Override
    @Transactional
    public void checkOrderSignedStatus(){
        orderRepository.findAllByOrderIsNotSigned().forEach(v->{
            try {
                getNewTraceQuery(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    //更新订单为收货状态
    @Override
    @Transactional
    public void updateOrderStateByDateTime(String dateTime){

//        orderRepository.findAllByOrderSignedTimeBeforeAndOrderIsSignedAndOrderState(dateTime, ProjectConfig.OrderSignedStatus.ISSIGNED.getValue(), ProjectConfig.OrderState.RECEIPT.getValue()+"");
        orderRepository.findOrderIdByOrderSignedTime(dateTime).forEach(v->{
            orderRepository.updateOrderState(v, ProjectConfig.OrderState.COMPLETED.getValue());
            orderRecordRepository.save(new OrderRecord(ProjectConfig.OrderRecordType.RECEIVINGGOODS.getValue(),null,null,v,"订单签收超过14天"));
        });
//        orderRepository.updateOrderStateByDateTime(dateTime);
    }

    @Transactional
    @Override
    public void checkOvertimeOrders(String dataTime) {
        orderRepository.updateOvertimeOrders(dataTime);
    }


    //删除訂單
    @Override
    public void delOrderByOrderId(String orderId) {
        orderRepository.deleteById(orderId);
    }
    //批量删除訂單
    @Override
    public void delAllOrderByOrderId(List<String> orderIds) {
        List<Order> orders = orderRepository.findAllByOrderIdIn(orderIds);
//        //删除所有
        orderRepository.deleteAll(orders);
    }

    //發貨
    @Override
    public void ordeShipment(Order order,String adminId)throws Exception {
        update(order);
        saveOrderRecord(new OrderRecord(Shipment.getValue(),adminId,null,order.getOrderId(),Shipment.queryValue()));
        emailService.shipmentOrder(order.getOrderId());
    }




    //修改当前訂單信息
    @Override
    @Transactional
    public  void editCurrentOrderInfo(Order orderInfo, String adminId)throws Exception{
        Order order = orderRepository.findByOrderId(orderInfo.getOrderId());
        BeanUtils.copyNotNullProperties(orderInfo,order);
        saveOrderRecord(new OrderRecord(ADMIN_UPDATE.getValue(),adminId,null,orderInfo.getOrderId(),Shipment.queryValue()));
        orderRepository.flush();
    }


    @Override
    @Transactional
    public void updateOrderByDealNo(Integer orderstate, Integer paymentStatus, String deslNo) {
            orderRepository.updateOrderByDealNo(orderstate,paymentStatus,deslNo);
    }

    @Override
    @Transactional
    public void saveOrderRecord(OrderRecord orderRecord){
        orderRecordRepository.save(orderRecord);
    }

    @Override
    @Transactional
    public Integer refundHandleOrder(String adminId ,OrderRecord orderRecord) {
        orderRecord.setAdminId(adminId);
        orderRepository.updateOrderRefundState(orderRecord.getOrderId(),orderRecord.getOrderRecType()-5);
        orderRecordRepository.save(orderRecord);
        if(orderRecord.getOrderRecType().equals(ProjectConfig.OrderRecordType.REFUND.getValue())){
            orderRepository. updateOrderState(orderRecord.getOrderId(),ProjectConfig.OrderState.PENDINGREFUND.getValue());//更新訂單狀態
            List<DealingSlip> dealingSlips =orderRepository.findByOrderIdAndDeslAState(orderRecord.getOrderId(), ProjectConfig.DealingSlipState.PAYSUCCESS.getValue());
            if(dealingSlips.size()>0){
                DealingSlip dealingSlip = dealingSlips.get(0);
                JSONObject jsonObject = Paydollar.refundOrder(Integer.valueOf(dealingSlip.getDeslCur()),dealingSlip.getDeslPayRef(),null);
                if(jsonObject.getInteger("resultCode").intValue()==-1){
                   return -1;
                }else {
                    orderRepository. updateOrderState(orderRecord.getOrderId(),ProjectConfig.OrderState.REFUNDED.getValue());//更新訂單狀態
                }
            }
        }
        return 1;
    }



    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////







    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    private void update(Order newOrder){
        BeanUtils.copyNotNullProperties(newOrder,orderRepository.findByOrderId(newOrder.getOrderId()));
        orderRepository.flush();
    }


    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
}
