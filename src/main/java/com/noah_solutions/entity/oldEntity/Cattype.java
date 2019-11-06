//package com.noah_solutions.entity.oldEntity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.List;
//
///**
// * 2018 12 19 lijun
// * 建材网老版商品类型表表实体类（用于数据对接）
// */
//@Entity
//@Table(name = "cattype")
//@Data
//public class Cattype implements Serializable {
//    @Id
//    private String id;
//    private String  code;
//    private String name_tc;
//    private String  name_eng;
//    private String  status;
//    private String  catId;
//    private String parentId;
//    @OneToMany(fetch=FetchType.EAGER)
//    @JoinColumn(name="parentId")
//    @OrderBy("id ASC")
//    private List<Cattype> cattype;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getName_tc() {
//        return name_tc;
//    }
//
//    public void setName_tc(String name_tc) {
//        this.name_tc = name_tc;
//    }
//
//    public String getName_eng() {
//        return name_eng;
//    }
//
//    public void setName_eng(String name_eng) {
//        this.name_eng = name_eng;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getCatId() {
//        return catId;
//    }
//
//    public void setCatId(String catId) {
//        this.catId = catId;
//    }
//
//    public String getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(String parentId) {
//        this.parentId = parentId;
//    }
//
//    public List<Cattype> getCattype() {
//        return cattype;
//    }
//
//    public void setCattype(List<Cattype> cattype) {
//        this.cattype = cattype;
//    }
//
////
////    @OneToMany(mappedBy = "productTypemission",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//////    @JoinTable(
//////            name = "role_productTypemission",
//////            joinColumns = {@JoinColumn(name = "roleId")},
//////            inverseJoinColumns = {@JoinColumn(name = "productTypeId")}
//////    )
////    private Set<RoleProductTypemission> roleProductTypemissions=new HashSet<>();
//
////    public Set<Role> getRoles() {
////        return roles;
////    }
////
////    public void setRoles(Set<Role> roles) {
////        this.roles = roles;
////    }
//
////    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
////    @JoinTable(
////            name = "role_productTypemission",
////            joinColumns = {@JoinColumn(name = "productTypeId")},
////            inverseJoinColumns = {@JoinColumn(name = "roleId")}
////    )
////    private Set<Role> roles;
//}
