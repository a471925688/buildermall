package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 11 26 lijun
 * 建材网订单实体类
 */
@Entity
@Data
public class OrderRecord implements Serializable {
    private static final long serialVersionUID = 2866588012695255434L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "BIGINT  AUTO_INCREMENT NOT NULL COMMENT '订单记录id'")
    private String orderRecId;
    @Column(columnDefinition = "INT(6) COMMENT '记录类型(1.下单订单，2.付款成功，3.订单发货，4.确认收货，5.取消订单,6.申请退款，7.退款操作，8.拒绝退款，)'")
    private Integer  orderRecType;
    @Column(columnDefinition = "INT(10)  COMMENT '操作人id(管理员)'")
    private String  adminId;
    @Column(columnDefinition = "INT(10)  COMMENT '操作人id(客户)'")
    private String  userId;
    @Column(columnDefinition = "BIGINT  COMMENT '订单id'")
    private String  orderId;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '操作说明'")
    private String  orderRecExplanation;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  orderRecCreateTime;


    public OrderRecord() {
    }

    public OrderRecord(Integer orderRecType, String adminId, String userId, String orderId, String orderRecExplanation) {
        this.orderRecType = orderRecType;
        this.adminId = adminId;
        this.userId = userId;
        this.orderId = orderId;
        this.orderRecExplanation = orderRecExplanation;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderRecId() {
        return orderRecId;
    }

    public void setOrderRecId(String orderRecId) {
        this.orderRecId = orderRecId;
    }

    public Integer getOrderRecType() {
        return orderRecType;
    }

    public void setOrderRecType(Integer orderRecType) {
        this.orderRecType = orderRecType;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderRecExplanation() {
        return orderRecExplanation;
    }

    public void setOrderRecExplanation(String orderRecExplanation) {
        this.orderRecExplanation = orderRecExplanation;
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
}
