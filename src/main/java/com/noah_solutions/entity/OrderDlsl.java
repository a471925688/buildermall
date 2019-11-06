package com.noah_solutions.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 訂單與交易單關系表
 */
@Entity
@Table(name = "order_dlsl")
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class OrderDlsl implements Serializable {

    @Id
    @Column(columnDefinition = "BIGINT NOT NULL COMMENT '訂單id'")
    private String orderId;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '交易號'",nullable = false)
    private String deslNo;

    public OrderDlsl() {
    }

    public OrderDlsl(String orderId, String deslNo) {
        this.orderId = orderId;
        this.deslNo = deslNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeslNo() {
        return deslNo;
    }

    public void setDeslNo(String deslNo) {
        this.deslNo = deslNo;
    }
}
