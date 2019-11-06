package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class SupplierApplyProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT 'id'")
    private String id;

    @Column(columnDefinition = "VARCHAR(200) COMMENT '产品名称1'")
    private String productName1;//
    @Column(columnDefinition = "VARCHAR(200) COMMENT '产品名称2'")
    private String productName2;//
    @Column(columnDefinition = "VARCHAR(60) COMMENT '月产量'")
    private String productionMonthly;//
    @Column(columnDefinition = "VARCHAR(200) COMMENT '銷售額'")
    private String productSales;//
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '(供應商申请表id)'")
    private String supplierapplyId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName1() {
        return productName1;
    }

    public void setProductName1(String productName1) {
        this.productName1 = productName1;
    }

    public String getProductName2() {
        return productName2;
    }

    public void setProductName2(String productName2) {
        this.productName2 = productName2;
    }

    public String getProductionMonthly() {
        return productionMonthly;
    }

    public void setProductionMonthly(String productionMonthly) {
        this.productionMonthly = productionMonthly;
    }

    public String getProductSales() {
        return productSales;
    }

    public void setProductSales(String productSales) {
        this.productSales = productSales;
    }

    public String getSupplierapplyId() {
        return supplierapplyId;
    }

    public void setSupplierapplyId(String supplierapplyId) {
        this.supplierapplyId = supplierapplyId;
    }
}
