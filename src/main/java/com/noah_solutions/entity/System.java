package com.noah_solutions.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_system")
@Data
public class System implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '自增id'")
    private String systemId;

    @Column(columnDefinition = "VARCHAR(200) COMMENT '網站名稱'")
    private String  systemWebName;
    @Column(columnDefinition = "INT  COMMENT '交易金額超過最大金額發送通知郵件'")
    private String  systemMaxAmount;
    @Column(columnDefinition = "VARCHAR(40) COMMENT '邮箱地址'")
    private String  systemEmail;

    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  systemCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  systemUpdateTime;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemWebName() {
        return systemWebName;
    }

    public void setSystemWebName(String systemWebName) {
        this.systemWebName = systemWebName;
    }

    public String getSystemMaxAmount() {
        return systemMaxAmount;
    }

    public void setSystemMaxAmount(String systemMaxAmount) {
        this.systemMaxAmount = systemMaxAmount;
    }

    public String getSystemEmail() {
        return systemEmail;
    }

    public void setSystemEmail(String systemEmail) {
        this.systemEmail = systemEmail;
    }

    public String getSystemCreateTime() {
        return systemCreateTime;
    }

    public void setSystemCreateTime(String systemCreateTime) {
        this.systemCreateTime = systemCreateTime;
    }

    public String getSystemUpdateTime() {
        return systemUpdateTime;
    }

    public void setSystemUpdateTime(String systemUpdateTime) {
        this.systemUpdateTime = systemUpdateTime;
    }
}
