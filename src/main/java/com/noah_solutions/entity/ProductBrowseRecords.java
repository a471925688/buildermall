package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 2018 11 26 lijun
 * 建材网产品评价表实体类
 */
@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ProductBrowseRecords implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "BIGINT  AUTO_INCREMENT NOT NULL COMMENT '自增id'")
    private String id;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '產品id(編號)'")
    private String productId;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '用戶id'")
    private String userId;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '瀏覽时间'",insertable=false)
    private String  createTime;


    public ProductBrowseRecords(String productId, String userId) {
        this.productId = productId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
