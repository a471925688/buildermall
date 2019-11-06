package com.noah_solutions.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 2019 1 9  lijun
 * 地點信息(順豐)
 */
@Entity
@DynamicUpdate
public class ShunFengPlace implements Serializable {
    private static final long serialVersionUID = 1476898712238866168L;
    @Id
    @Column(unique = true, nullable = false,columnDefinition = "BIGINT  AUTO_INCREMENT  COMMENT 'id'")
    private String sfId;
    @Column(unique = true,columnDefinition = "VARCHAR(50)")
    private String  sfCode;
    @Column(columnDefinition = "VARCHAR(50) ")
    private String  sfName;
    @Column(columnDefinition = "INT(10) DEFAULT 0 COMMENT '(等級)'",nullable = false)
    private String sfLevel;
    @Column(columnDefinition = "BIGINT DEFAULT 0 COMMENT '(父級id)'",nullable = false)
    private String sfParentId;
    @Column(columnDefinition = "VARCHAR(50) DEFAULT 0  COMMENT '父級code'",nullable = false)
    private String  sfParentCode;
    @Column(columnDefinition = "INT(10)   COMMENT '關聯的本地id'")
    private String placeId;

    @Column(columnDefinition = "INT(4)   COMMENT '1.國際,2.港澳台,3.大陸'")
    private Integer placeType;
    //orphanRemoval=true 刪除無關了的數據
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name="sfParentId",insertable = false,updatable = false)
    private Set<ShunFengPlace> shunFengPlaceSet=new HashSet<>();


    public ShunFengPlace() {
    }

    public ShunFengPlace(String sfId,String sfCode, String sfName, String sfLevel, String sfParentId, String sfParentCode) {
        this.sfId = sfId;
        this.sfCode = sfCode;
        this.sfName = sfName;
        this.sfLevel = sfLevel;
        this.sfParentId = sfParentId;
        this.sfParentCode = sfParentCode;
    }

    public Integer getPlaceType() {
        return placeType;
    }

    public void setPlaceType(Integer placeType) {
        this.placeType = placeType;
    }

    public Set<ShunFengPlace> getShunFengPlaceSet() {
        return shunFengPlaceSet;
    }

    public void setShunFengPlaceSet(Set<ShunFengPlace> shunFengPlaceSet) {
        this.shunFengPlaceSet = shunFengPlaceSet;
    }

    public String getSfId() {
        return sfId;
    }

    public void setSfId(String sfId) {
        this.sfId = sfId;
    }

    public String getSfCode() {
        return sfCode;
    }

    public void setSfCode(String sfCode) {
        this.sfCode = sfCode;
    }

    public String getSfName() {
        return sfName;
    }

    public void setSfName(String sfName) {
        this.sfName = sfName;
    }

    public String getSfLevel() {
        return sfLevel;
    }

    public void setSfLevel(String sfLevel) {
        this.sfLevel = sfLevel;
    }

    public String getSfParentId() {
        return sfParentId;
    }

    public void setSfParentId(String sfParentId) {
        this.sfParentId = sfParentId;
    }

    public String getSfParentCode() {
        return sfParentCode;
    }

    public void setSfParentCode(String sfParentCode) {
        this.sfParentCode = sfParentCode;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
