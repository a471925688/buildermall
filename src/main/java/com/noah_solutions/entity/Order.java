package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.houbb.paradise.common.util.StringUtil;
import com.noah_solutions.common.ProjectConfig;
import com.noah_solutions.util.ZJFConverterDemo;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static com.noah_solutions.common.ProjectConfig.PayDollarCurType.CNY;
import static com.noah_solutions.common.ProjectConfig.PayDollarCurType.HKD;

/**
 * 2018 11 26 lijun
 * 建材网订单实体类
 */
@Entity
@Table(name = "tb_order")
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 2866588012695255434L;
    @Id
    @Column(unique = true, nullable = false,columnDefinition = "BIGINT COMMENT '订单id'")
    private String orderId;
    @Column(columnDefinition = "VARCHAR(40) COMMENT '订单编号'")
    private String  orderNo;
    @Column(columnDefinition = "INT(10) DEFAULT 1 COMMENT '訂購人id'")
    private String  userId;
    @Column(columnDefinition = "INT(10) DEFAULT 1 COMMENT '供應商id'")
    private String  adminId;
    @Column(nullable = false,columnDefinition = "DOUBLE  COMMENT '訂單總價'")
    private String  orderTotalPrice;
    @Column(nullable = false,columnDefinition = "DOUBLE  COMMENT '總運費'")
    private String totalFreightCharges;//總運費
    @Column(columnDefinition = "DOUBLE  COMMENT '優惠金額'")
    private String  orderPreferentialAmount;
    @Column(columnDefinition = "VARCHAR(50)  COMMENT '優惠碼'")
    private String  orderDiscountCode;
    @Column(columnDefinition = "INT(2) DEFAULT 0  COMMENT '是否需要發票(1.是,0.否)'")
    private String  orderInvoice;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '發票抬頭'")
    private String  orderInvoiceCorporateName;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '納稅人識別號'")
    private String  orderInvoiceBusinessLicense;
    @Column(columnDefinition = "INT(5) DEFAULT 1 COMMENT '记录类型(1.下单订单，2.付款成功，3.订单发货，4.确认收货，5.取消订单,6.申请退款，7.退款操作，8.拒绝退款)'")
    private Integer  orderState;
    @Column(columnDefinition = "INT(6) DEFAULT 1 COMMENT '付款狀態(1.未付款.2.已付款.3.已退款)'",insertable = false)
    private Integer  orderPaymentStatus;
    @Column(columnDefinition = "INT(4) COMMENT '付款方式 1.银行转账,2.线上支付'")
    private Integer  orderPaymentMethod;
    @Column(columnDefinition = "INT(4) COMMENT '快遞公司.1.德邦,2.順豐'")
    private Integer order_express_company_send;
    @Column(columnDefinition = "INT(4) COMMENT '快遞公司.1.德邦'")
    private Integer orderExpressCompany;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '快遞單號'")
    private String  orderExpressNo;
    @Column(columnDefinition = "INT(4) DEFAULT 0  COMMENT '签收状态(1.已签收,0.未签收)'",insertable = false)
    private Integer orderIsSigned;
    @Column(columnDefinition = "DATETIME   COMMENT '签收时间'",insertable = false)
    private String orderSignedTime;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '客户备注'")
    private String  orderRemark;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '订单说明'")
    private String  orderExplanation;
    @Column(columnDefinition = "INT DEFAULT 0  COMMENT '申請退款次數'",insertable = false)
    private Integer orderRefundNum;
    @Column(columnDefinition = "DOUBLE DEFAULT 0  COMMENT '退款金額'",insertable = false)
    private String orderRefundAmount;
    @Column(columnDefinition = "INT(4) DEFAULT 0  COMMENT '退款處理狀態（0.無退款申請,1.退款申請中,2.已同意,3.已拒絕）'",insertable = false)
    private Integer orderRefundState;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '催貨次數'",insertable = false)
    private Integer orderRushGoodsNum;

    @Column(columnDefinition = "VARCHAR(10) COMMENT '提交時的語言'")
    private String lang;
    @Column(columnDefinition = "VARCHAR(10) COMMENT '提交時的貨幣類型'")
    private String cur;

    @Column(columnDefinition = "VARCHAR(200) COMMENT '项目名称(订单发起来源)'")
    private String shopCartName;



    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  orderCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  orderUpdateTime;



    @Transient
    private Double totalFreightChargesCNY = Double.valueOf(0);//總運費
    @Transient
    private Double totalFreightChargesUSD = Double.valueOf(0);//總運費
    @Transient
    private String orderBillAddrId;//賬單地址id
    @Transient
    private String orderDelAddrId;//配送地址id
    @Transient
    private String transitId;//集運地址id
    @Transient
    private Double orderTotalPriceCNY;
    @Transient
    private Double orderTotalPriceUSD;
    @Transient
    private Double orderPreferentialAmountCNY;
    @Transient
    private Double orderPreferentialAmountUSD;
    @Transient
    private Double PricePDF;
    @Transient
    private String curQuery;
    @Transient
    private Integer curType;
    @Transient
    private String orderPaymentMethodStr;

    @Transient
    private Integer curProductPrice;


    public Order(String orderId, String userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public Order() {
    }

    public Order(String orderNo, String userId, String orderCreateTime) {
        this.orderNo = orderNo;
        this.userId = userId;
        this.orderCreateTime = orderCreateTime;
    }




    //供應商信息
    @OneToOne(fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"role","login","hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="adminId",insertable = false,updatable = false)
    private Admin admin;

    //客戶信息
    @OneToOne(fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"receivingInfos","login","hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="userId",insertable = false,updatable = false)
    private User user;

    //產品參數信息
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="orderId",updatable = false)
    @OrderBy("orderProductId ASC")
    private Set<OrderProduct> orderProducts ;


    //產品地址信息
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="orderId",updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private Set<OrderAddr> orderAddrs ;



    //訂單操作記錄
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="orderId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @OrderBy("orderRecId DESC")
    private Set<OrderRecord> orderRecords ;


    //交易記錄
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_dlsl",
            joinColumns = {@JoinColumn(name = "orderId")},
            inverseJoinColumns = {@JoinColumn(name = "deslNo")}
    )
    @OrderBy("deslCreatTime DESC")
    private Set<DealingSlip> dealingSlips;
    ////////////////////////////////////不映射到数据库中的属性 start ///////////////////////////////////////////////////////


    ////////////////////////////////////不映射到数据库中的属性 end///////////////////////////////////////////////////////


    public String getTransitId() {
        return transitId;
    }

    public void setTransitId(String transitId) {
        this.transitId = transitId;
    }

    public Integer getCurProductPrice() {
        Double price = Double.valueOf(0);
        if(!StringUtils.isEmpty(orderTotalPrice))
            price = Double.valueOf(orderTotalPrice);
        if(!StringUtils.isEmpty(totalFreightCharges))
            price = price-Double.valueOf(totalFreightCharges);
        if(!StringUtils.isEmpty(orderPreferentialAmount))
            price = price-Double.valueOf(orderPreferentialAmount);
        if(!StringUtils.isEmpty(curType)){
            if(curType.equals(HKD.getValue())) {
            }else if(curType.equals(CNY.getValue())) {
                price = price*ProjectConfig.TodayRate;
            }else {
                price = price*ProjectConfig.TodayRateUSD;
            }
        }
        return price.intValue();
    }

    public Integer getOrder_express_company_send() {
        return order_express_company_send;
    }

    public void setOrder_express_company_send(Integer order_express_company_send) {
        this.order_express_company_send = order_express_company_send;
    }

    public void setCurProductPrice(Integer curProductPrice) {
        this.curProductPrice = curProductPrice;
    }

    public String getShopCartName() {
        return shopCartName;
    }

    public void setShopCartName(String shopCartName) {
        this.shopCartName = shopCartName;
    }

    public String getOrderPaymentMethodStr() {
        if(!StringUtils.isEmpty(orderPaymentMethod))
            orderPaymentMethodStr = ProjectConfig.OrderPaymentMethod.queryValue(orderPaymentMethod);
        return orderPaymentMethodStr;
    }

    public void setOrderPaymentMethodStr(String orderPaymentMethodStr) {
        this.orderPaymentMethodStr = orderPaymentMethodStr;
    }
    public Double getPricePDF() {
        if(!StringUtils.isEmpty(orderTotalPrice)){
            java.lang.System.out.println(Double.parseDouble(orderTotalPrice));
            PricePDF = Double.parseDouble(orderTotalPrice);
        }

        if(!StringUtils.isEmpty(orderPreferentialAmount)){
            PricePDF =  PricePDF-Double.parseDouble(orderPreferentialAmount);
        }

        if(!StringUtils.isEmpty(PricePDF)&&!StringUtils.isEmpty(totalFreightCharges)) {
            PricePDF = PricePDF - Double.valueOf(totalFreightCharges);
            if(!StringUtils.isEmpty(cur)){
                if(curType.equals(HKD.getValue())) {

                }else if(curType.equals(CNY.getValue())) {
                    PricePDF = PricePDF*ProjectConfig.TodayRate;
                }else {
                    PricePDF = PricePDF*ProjectConfig.TodayRateUSD;
                }
            }

        }
        return PricePDF;
    }



    public void setPricePDF(Double pricePDF) {
        PricePDF = pricePDF;
    }

    public String getOrderSignedTime() {
        return orderSignedTime;
    }

    public void setOrderSignedTime(String orderSignedTime) {
        this.orderSignedTime = orderSignedTime;
    }

    public Integer getOrderIsSigned() {
        return orderIsSigned;
    }

    public void setOrderIsSigned(Integer orderIsSigned) {
        this.orderIsSigned = orderIsSigned;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public Double getOrderPreferentialAmountCNY() {
        if(!StringUtils.isEmpty(orderPreferentialAmount)){
            orderPreferentialAmountCNY = Double.valueOf(Math.round(Double.valueOf(orderPreferentialAmount) * ProjectConfig.TodayRate));
        }

        return orderPreferentialAmountCNY;
    }

    public Double getTotalFreightChargesUSD() {
        if(!StringUtils.isEmpty(totalFreightCharges)){
            totalFreightChargesUSD = Double.valueOf(Math.round(Double.valueOf(totalFreightCharges) * ProjectConfig.TodayRateUSD));
        }

        return totalFreightChargesUSD;
    }

//    public Double getCurProductTotlePrice() {
//        if(orderProducts!=null&&!StringUtils.isEmpty(cur)&&curProductTotlePrice==0){
//            for (OrderProduct op:orderProducts) {
//                if(cur.equals(HKD.getValue())) {
//                    curProductTotlePrice = curProductTotlePrice + op.getProductPrice()*op.getProductQuantity() ;
//                }else if(cur.equals(CNY.getValue())) {
//
//                    curProductTotlePrice = curProductTotlePrice + op.getProductPriceCNY()*op.getProductQuantity() ;
//                }else {
//                    curProductTotlePrice = curProductTotlePrice + op.getProductPriceUSD()*op.getProductQuantity() ;
//                }
//            }
//        }
//        return Double.valueOf(Math.round(curProductTotlePrice));
//    }
//
//    public void setCurProductTotlePrice(Double curProductTotlePrice) {
//        this.curProductTotlePrice = curProductTotlePrice;
//    }

    public void setTotalFreightChargesUSD(Double totalFreightChargesUSD) {
        this.totalFreightChargesUSD = totalFreightChargesUSD;
    }

    public Double getOrderTotalPriceUSD() {
        if(!StringUtils.isEmpty(orderTotalPrice)){
            orderTotalPriceUSD = Double.valueOf(Math.round(Double.valueOf(orderTotalPrice) * ProjectConfig.TodayRateUSD));
        }

        return orderTotalPriceUSD;
    }

    public void setOrderTotalPriceUSD(Double orderTotalPriceUSD) {
        this.orderTotalPriceUSD = orderTotalPriceUSD;
    }

    public Double getOrderPreferentialAmountUSD() {
        if(!StringUtils.isEmpty(orderPreferentialAmount)){
            orderPreferentialAmountUSD = Double.valueOf(Math.round(Double.valueOf(orderPreferentialAmount) * ProjectConfig.TodayRateUSD));
        }

        return orderPreferentialAmountUSD;
    }

    public void setOrderPreferentialAmountUSD(Double orderPreferentialAmountUSD) {
        this.orderPreferentialAmountUSD = orderPreferentialAmountUSD;
    }

    public void setOrderPreferentialAmountCNY(Double orderPreferentialAmountCNY) {


        this.orderPreferentialAmountCNY = orderPreferentialAmountCNY;
    }

    public Double getOrderTotalPriceCNY() {
        if(orderTotalPrice!=null){
            orderTotalPriceCNY = Double.valueOf(Math.round(Double.valueOf(orderTotalPrice)* ProjectConfig.TodayRate));
        }
        return orderTotalPriceCNY;
    }

    public void setOrderTotalPriceCNY(Double orderTotalPriceCNY) {
        this.orderTotalPriceCNY = orderTotalPriceCNY;
    }

    public Integer getCurType() {
        return curType;
    }

    public void setCurType(Integer curType) {
        this.curType = curType;
    }

    public String getCurQuery() {
        if(this.curType==null&&!StringUtils.isEmpty(cur)){
           this.curType = Integer.valueOf(cur);
        }
        if(this.curType!=null){
            this.curQuery = ProjectConfig.PayDollarCurType.queryValue(this.curType);
        }

        return curQuery;
    }

    public void setCurQuery(String curQuery) {
        this.curQuery = curQuery;
    }

    public String getOrderRefundAmount() {
        return orderRefundAmount;
    }

    public void setOrderRefundAmount(String orderRefundAmount) {
        this.orderRefundAmount = orderRefundAmount;
    }

    public Integer getOrderRefundState() {
        return orderRefundState;
    }

    public void setOrderRefundState(Integer orderRefundState) {
        this.orderRefundState = orderRefundState;
    }

    public Integer getOrderRefundNum() {
        return orderRefundNum;
    }

    public void setOrderRefundNum(Integer orderRefundNum) {
        this.orderRefundNum = orderRefundNum;
    }

    public Integer getOrderRushGoodsNum() {
        return orderRushGoodsNum;
    }

    public void setOrderRushGoodsNum(Integer orderRushGoodsNum) {
        this.orderRushGoodsNum = orderRushGoodsNum;
    }

    public String getOrderInvoiceCorporateName() {
        return orderInvoiceCorporateName;
    }

    public void setOrderInvoiceCorporateName(String orderInvoiceCorporateName) {
        this.orderInvoiceCorporateName = orderInvoiceCorporateName;
    }

    public String getOrderInvoiceBusinessLicense() {
        return orderInvoiceBusinessLicense;
    }

    public void setOrderInvoiceBusinessLicense(String orderInvoiceBusinessLicense) {
        this.orderInvoiceBusinessLicense = orderInvoiceBusinessLicense;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOrderExplanation() {
        return orderExplanation;
    }

    public void setOrderExplanation(String orderExplanation) {
        this.orderExplanation = orderExplanation;
    }

    public String getOrderBillAddrId() {
        return orderBillAddrId;
    }

    public void setOrderBillAddrId(String orderBillAddrId) {
        this.orderBillAddrId = orderBillAddrId;
    }

    public String getOrderDelAddrId() {
        return orderDelAddrId;
    }

    public void setOrderDelAddrId(String orderDelAddrId) {
        this.orderDelAddrId = orderDelAddrId;
    }

    public Set<OrderRecord> getOrderRecords() {
        return orderRecords;
    }

    public void setOrderRecords(Set<OrderRecord> orderRecords) {
        this.orderRecords = orderRecords;
    }

    public Double getTotalFreightChargesCNY() {
        if(!StringUtils.isEmpty(totalFreightCharges)){
            totalFreightChargesCNY = Double.valueOf(Math.round(Double.valueOf(totalFreightCharges) * ProjectConfig.TodayRate));
        }
        return totalFreightChargesCNY;
    }

    public void setTotalFreightChargesCNY(Double totalFreightChargesCNY) {
        this.totalFreightChargesCNY = totalFreightChargesCNY;
    }

//    public Double getTotalFreightCharges() {
//        if(orderProducts!=null&&totalFreightCharges==0){
//            for (OrderProduct product: orderProducts) {
//                if(product.getProductFreightCharges()!=null)
//                    totalFreightCharges+=product.getProductFreightCharges();
//            }
//        }
//        return totalFreightCharges;
//    }

    public String getOrderId() {
        return orderId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderPreferentialAmount() {
        return orderPreferentialAmount;
    }

    public void setOrderPreferentialAmount(String orderPreferentialAmount) {
        this.orderPreferentialAmount = orderPreferentialAmount;
    }

    public String getOrderDiscountCode() {
        return orderDiscountCode;
    }

    public void setOrderDiscountCode(String orderDiscountCode) {
        this.orderDiscountCode = orderDiscountCode;
    }

//    public void setTotalFreightCharges(Double totalFreightCharges) {
//        this.totalFreightCharges = totalFreightCharges;
//    }


    public String getTotalFreightCharges() {
        return totalFreightCharges;
    }

    public void setTotalFreightCharges(String totalFreightCharges) {
        this.totalFreightCharges = totalFreightCharges;
    }

    public String getOrderInvoice() {
        return orderInvoice;
    }

    public void setOrderInvoice(String orderInvoice) {
        this.orderInvoice = orderInvoice;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getOrderPaymentStatus() {
        return orderPaymentStatus;
    }

    public void setOrderPaymentStatus(Integer orderPaymentStatus) {
        this.orderPaymentStatus = orderPaymentStatus;
    }

    public Integer getOrderPaymentMethod() {
        return orderPaymentMethod;
    }

    public void setOrderPaymentMethod(Integer orderPaymentMethod) {
        this.orderPaymentMethod = orderPaymentMethod;
    }

    public Set<DealingSlip> getDealingSlips() {
        return dealingSlips;
    }

    public void setDealingSlips(Set<DealingSlip> dealingSlips) {
        this.dealingSlips = dealingSlips;
    }

    public Integer getOrderExpressCompany() {
        return orderExpressCompany;
    }

    public void setOrderExpressCompany(Integer orderExpressCompany) {
        this.orderExpressCompany = orderExpressCompany;
    }

    public String getOrderExpressNo() {
        return orderExpressNo;
    }

    public void setOrderExpressNo(String orderExpressNo) {
        this.orderExpressNo = orderExpressNo;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getOrderUpdateTime() {
        return orderUpdateTime;
    }

    public void setOrderUpdateTime(String orderUpdateTime) {
        this.orderUpdateTime = orderUpdateTime;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Set<OrderAddr> getOrderAddrs() {
        return orderAddrs;
    }

    public void setOrderAddrs(Set<OrderAddr> orderAddrs) {
        this.orderAddrs = orderAddrs;
    }
}
