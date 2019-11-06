package com.noah_solutions.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 交易單
 */
@Entity
@Table(name = "dealing_slip")
@Data
public class DealingSlip {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '自增id'")
//    private String deSlId;
    @Id
    @Column(columnDefinition = "VARCHAR(60)  NOT NULL  COMMENT '交易單號'")
    private String  deslNo;
    @Column(columnDefinition = "VARCHAR(60)   COMMENT 'PayDollar 的支付参考号'")
    private String  deslPayRef;
    @Column(columnDefinition = "DOUBLE NOT NULL COMMENT '(交易金額)'")
    private Double deslAmount;
    @Column(columnDefinition = "DOUBLE NOT NULL COMMENT '(交易金額)'")
    private Double deslAmountCNY;
    @Column(columnDefinition = "DOUBLE NOT NULL COMMENT '(交易金額)'")
    private Double deslAmountUSD;
    @Column(columnDefinition = "VARCHAR(60)  NOT NULL  COMMENT '交易貨幣'")
    private String  deslCur;
    @Column(columnDefinition = "INT(4)  DEFAULT 1 COMMENT '當前狀態(1.交易中,2.交易成功,3.交易失敗,4.交易取消,5.交易超時)'",insertable = false)
    private Integer  deslAState;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW()  COMMENT '創建时间'",updatable = false,insertable=false)
    private String  deslCreatTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String   deslUpdateTime;
    @Column(columnDefinition = "VARCHAR(60)   COMMENT '說明'")
    private String deslExplanation;


    public DealingSlip(String deslNo, Double deslAmount,Double  deslAmountCNY,String  deslCur,String deslExplanation) {
        this.deslNo = deslNo;
        this.deslAmount = deslAmount;
        this.deslCur = deslCur;
        this.deslExplanation = deslExplanation;
        this.deslAmountCNY = deslAmountCNY;
    }

    public DealingSlip() {
    }

    public String getDeslPayRef() {
        return deslPayRef;
    }

    public void setDeslPayRef(String deslPayRef) {
        this.deslPayRef = deslPayRef;
    }

    public String getDeslExplanation() {
        return deslExplanation;
    }

    public void setDeslExplanation(String deslExplanation) {
        this.deslExplanation = deslExplanation;
    }

    public String getDeslNo() {
        return deslNo;
    }

    public void setDeslNo(String deslNo) {
        this.deslNo = deslNo;
    }

    public Double getDeslAmount() {
        return deslAmount;
    }

    public String getDeslCur() {
        return deslCur;
    }

    public void setDeslCur(String deslCur) {
        this.deslCur = deslCur;
    }

    public void setDeslAmount(Double deslAmount) {
        this.deslAmount = deslAmount;
    }

    public Integer getDeslAState() {
        return deslAState;
    }

    public void setDeslAState(Integer deslAState) {
        this.deslAState = deslAState;
    }

    public String getDeslCreatTime() {
        return deslCreatTime;
    }

    public void setDeslCreatTime(String deslCreatTime) {
        this.deslCreatTime = deslCreatTime;
    }

    public String getDeslUpdateTime() {
        return deslUpdateTime;
    }

    public void setDeslUpdateTime(String deslUpdateTime) {
        this.deslUpdateTime = deslUpdateTime;
    }

//    public String getDeSlId() {
//        return deSlId;
//    }
//
//    public void setDeSlId(String deSlId) {
//        this.deSlId = deSlId;
//    }
}
