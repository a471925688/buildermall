//package com.noah_solutions.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
///**
// * 2018 12 24 lijun
// * 顏色表
// */
//@Entity
//@Table(name = "color")
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//public class Color implements Serializable {
//    private static final long serialVersionUID = -1017411103470852378L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '顏色id'")
//    private String colorId;
//    @Column(columnDefinition = "VARCHAR(100) DEFAULT 0 COMMENT '(顏色名稱(中文))'")
//    private String colorName;
//    @Column(columnDefinition = "VARCHAR(100) DEFAULT 0 COMMENT '(顏色名稱(英文))'")
//    private String colorNameEng;
////    @ManyToMany(mappedBy="colors")    //就配置一个mappedBy,其余的交给对方配置。
////    @JsonIgnore
////    private Set<Product> product = new HashSet<Product>(0);
//
//    public String getColorId() {
//        return colorId;
//    }
//
//    public void setColorId(String colorId) {
//        this.colorId = colorId;
//    }
//
//    public String getColorName() {
//        return colorName;
//    }
//
//    public void setColorName(String colorName) {
//        this.colorName = colorName;
//    }
//
//    public String getColorNameEng() {
//        return colorNameEng;
//    }
//
//    public void setColorNameEng(String colorNameEng) {
//        this.colorNameEng = colorNameEng;
//    }
//}
