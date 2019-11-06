package com.noah_solutions.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 2018 12 19 lijun
 * 建材网老版商品类型表表实体类（用于数据对接）
 */
@Entity
@Table(name ="place",
        indexes = {@Index(columnList = "parentId"),@Index(columnList = "level")} )
@Data
public class Place implements Serializable {
    @Id
    private String  id;
    private String  nameTc;
    private String  nameEng;
    private String  countryCode;
    private String  status;
    private String  parentId;
    private String  level;
    private String  details;
    private String  detailsEng;
    private Integer  type;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name="parentId",insertable = false,updatable = false)
    private Set<Place> places=new HashSet<>();

   /* private String  parentId;
    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="parentId")
    @OrderBy("id ASC")
    private List<Place> cattype;*/


    public Place() {
    }

    public Place(String id, String nameTc, String nameEng, String countryCode, String status, String parentId, String level) {
        this.id = id;
        this.nameTc = nameTc;
        this.nameEng = nameEng;
        this.countryCode = countryCode;
        this.status = status;
        this.parentId = parentId;
        this.level = level;
    }

    public Place(String id, String nameTc, String nameEng, String countryCode, String status, String parentId, String level,Integer type) {
        this.id = id;
        this.nameTc = nameTc;
        this.nameEng = nameEng;
        this.countryCode = countryCode;
        this.status = status;
        this.parentId = parentId;
        this.level = level;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDetailsEng() {
        return detailsEng;
    }

    public void setDetailsEng(String detailsEng) {
        this.detailsEng = detailsEng;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameTc() {
        return nameTc;
    }

    public void setNameTc(String nameTc) {
        this.nameTc = nameTc;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
