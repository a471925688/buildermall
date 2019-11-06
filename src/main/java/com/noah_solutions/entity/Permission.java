package com.noah_solutions.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 2018 11 26 lijun
 * 建材网权限表实体类
 */
@Entity
@Table(name = "permission")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 1166874999877058362L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '权限id'")
    private String perId;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '模块名称(中文)'")
    private String  perName;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '模块名称(英文)'")
    private String  perNameEng;
    @Column(columnDefinition = "INT(10) DEFAULT 0 NOT NULL COMMENT '(父模块id)'")
    private String perParentId;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '模块路径'")
    private String  perPath;
    @Column(columnDefinition = "VARCHAR(100) COMMENT '图片名称'")
    private String  perIconName;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '模块描述'")
    private String  perDescribe;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '模块描述'")
    private String  perDescribeEng;
    @Column(columnDefinition = "INT(2) DEFAULT 0  NOT NULL COMMENT '模块等级'")
    private Integer  perLevel;
    @Column(columnDefinition = "INT(10)  NOT NULL COMMENT '排序'")
    private Integer  perOrder;
    @Column(columnDefinition = "INT(2) DEFAULT 0 NOT NULL COMMENT '是否父节点(1.是;0.否)'")
    private Integer  perIsParent;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  perCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  perUpdateTime;

//
//    @OneToMany(mappedBy = "permission",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
////    @JoinTable(
////            name = "role_permission",
////            joinColumns = {@JoinColumn(name = "roleId")},
////            inverseJoinColumns = {@JoinColumn(name = "perId")}
////    )
//    private Set<RolePermission> rolePermissions=new HashSet<>();

//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }

    public Integer getPerOrder() {
        return perOrder;
    }

    public void setPerOrder(Integer perOrder) {
        this.perOrder = perOrder;
    }

    public void setPerIsParent(Integer perIsParent) {
        this.perIsParent = perIsParent;
    }

    public String getPerId() {
        return perId;
    }

    public void setPerId(String perId) {
        this.perId = perId;
    }

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public void setPerParentId(String perParentId) {
        this.perParentId = perParentId;
    }

    public String getPerPath() {
        return perPath;
    }

    public void setPerPath(String perPath) {
        this.perPath = perPath;
    }

    public String getPerDescribe() {
        return perDescribe;
    }

    public void setPerDescribe(String perDescribe) {
        this.perDescribe = perDescribe;
    }

    public Integer getPerIsParent() {
        return perIsParent;
    }

    public String getPerCreateTime() {
        return perCreateTime;
    }

    public void setPerCreateTime(String perCreateTime) {
        this.perCreateTime = perCreateTime;
    }

    public String getPerUpdateTime() {
        return perUpdateTime;
    }

    public void setPerUpdateTime(String perUpdateTime) {
        this.perUpdateTime = perUpdateTime;
    }

    public String getPerIconName() {
        return perIconName;
    }

    public String getPerParentId() {
        return perParentId;
    }

    public void setPerLevel(Integer perLevel) {
        this.perLevel = perLevel;
    }

    public void setPerIconName(String perIconName) {
        this.perIconName = perIconName;
    }

    public Integer getPerLevel() {
        return perLevel;
    }
//    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "role_permission",
//            joinColumns = {@JoinColumn(name = "perId")},
//            inverseJoinColumns = {@JoinColumn(name = "roleId")}
//    )
//    private Set<Role> roles;
}
