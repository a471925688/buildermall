package com.noah_solutions.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 2018 12 24
 * 商品與顏色關係表
 */
@Entity
@Table(name = "product_config_specifications")
public class ProductConfigSpecifications implements Serializable {
    private static final long serialVersionUID = 6427770053906884950L;
    @Id
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '配置id'")
    private String productConfigId;
    @Column(columnDefinition = "INT(10) NOT NULL COMMENT '其他選擇id'")
    private String productSpecificationsId;

}
