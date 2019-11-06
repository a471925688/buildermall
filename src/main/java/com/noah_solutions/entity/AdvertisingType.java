package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AdvertisingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '自增id'")
    private String advertisingTypeId;
    @Column(columnDefinition = "DOUBLE COMMENT '圖片大小比列'")
    private String  advertisingTypeImgRatio;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '類型名稱'")
    private String  advertisingTypeName;
    @Column(columnDefinition = "INT COMMENT '圖片數量'")
    private Integer  advertisingTypeNumber;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '類型描述'")
    private String  advertisingTypeDescribe;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  advertisingTypeCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  advertisingTypeUpdateTime;

    //詳情圖片
    @Where(clause="image_group_type = 2")
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="imageGroupId",insertable = false,updatable = false)
    @OrderBy("imageOrder ASC")
    private Set<TbImage> detailsImgNames = new HashSet<>();


    public String getAdvertisingTypeImgRatio() {
        return advertisingTypeImgRatio;
    }

    public void setAdvertisingTypeImgRatio(String advertisingTypeImgRatio) {
        this.advertisingTypeImgRatio = advertisingTypeImgRatio;
    }

    public Set<TbImage> getDetailsImgNames() {
        return detailsImgNames;
    }

    public void setDetailsImgNames(Set<TbImage> detailsImgNames) {
        this.detailsImgNames = detailsImgNames;
    }

    public String getAdvertisingTypeId() {
        return advertisingTypeId;
    }

    public void setAdvertisingTypeId(String advertisingTypeId) {
        this.advertisingTypeId = advertisingTypeId;
    }

    public String getAdvertisingTypeName() {
        return advertisingTypeName;
    }

    public void setAdvertisingTypeName(String advertisingTypeName) {
        this.advertisingTypeName = advertisingTypeName;
    }

    public String getAdvertisingTypeDescribe() {
        return advertisingTypeDescribe;
    }

    public void setAdvertisingTypeDescribe(String advertisingTypeDescribe) {
        this.advertisingTypeDescribe = advertisingTypeDescribe;
    }

    public Integer getAdvertisingTypeNumber() {
        return advertisingTypeNumber;
    }

    public void setAdvertisingTypeNumber(Integer advertisingTypeNumber) {
        this.advertisingTypeNumber = advertisingTypeNumber;
    }

    public String getAdvertisingTypeCreateTime() {
        return advertisingTypeCreateTime;
    }

    public void setAdvertisingTypeCreateTime(String advertisingTypeCreateTime) {
        this.advertisingTypeCreateTime = advertisingTypeCreateTime;
    }

    public String getAdvertisingTypeUpdateTime() {
        return advertisingTypeUpdateTime;
    }

    public void setAdvertisingTypeUpdateTime(String advertisingTypeUpdateTime) {
        this.advertisingTypeUpdateTime = advertisingTypeUpdateTime;
    }
}
