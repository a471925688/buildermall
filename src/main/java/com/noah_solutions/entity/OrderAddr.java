package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah_solutions.common.PlaceCache;
import lombok.Data;

import javax.persistence.*;

/**
 * 2019 2 20 lijun
 * 訂單地址信息
 */
@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class OrderAddr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "BIGINT  AUTO_INCREMENT NOT NULL COMMENT '訂單地址id'")
    private String orderAddrId;
    @Column(columnDefinition = "VARCHAR(40)  COMMENT '(公司)'")
    private String orderAddrCompany;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(收货人姓名)'")
    private String orderAddrName;
    @Column(columnDefinition = "VARCHAR(30) NOT NULL COMMENT '(收货人电话)'")
    private String orderAddrPhome;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '地址地區'")
    private String  orderAddrAddr;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '详细地址'")
    private String  orderAddrDetailedAddr;
    @Column(columnDefinition = "INT(4)  COMMENT '訂單地址類型(1.訂單地址,2.送貨地址)'")
    private Integer orderAddrType;
    @Column(nullable = false,columnDefinition = "BIGINT  COMMENT '訂單id'")
    private String orderId;

    @Transient
    private String orderAddrAddrStr;

    public OrderAddr() {
    }

    public OrderAddr(String orderAddrCompany, String orderAddrName, String orderAddrPhome, String orderAddrAddr, String orderAddrDetailedAddr, Integer orderAddrType, String orderId) {
        this.orderAddrCompany = orderAddrCompany;
        this.orderAddrName = orderAddrName;
        this.orderAddrPhome = orderAddrPhome;
        this.orderAddrAddr = orderAddrAddr;
        this.orderAddrDetailedAddr = orderAddrDetailedAddr;
        this.orderAddrType = orderAddrType;
        this.orderId = orderId;
    }

    public String getOrderAddrId() {
        return orderAddrId;
    }

    public void setOrderAddrId(String orderAddrId) {
        this.orderAddrId = orderAddrId;
    }

    public String getOrderAddrCompany() {
        return orderAddrCompany;
    }

    public void setOrderAddrCompany(String orderAddrCompany) {
        this.orderAddrCompany = orderAddrCompany;
    }

    public String getOrderAddrName() {
        return orderAddrName;
    }

    public void setOrderAddrName(String orderAddrName) {
        this.orderAddrName = orderAddrName;
    }

    public String getOrderAddrPhome() {
        return orderAddrPhome;
    }

    public void setOrderAddrPhome(String orderAddrPhome) {
        this.orderAddrPhome = orderAddrPhome;
    }

    public String getOrderAddrAddr() {
        return orderAddrAddr;
    }

    public void setOrderAddrAddr(String orderAddrAddr) {
        this.orderAddrAddr = orderAddrAddr;
    }

    public String getOrderAddrDetailedAddr() {
        return orderAddrDetailedAddr;
    }

    public void setOrderAddrDetailedAddr(String orderAddrDetailedAddr) {
        this.orderAddrDetailedAddr = orderAddrDetailedAddr;
    }

    public void setOrderAddrType(Integer orderAddrType) {
        this.orderAddrType = orderAddrType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAddrAddrStr() {
        Place place = PlaceCache.getPlaceById(orderAddrAddr.substring(orderAddrAddr.lastIndexOf("/")+1));
        return (place!=null?place.getDetails():"")+"<br/>"+orderAddrDetailedAddr;
    }

    public Integer getOrderAddrType() {
        return orderAddrType;
    }
}
